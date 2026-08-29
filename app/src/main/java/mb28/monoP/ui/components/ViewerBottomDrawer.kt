package mb28.monoP.ui.components

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.exifinterface.media.ExifInterface
import mb28.crystalHomeKt.ui.icons.coffee
import mb28.crystalHomeKt.ui.icons.delete_forever
import mb28.crystalHomeKt.ui.icons.favorite
import mb28.crystalHomeKt.ui.icons.heart_plus
import mb28.monoP.R
import mb28.monoP.core.Settings
import mb28.monoP.icons.add_2
import mb28.monoP.icons.camera
import mb28.monoP.icons.comic_bubble
import mb28.monoP.icons.comment
import mb28.monoP.icons.draw
import mb28.monoP.icons.lens_blur
import mb28.monoP.icons.my_location
import mb28.monoP.icons.pageless
import mb28.monoP.icons.photo_camera
import mb28.monoP.icons.photo_prints
import java.io.File
import java.util.Date
import kotlin.math.roundToInt

@SuppressLint("SdCardPath")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewerBottomDrawer(path: String) {
    val exif = ExifInterface(path)
    val padding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val st = rememberBottomSheetScaffoldState()
    val isInFavorites = remember { mutableStateOf(Settings.favorites.contains(path)) }

    val tabsSpacing = animateDpAsState(if (st.bottomSheetState.currentValue
        != SheetValue.Expanded) (padding + 20.dp) else 10.dp)
    val tabs = listOf("Location", "Main Tags", "Advance")
    val selectedI = remember { mutableIntStateOf(1) }

    BottomSheetScaffold(
        scaffoldState = st,
        containerColor = Color.Transparent,
        sheetContainerColor = MaterialTheme.colorScheme.surfaceContainer.copy(0.8f),
        sheetPeekHeight = 60.dp + padding,
        sheetShadowElevation = 0.dp,
        sheetTonalElevation = 0.dp,
        sheetDragHandle = { },
        modifier = Modifier.padding(horizontal = 10.dp),
        sheetContent = {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton({

                }) {
                    Icon(delete_forever, stringResource(R.string.delete))
                }
                IconButton({

                }) {
                    Icon(draw, null)
                }
                IconButton({

                }) {
                    Icon(add_2, null)
                }
                IconButton({

                }) {
                    Icon(comment, null)
                }
                IconButton({
                    if (Settings.favorites.contains(path)) {
                        Settings.favorites.remove(path)
                        isInFavorites.value = false
                    } else {
                        Settings.favorites.add(0, path)
                        isInFavorites.value = true
                    }
                    Settings.save()
                }) {
                    Icon(if (isInFavorites.value) favorite else heart_plus, null)
                }
            }
            Spacer(Modifier.height(tabsSpacing.value))

            Column(
                Modifier.padding(10.dp)
            ) {
                var dt = exif.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL)
                if (dt.isNullOrBlank()) {
                    dt = Date(File(path).lastModified()).toString()
                } else {
                    dt += " (${exif.getAttribute(ExifInterface.TAG_OFFSET_TIME_ORIGINAL)})"
                }

                when (selectedI.intValue) {
                    0 -> {
                        val context = LocalContext.current
                        val ll = exif.latLong
                        val alt = exif.getAttributeDouble(ExifInterface.TAG_GPS_LONGITUDE, -99.0)
                        if (ll != null) {
                            EasySegmentedListItem(
                                my_location,
                                "Latitude: ${ll[0]}\nLongitude: ${ll[1]}"
                                        + "\nAltitude: ${if (alt != -99.0) alt else "null"}",
                                index = 0,
                                1
                            ) {
                                val intent = Intent(Intent.ACTION_VIEW,
                                    "https://maps.google.com/maps/search/${ll[0]},${ll[1]}".toUri())
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                context.startActivity(intent)
                            }
                            Text(
                                "Tap to open with Google Maps",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 5.dp)
                            )
                        } else {
                            Text(
                                "No location tags 😵",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 5.dp)
                            )
                        }
                    }
                    1 -> {
                        val count = 5
                        val flashUsed = when(exif.getAttribute(ExifInterface.TAG_FLASH)?.toShort())  {
                            ExifInterface.FLAG_FLASH_FIRED -> " • Flash used"
                            ExifInterface.FLAG_FLASH_MODE_AUTO -> " • Flash used (Auto)"
                            else -> ""
                        }
                        val x = exif.getAttributeInt(ExifInterface.TAG_PIXEL_X_DIMENSION, 0)
                        val y = exif.getAttributeInt(ExifInterface.TAG_PIXEL_Y_DIMENSION, 0)
                        val mp = ((x * y) / 1000000f).roundToInt()
                        val mb = ((File(path).length() / 1024f / 1024f) * 100f).roundToInt() / 100f

                        Text(
                            dt,
                            textAlign = TextAlign.Center,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        )
                        EasySegmentedListItem(
                            null,
                            path.replace("/sdcard", "Internal storage")
                                .replace("/storage/emulated/0", "Internal storage"),
                            index = 0,
                            count
                        ) { }
                        val cmt = exif.getAttribute(ExifInterface.TAG_USER_COMMENT)
                        if (!cmt.isNullOrBlank()) {
                            EasySegmentedListItem(
                                comic_bubble,
                                "Comment: $cmt",
                                index = 1,
                                count
                            ) { }
                        }
                        EasySegmentedListItem(
                            pageless,
                            "$mb mb\n${x}x$y • $mp MP",
                            index = 2,
                            count
                        ) { }
                        EasySegmentedListItem(
                            camera,
                            "${exif.getAttribute(ExifInterface.TAG_F_NUMBER)}f"
                                    + " • ${exif.getAttributeDouble(ExifInterface.TAG_FOCAL_LENGTH, -99.0)} mm"
                                    + " • ${exif.getAttributeDouble(ExifInterface.TAG_EXPOSURE_BIAS_VALUE, -99.0)}ev"
                                    + " • ISO ${exif.getAttribute(ExifInterface.TAG_PHOTOGRAPHIC_SENSITIVITY)}"
                                    + " • ${exif.getAttributeDouble(ExifInterface.TAG_SHUTTER_SPEED_VALUE, -99.0)} ss"
                                    + flashUsed,
                            index = 3,
                            count
                        ) { }
                        EasySegmentedListItem(
                            photo_camera,
                            "${exif.getAttribute(ExifInterface.TAG_MAKE)}"
                                    + " • ${exif.getAttribute(ExifInterface.TAG_MODEL)}"
                                    + "\n${exif.getAttribute(ExifInterface.TAG_SOFTWARE)}",
                            index = 4,
                            count
                        ) { }
                    }
                    2 -> {
                        val count = 3
                        val sct = when(exif.getAttribute(ExifInterface.TAG_SCENE_CAPTURE_TYPE)?.toShort()) {
                            ExifInterface.SCENE_CAPTURE_TYPE_NIGHT -> "Night"
                            ExifInterface.SCENE_CAPTURE_TYPE_STANDARD -> "Standard"
                            ExifInterface.SCENE_CAPTURE_TYPE_PORTRAIT -> "Portrait"
                            ExifInterface.SCENE_CAPTURE_TYPE_LANDSCAPE -> "Landscape"
                            else -> "null"
                        }
                        val mtm = when(exif.getAttribute(ExifInterface.TAG_METERING_MODE)?.toShort()) {
                            ExifInterface.METERING_MODE_SPOT -> "Spot"
                            ExifInterface.METERING_MODE_OTHER -> "Other"
                            ExifInterface.METERING_MODE_PARTIAL -> "Partial"
                            ExifInterface.METERING_MODE_PATTERN -> "Pattern"
                            ExifInterface.METERING_MODE_AVERAGE -> "Average"
                            ExifInterface.METERING_MODE_MULTI_SPOT -> "Multi spot"
                            ExifInterface.METERING_MODE_CENTER_WEIGHT_AVERAGE -> "Center weight average"
                            ExifInterface.METERING_MODE_UNKNOWN -> "Unknown"
                            else -> "null"
                        }

                        EasySegmentedListItem(
                            null,
                            "Scene Capture Type: $sct\nMetering Mode: $mtm",
                            index = 0,
                            count
                        ) { }
                        EasySegmentedListItem(
                            lens_blur,
                            "Lens Make: ${exif.getAttribute(ExifInterface.TAG_LENS_MAKE)}"
                                    + "\nLens Model: ${exif.getAttribute(ExifInterface.TAG_LENS_MODEL)}"
                                    + "\nLens Specification: ${exif.getAttribute(ExifInterface.TAG_LENS_SPECIFICATION)}"
                                    + "\nLens Serial Number: ${exif.getAttribute(ExifInterface.TAG_LENS_SERIAL_NUMBER)}",
                            index = 1,
                            count
                        ) { }
                        EasySegmentedListItem(
                            null,
                            "Description:\n${exif.getAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION)}"
                                    + "\n\nMaker Note: ${exif.getAttribute(ExifInterface.TAG_MAKER_NOTE)}",
                            index = 2,
                            count
                        ) { }
                    }
                    else -> {
                        Text("Bro bro bro bro.....")
                    }
                }
            }

            SingleChoiceSegmentedButtonRow(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            ) {
                tabs.forEachIndexed { index, t ->
                    SegmentedButton(
                        selected = selectedI.intValue == index,
                        shape = SegmentedButtonDefaults.itemShape(index, 3),
                        onClick = { selectedI.intValue = index }
                    ) {
                        Text(t)
                    }
                }
            }

            Spacer(Modifier.height(padding + 5.dp))
        },
    ) { }
}
