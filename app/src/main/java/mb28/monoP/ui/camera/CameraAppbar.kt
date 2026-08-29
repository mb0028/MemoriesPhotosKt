package mb28.monoP.ui.camera

import android.app.Activity
import android.content.Intent
import androidx.camera.core.ImageCapture
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mb28.crystalHomeKt.ui.icons.arrow_back
import mb28.monoP.EXTRA_SHOW_CAMERA_SETTINGS
import mb28.monoP.SettingsActivity
import mb28.monoP.core.Settings
import mb28.monoP.icons.crop_9_16
import mb28.monoP.icons.crop_portrait
import mb28.monoP.icons.crop_square
import mb28.monoP.icons.flash_auto
import mb28.monoP.icons.flash_on
import mb28.monoP.icons.fullscreen_portrait
import mb28.monoP.icons.no_flash
import mb28.monoP.icons.settings_photo_camera

@Composable
fun CameraAppBar(
    activity: Activity,
    controller: LifecycleCameraController,
    modifier: Modifier,
    onAspectChanged: (Int) -> Unit,
) {
    TopAppBar(
        {},
        colors = TopAppBarDefaults.topAppBarColors(
            MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
            actionIconContentColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier,
        navigationIcon = {
            IconButton(
                { activity.finish() },
                colors = IconButtonDefaults.iconButtonColors(
                    MaterialTheme.colorScheme.surfaceContainerHigh
                ),
                modifier = Modifier.padding(horizontal = 15.dp)
            ) {
                Icon(
                    arrow_back,
                    contentDescription = null
                )
            }
        },
        actions = {
            var aspect by remember { mutableIntStateOf(Settings.cameraAspect) }
            IconButton(
                {
                    when(aspect) {
                        0 -> aspect = 1
                        1 -> aspect = 0
//                        2 -> aspect = 3
//                        3 -> aspect = 0
                    }
                    Settings.cameraAspect = aspect
                    onAspectChanged(aspect)
                    Settings.save()
                }
            ) {
                Icon(
                    when(aspect) {
                        0 -> crop_portrait
                        1 -> crop_9_16
                        2 -> fullscreen_portrait
                        3 -> crop_square
                        else -> throw Exception()
                    },
                    null
                )
            }

            var fm by remember { mutableIntStateOf(Settings.imageCaptureFlashMode) }
            if (true) { // TODO: add condition for this (if camera is not front = true)
                IconButton(
                    {
                        when(fm) {
                            ImageCapture.FLASH_MODE_AUTO -> fm = 1
                            ImageCapture.FLASH_MODE_ON -> fm = 2
                            ImageCapture.FLASH_MODE_OFF -> fm = 0
                        }
                        Settings.imageCaptureFlashMode = fm
                        controller.imageCaptureFlashMode = fm
                        Settings.save()
                    }
                ) {
                    Icon(
                        when(fm) {
                            ImageCapture.FLASH_MODE_AUTO -> flash_auto
                            ImageCapture.FLASH_MODE_ON -> flash_on
                            ImageCapture.FLASH_MODE_OFF -> no_flash
                            else -> throw Exception()
                        },
                        null
                    )
                }
            }

            IconButton({
                val intent = Intent(activity, SettingsActivity::class.java)
                    .putExtra(EXTRA_SHOW_CAMERA_SETTINGS, true)
                activity.startActivity(intent)
            }) { Icon(settings_photo_camera, null) }

        }
    )
}