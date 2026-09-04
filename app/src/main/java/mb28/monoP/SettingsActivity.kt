package mb28.monoP

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import mb28.monoP.core.Settings
import mb28.monoP.core.Settings.mediaStore_sql_sorting
import mb28.monoP.core.Settings.requestAllFilesAccessOrFinish
import mb28.monoP.icons.arrow_back
import mb28.monoP.icons.info
import mb28.monoP.icons.photo_camera_back
import mb28.monoP.icons.photo_camera_front
import mb28.monoP.ui.theme.MemoriesPhotosTheme

const val EXTRA_SHOW_CAMERA_SETTINGS = "EXTRA_SHOW_CAMERA_SETTINGS"

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.isAppearanceLightStatusBars = true
        controller.isAppearanceLightNavigationBars = true
        window.isNavigationBarContrastEnforced = false

        requestAllFilesAccessOrFinish()
        Settings.load()

        val showCameraSettings = intent.getBooleanExtra(EXTRA_SHOW_CAMERA_SETTINGS, false)

        super.onCreate(savedInstanceState)
        setContent {
            MemoriesPhotosTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    topBar = {
                        LargeTopAppBar(
                            {
                                Text(stringResource(R.string.settings))
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Transparent
                            ),
                            navigationIcon = {
                                IconButton(
                                    { finish() },
                                    colors = IconButtonDefaults.iconButtonColors().copy(
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
                                IconButton({

                                }) {
                                    Icon(info, null)
                                }
                            }
                        )
                    }
                ) { i ->
                    if (showCameraSettings) {
                        CameraSettings(i)
                    }
                    else {
                        MainSettings(i)
                    }
                }
            }
        }
    }
}

@Composable
private fun MainSettings(paddingValues: PaddingValues) {
    val count = 6

    LazyColumn(
        contentPadding = paddingValues,
        modifier = Modifier.padding(15.dp)
    ) {
        items(count) { i ->
            SegmentedListItem(
                shapes = ListItemDefaults.segmentedShapes(i, count),
                modifier = Modifier.padding(bottom = 2.dp),
                trailingContent = {
                    Switch(
                        when(i) {
                            0 -> Settings.allowRotationGesture
                            1 -> Settings.inAppCamera
                            2 -> Settings.inAppPhotoViewer
                            3 -> Settings.onlyShowDCIM
                            4 -> Settings.trashInstead
                            5 -> Settings.useMediaStoreDelete
                            else -> throw Exception()
                        },
                        { v ->
                            when(i) {
                                0 -> Settings.allowRotationGesture = v
                                1 -> Settings.inAppCamera = v
                                2 -> Settings.inAppPhotoViewer = v
                                3 -> Settings.onlyShowDCIM = v
                                4 -> Settings.trashInstead = v
                                5 -> Settings.useMediaStoreDelete = v
                            }
                            Settings.save()
                        },
                        modifier = Modifier.padding(vertical = 10.dp),
                        enabled = when(i) {
                            0 -> Settings.inAppPhotoViewer
                            5 -> false
                            1 -> false
                            2 -> false
                            else -> true
                        },
                    )
                },
                onClick = {}
            ) {
                Text(
                    when(i) {
                        0 -> stringResource(R.string.s_rotatable_images)
                        1 -> stringResource(R.string.s_in_app_camera)
                        2 -> stringResource(R.string.s_in_app_image_viewer)
                        3 -> stringResource(R.string.s_only_show_dcim)
                        4 -> stringResource(R.string.s_trash_instead)
                        5 -> stringResource(R.string.s_mediastore_deleting)
                        else -> throw Exception()
                    }
                )
            }
        }

        item { Spacer(Modifier.height(15.dp)) }
        item {
            SegmentedListItem(
                shapes = ListItemDefaults.segmentedShapes(0, 1),
                modifier = Modifier.clip(RoundedCornerShape(15.dp))
            ) {
                OutlinedTextField(
                    mediaStore_sql_sorting,
                    {
                        mediaStore_sql_sorting = it
                        Settings.save()
                    },
                    minLines = 3,
                    maxLines = 10,
                    shape = OutlinedTextFieldDefaults.roundedShape,
                    textStyle = TextStyle(
                        fontSize = 18.sp
                    ),
                    label = {
                        Text(stringResource(R.string.image_sorting_sql))
                    },
                    supportingText = {
                        Text(stringResource(R.string.warning_invalid_sorting))
                    }
                )
            }
        }
    }
}

@Composable
private fun CameraSettings(paddingValues: PaddingValues) {
    val count = 3

    LazyColumn(
        contentPadding = paddingValues,
        modifier = Modifier.padding(15.dp)
    ) {
        items(count) { i ->
            SegmentedListItem(
                shapes = ListItemDefaults.segmentedShapes(i, count),
                modifier = Modifier.padding(bottom = 2.dp),
                trailingContent = {
                    Switch(
                        when(i) {
                            0 -> Settings.lockFocusWithShutter
                            1 -> Settings.lockExposureWithShutter
                            2 -> Settings.addCommentAfterCapture
                            else -> throw Exception()
                        },
                        { v ->
                            when(i) {
                                0 -> Settings.lockFocusWithShutter = v
                                1 -> Settings.lockExposureWithShutter = v
                                2 -> Settings.addCommentAfterCapture = v
                            }
                            Settings.save()
                        },
                        modifier = Modifier.padding(vertical = 10.dp),
                    )
                },
                enabled = when(i) {
                    0 -> false
                    1 -> false
                    else -> true
                },
                onClick = {}
            ) {
                Text(
                    when(i) {
                        0 -> stringResource(R.string.s_lock_focus)
                        1 -> stringResource(R.string.s_lock_exposure)
                        2 -> stringResource(R.string.s_write_comment_input)
                        else -> throw Exception()
                    }
                )
            }
        }

        val segmentedSectionCount = 2
        item { Spacer(Modifier.height(15.dp)) }
        item {
            val cameraDirections = listOf(
                stringResource(R.string.camera_back),
                stringResource(R.string.camera_front)
            )
            SegmentedListItem(
                shapes = ListItemDefaults.segmentedShapes(0, segmentedSectionCount),
                modifier = Modifier.padding(bottom = 2.dp),
                ) {
                Column {
                    Text(
                        "Start camera in",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    SingleChoiceSegmentedButtonRow(
                        Modifier.fillMaxWidth()
                    ) {
                        cameraDirections.forEachIndexed { index, t ->
                            SegmentedButton(
                                selected = Settings.startCameraMode == index,
                                shape = SegmentedButtonDefaults.itemShape(index, 2),
                                icon = {
                                    Icon(
                                        when(index) {
                                            0 -> photo_camera_back
                                            else -> photo_camera_front
                                        },
                                        null
                                    )
                                },
                                onClick = {
                                    Settings.startCameraMode = index
                                    Settings.save()
                                }
                            ) {
                                Text(t)
                            }
                        }
                    }
                }
            }
        }

        item {
            val seg = listOf("Maximize\nQuality", "Minimize\nLatency", "0 Shutter\nLag")
            SegmentedListItem(
                shapes = ListItemDefaults.segmentedShapes(1, segmentedSectionCount),
                modifier = Modifier.padding(bottom = 2.dp),
            ) {
                Column {
                    Text(
                        stringResource(R.string.s_image_capture_mode),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    SingleChoiceSegmentedButtonRow(
                        Modifier.fillMaxWidth()
                    ) {
                        seg.forEachIndexed { index, t ->
                            SegmentedButton(
                                selected = Settings.imageCaptureMode == index,
                                shape = SegmentedButtonDefaults.itemShape(index, 3),
                                onClick = {
                                    Settings.imageCaptureMode = index
                                    Settings.save()
                                }
                            ) {
                                Text(t)
                            }
                        }
                    }
                    Text(
                        stringResource(R.string.s_image_capture_mode_helper),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        fontSize = 14.sp
                    )
                }
            }
        }

    }
}
