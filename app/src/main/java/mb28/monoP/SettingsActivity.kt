package mb28.monoP

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import mb28.crystalHomeKt.ui.icons.arrow_back
import mb28.monoP.core.Settings
import mb28.monoP.core.Settings.requestAllFilesAccessOrFinish
import mb28.monoP.core.getComment
import mb28.monoP.icons.add_2
import mb28.monoP.icons.camera
import mb28.monoP.icons.info
import mb28.monoP.icons.photo_camera
import mb28.monoP.icons.photo_camera_back
import mb28.monoP.icons.photo_camera_front
import mb28.monoP.ui.components.EasySegmentedListItem
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
                                Text("Settings")
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
fun MainSettings(paddingValues: PaddingValues) {
    val count = 5 // TODO: include msd in settings
    var osdcim by remember { mutableStateOf(Settings.onlyShowDCIM) }
    var rotate by remember { mutableStateOf(Settings.allowRotationGesture) }
    var iapp by remember { mutableStateOf(Settings.inAppPhotoViewer) }
    var iappcam by remember { mutableStateOf(Settings.inAppCamera) }
    var ti by remember { mutableStateOf(Settings.trashInstead) }
    var msd by remember { mutableStateOf(Settings.useMediaStoreDelete) }

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
                            0 -> rotate
                            1 -> iappcam
                            2 -> iapp
                            3 -> osdcim
                            4 -> ti
                            5 -> msd
                            else -> throw Exception()
                        },
                        { v ->
                            when(i) {
                                0 -> {
                                    rotate = v
                                    Settings.allowRotationGesture = v
                                }
                                1 -> {
                                    iappcam = v
                                    Settings.inAppCamera = v
                                }
                                2 -> {
                                    iapp = v
                                    Settings.inAppPhotoViewer = v
                                }
                                3 -> {
                                    osdcim = v
                                    Settings.onlyShowDCIM = v
                                }
                                4 -> {
                                    ti = v
                                    Settings.trashInstead = v
                                }
                                5 -> {
                                    msd = v
                                    Settings.useMediaStoreDelete = v
                                }
                            }
                            Settings.save()
                        },
                        modifier = Modifier.padding(vertical = 10.dp),
                        enabled = when(i) {
                            0 -> iapp
                            5 -> ti
                            else -> true
                        },
                    )
                },
                onClick = {}
            ) {
                Text(
                    when(i) {
                        0 -> "Allow rotation in image viewer"
                        1 -> "In-app camera"
                        2 -> "In-app image viewer"
                        3 -> "Only show DCIM folder"
                        4 -> "Trash instead of delete"
                        5 -> "Use MediaStore for deleting"
                        else -> throw Exception()
                    }
                )
            }
        }

        item { Spacer(Modifier.height(15.dp)) }
        item {
            var sorting by remember { mutableStateOf(Settings.mediaStore_sql_sorting) }
            SegmentedListItem(
                shapes = ListItemDefaults.segmentedShapes(0, 1),
            ) {
                OutlinedTextField(
                    sorting,
                    {
                        sorting = it
                        Settings.mediaStore_sql_sorting = it
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
fun CameraSettings(paddingValues: PaddingValues) {
    val count = 3
    var lf by remember { mutableStateOf(Settings.lockFocusWithShutter) }
    var le by remember { mutableStateOf(Settings.lockExposureWithShutter) }
    var ac by remember { mutableStateOf(Settings.addCommentAfterCapture) }

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
                            0 -> lf
                            1 -> le
                            2 -> ac
                            else -> throw Exception()
                        },
                        { v ->
                            when(i) {
                                0 -> {
                                    lf = v
                                    Settings.lockFocusWithShutter = v
                                }
                                1 -> {
                                    le = v
                                    Settings.lockExposureWithShutter = v
                                }
                                2 -> {
                                    ac = v
                                    Settings.addCommentAfterCapture = v
                                }
                            }
                            Settings.save()
                        },
                        modifier = Modifier.padding(vertical = 10.dp),
                    )
                },
                onClick = {}
            ) {
                Text(
                    when(i) {
                        0 -> "Lock focus when holding shutter"
                        1 -> "Lock exposure when holding shutter"
                        2 -> "Show write comment input field and save comment to image Exif"
                        else -> throw Exception()
                    }
                )
            }
        }

        val segmentedSectionCount = 2
        item { Spacer(Modifier.height(15.dp)) }
        item {
            val cameraDirections = listOf("Back", "Front")
            var cameraDirectionsI by remember { mutableIntStateOf(Settings.startCameraMode) }
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
                                selected = cameraDirectionsI == index,
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
                                    cameraDirectionsI = index
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
            var segI by remember { mutableIntStateOf(Settings.imageCaptureMode) }
            SegmentedListItem(
                shapes = ListItemDefaults.segmentedShapes(1, segmentedSectionCount),
                modifier = Modifier.padding(bottom = 2.dp),
            ) {
                Column {
                    Text(
                        "Image Capture mode",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    SingleChoiceSegmentedButtonRow(
                        Modifier.fillMaxWidth()
                    ) {
                        seg.forEachIndexed { index, t ->
                            SegmentedButton(
                                selected = segI == index,
                                shape = SegmentedButtonDefaults.itemShape(index, 3),
                                onClick = {
                                    segI = index
                                    Settings.imageCaptureMode = index
                                    Settings.save()
                                }
                            ) {
                                Text(t)
                            }
                        }
                    }
                    Text(
                        "- Maximize Quality: Better quality but images may take longer to capture. larger file size\n" +
                            "- Minimize Latency: Balance\n" +
                                "- 0 Shutter Lag: Better latency while keeping good image quality (Experimental in CameraX)",
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







