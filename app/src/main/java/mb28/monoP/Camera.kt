package mb28.monoP

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import mb28.monoP.core.Settings.requestAllFilesAccessOrFinish
import mb28.monoP.icons.photo_camera
import mb28.monoP.icons.photo_prints
import mb28.monoP.ui.components.ShutterButton
import mb28.monoP.ui.theme.MemoriesPhotosTheme

class Camera : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.isAppearanceLightStatusBars = true
        controller.isAppearanceLightNavigationBars = true
        window.isNavigationBarContrastEnforced = false

        requestAllFilesAccessOrFinish()

        super.onCreate(savedInstanceState)

        val permission = checkSelfPermission(Manifest.permission.CAMERA)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 0)
        }

        val previewView = PreviewView(this)
        val cameraController = LifecycleCameraController(baseContext)
        cameraController.bindToLifecycle(this)
        cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        previewView.controller = cameraController

        setContent {
            MemoriesPhotosTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    bottom = WindowInsets.navigationBars.asPaddingValues()
                                        .calculateBottomPadding() + 5.dp
                                ),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val context = LocalActivity.current!!
                            IconButton(
                                {
                                    context.startActivity(Intent(context, MainActivity::class.java)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                                },
                                modifier = Modifier.size(75.dp)
                            ) {
                                Icon(
                                    photo_prints,
                                    null,
                                    modifier = Modifier.fillMaxSize(0.65f)
                                )
                            }
                            ShutterButton { }
                            IconButton(
                                {

                                },
                                modifier = Modifier.size(75.dp)
                            ) {
                                Icon(
                                    photo_camera,
                                    null,
                                    modifier = Modifier.fillMaxSize(0.65f)
                                )
                            }
                        }
                    }
                ) { innerPadding -> innerPadding
                    if (permission == PackageManager.PERMISSION_GRANTED) {

                    } else { PermissionPage() }
                }
            }
        }
    }
}

@Composable
fun PermissionPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
            .widthIn(max = 480.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Camera permission is denied.",
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = {}) {
            Text("Open Settings")
        }
    }
}