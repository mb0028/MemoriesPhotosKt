package mb28.monoP

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalZeroShutterLag
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.exifinterface.media.ExifInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.launch
import mb28.monoP.core.Settings
import mb28.monoP.core.Settings.load
import mb28.monoP.core.Settings.requestAllFilesAccessOrFinish
import mb28.monoP.icons.flip_camera_android
import mb28.monoP.icons.photo_prints
import mb28.monoP.ui.camera.CameraAppBar
import mb28.monoP.ui.camera.CameraPermissionPage
import mb28.monoP.ui.camera.ShutterButton
import mb28.monoP.ui.theme.MemoriesPhotosTheme
import java.io.File
import java.time.LocalDateTime

private const val MAKER_NOTE_P = "Captured with Memories Photos"
private lateinit var cameraController: LifecycleCameraController
class Camera : ComponentActivity() {
    @androidx.annotation.OptIn(ExperimentalZeroShutterLag::class)
    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        setupWindowAndShortcuts()
        requestAllFilesAccessOrFinish()
        load()
        super.onCreate(savedInstanceState)

        val permission = checkSelfPermission(Manifest.permission.CAMERA)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 0)
        }

        // Cam
        val previewView = PreviewView(this)
        previewView.scaleType = PreviewView.ScaleType.FIT_CENTER
        previewView.setBackgroundColor(0x00ffffff)
        cameraController = LifecycleCameraController(baseContext)
        changeAspect(Settings.cameraAspect)
        cameraController.bindToLifecycle(this)
        previewView.controller = cameraController


        setContent {
            MemoriesPhotosTheme {
                val interactionSource = remember { MutableInteractionSource() }
//                val isShutterPressed by interactionSource.collectIsPressedAsState()
//                val uiScale: Float by animateFloatAsState(
//                    if (isShutterPressed) 0.95f else 1f
//                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CameraAppBar(this, cameraController) {
                            changeAspect(it)
                        }
                    },
                    bottomBar = {
                        ShutterRow(interactionSource, this)
                    }
                ) { i -> i
                    if (permission == PackageManager.PERMISSION_GRANTED) {
                        Box(
                            Modifier.fillMaxSize().statusBarsPadding().padding(top = 65.dp),
                            Alignment.TopCenter
                        ) {
                            AndroidView(
                                { previewView },
                                modifier = Modifier.aspectRatio(when(Settings.cameraAspect) {
                                    0 -> 3f / 4f
                                    1 -> 9f / 16f
                                    else -> 0f
                                })
                            )
                        }
                    } else {
                        CameraPermissionPage()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        cameraController.imageCaptureMode = Settings.imageCaptureMode
        cameraController.imageCaptureFlashMode = Settings.imageCaptureFlashMode
        when (Settings.startCameraMode) {
            0 -> cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            1 -> cameraController.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        }
    }
}

@Composable
fun ShutterRow(interactionSource:  MutableInteractionSource, context: Activity) {
    val scope = rememberCoroutineScope()
    var capturedPath by remember { mutableStateOf("") }
    var lastComment by remember { mutableStateOf("") }

    Column(
        Modifier.navigationBarsPadding().padding(bottom = 5.dp),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        if (Settings.addCommentAfterCapture) {
            TextField(
                lastComment,
                { lastComment = it },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    cursorColor = Color.Transparent
                ),
                textStyle = TextStyle(
                    textAlign = TextAlign.Center
                ),
                placeholder = {
                    Text(
                        stringResource(R.string.camera_write_comment),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Ascii,
                ),
                modifier = Modifier.padding(bottom = 15.dp)
                    .size(220.dp, 50.dp)
            )
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                {
                    context.startActivity(
                        Intent(context, MainActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    )
                },
                modifier = Modifier.size(65.dp)
            ) {
                Icon(
                    photo_prints,
                    null,
                    modifier = Modifier.fillMaxSize(0.65f)
                )
            }
            ShutterButton(interactionSource) {
                scope.launch {
                    val t = LocalDateTime.now()
                    val outputOptions = ImageCapture.OutputFileOptions.Builder(
                        File(Settings.appFolder, "Photo ${t.year}-${t.monthValue.toString().padStart(2, '0')}" +
                            "-${t.dayOfMonth.toString().padStart(2, '0')} " +
                            "${t.hour.toString().padStart(2, '0')}-${t.minute.toString().padStart(2, '0')}" +
                            "-${t.second.toString().padStart(2, '0')}.jpg")
                    ).setMetadata(ImageCapture.Metadata())
                        .build()

                    cameraController.takePicture(
                        outputOptions,
                        Dispatchers.Main.immediate.asExecutor(),
                        object : ImageCapture.OnImageSavedCallback {
                            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                capturedPath = outputFileResults.savedUri!!.path!!
                                Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()

                                val e = ExifInterface(capturedPath)
                                e.setAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION, MAKER_NOTE_P)
                                if (Settings.addCommentAfterCapture && lastComment.isNotBlank() && lastComment.isNotEmpty()) {
                                    e.setAttribute(ExifInterface.TAG_USER_COMMENT, lastComment)
                                } else {
                                    e.setAttribute(ExifInterface.TAG_USER_COMMENT, null)
                                }
                                e.saveAttributes()
                            }

                            override fun onError(e: ImageCaptureException) {
                                Toast.makeText(context, "Failed: $e", Toast.LENGTH_LONG).show()
                            }
                        }
                    )
                    context.sendBroadcast(
                        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(
                            File(capturedPath)
                        ))
                    )
                }
            }
            IconButton(
                {
                    when (cameraController.cameraSelector) {
                        CameraSelector.DEFAULT_BACK_CAMERA -> cameraController.cameraSelector =
                            CameraSelector.DEFAULT_FRONT_CAMERA
                        CameraSelector.DEFAULT_FRONT_CAMERA -> cameraController.cameraSelector =
                            CameraSelector.DEFAULT_BACK_CAMERA
                    }
                },
                modifier = Modifier.size(65.dp)
            ) {
                Icon(
                    flip_camera_android,
                    null,
                    modifier = Modifier.fillMaxSize(0.65f)
                )
            }
        }
    }
}

private fun Activity.setupWindowAndShortcuts() {
    window.isNavigationBarContrastEnforced = false

    val shortcut = ShortcutInfoCompat.Builder(this, "cam_settings")
        .setShortLabel("Camera Settings")
        .setIcon(IconCompat.createWithResource(this, R.mipmap.shortcut_settings_icon))
        .setIntent(Intent(Intent.ACTION_SHOW_APP_INFO).putExtra(EXTRA_SHOW_CAMERA_SETTINGS, true))
        .setActivity(componentName)
        .build()
    ShortcutManagerCompat.pushDynamicShortcut(this, shortcut)
}

fun changeAspect(asp: Int) {
    val resolutionSelector = ResolutionSelector.Builder()
        .setAspectRatioStrategy(
            AspectRatioStrategy(
                when(asp) {
                    0 -> AspectRatio.RATIO_4_3
                    else -> AspectRatio.RATIO_16_9
                },
                AspectRatioStrategy.FALLBACK_RULE_AUTO
            )
        )
        .build()
    cameraController.previewResolutionSelector = resolutionSelector
}
