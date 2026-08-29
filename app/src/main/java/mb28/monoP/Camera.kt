package mb28.monoP

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.camera.camera2.compat.workaround.TargetAspectRatio
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraX
import androidx.camera.core.ExperimentalZeroShutterLag
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.impl.ImageCaptureConfig
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.view.WindowCompat
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
import mb28.monoP.ui.camera.CameraBgShape
import mb28.monoP.ui.camera.CameraPermissionPage
import mb28.monoP.ui.components.ShutterButton
import mb28.monoP.ui.theme.MemoriesPhotosTheme
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import kotlin.time.Duration.Companion.seconds

private const val MAKER_NOTE_P = "Captured with Memories Photos"
private lateinit var cameraController: LifecycleCameraController
class Camera : ComponentActivity() {
    @androidx.annotation.OptIn(ExperimentalZeroShutterLag::class)
    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
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
                val isShutterPressed by interactionSource.collectIsPressedAsState()
                val uiAlpha: Float by animateFloatAsState(
                    if (isShutterPressed) 0f else 0.75f
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CameraAppBar(this, cameraController, Modifier.alpha(uiAlpha)) {
                            changeAspect(it)
                        }
                    },
                    bottomBar = {
                        ShutterRow(interactionSource, uiAlpha)
                    }
                ) { i -> i
                    if (permission == PackageManager.PERMISSION_GRANTED) {
                        AndroidView(
                            { previewView },
                            modifier = Modifier
                                .fillMaxSize()
                        )

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
fun ShutterRow(interactionSource:  MutableInteractionSource, uiAlpha: Float) {
    val context = LocalActivity.current!!
    val scope = rememberCoroutineScope()
    var capturedPath by remember { mutableStateOf("") }
    val bPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 5.dp
    var lastComment by remember { mutableStateOf("") }

    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        CameraBgShape(
            Modifier
                .offset(y = 210.dp - bPadding)
                .alpha(uiAlpha)
        )
        if (Settings.addCommentAfterCapture) {
            TextField(
                lastComment,
                { lastComment = it },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    cursorColor = Color.Transparent
                ),
                textStyle = TextStyle(
                    textAlign = TextAlign.Center
                ),
                singleLine = true,
                placeholder = {
                    Text(
                        "write comment...",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Ascii,
                ),
                modifier = Modifier
                    .width(220.dp)
                    .height(50.dp)
                    .offset(y = -(200).dp + bPadding)
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = bPadding),
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
                                Toast.makeText(context, "Saved: $capturedPath", Toast.LENGTH_LONG)
                                    .show()
                                val e = ExifInterface(capturedPath)
                                e.setAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION, MAKER_NOTE_P)
                                if (Settings.addCommentAfterCapture && lastComment.isNotBlank()) {
                                    e.setAttribute(ExifInterface.TAG_USER_COMMENT, lastComment)
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
    val controller = WindowCompat.getInsetsController(window, window.decorView)
    controller.isAppearanceLightStatusBars = true
    controller.isAppearanceLightNavigationBars = true
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
                    1 -> AspectRatio.RATIO_16_9
                    else -> AspectRatio.RATIO_16_9
                },
                AspectRatioStrategy.FALLBACK_RULE_AUTO
            )
        )
        .build()
    cameraController.previewResolutionSelector = resolutionSelector
}
