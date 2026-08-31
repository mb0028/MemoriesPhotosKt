package mb28.monoP.ui.components

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.LocalActivity
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.exifinterface.media.ExifInterface
import mb28.monoP.icons.delete_forever
import mb28.monoP.icons.favorite
import mb28.monoP.icons.heart_plus
import mb28.monoP.R
import mb28.monoP.core.Settings
import mb28.monoP.core.deleteOrTrash
import mb28.monoP.core.editComment
import mb28.monoP.core.getComment
import mb28.monoP.icons.add_2
import mb28.monoP.icons.camera
import mb28.monoP.icons.comic_bubble
import mb28.monoP.icons.comment
import mb28.monoP.icons.draw
import mb28.monoP.icons.forest
import mb28.monoP.icons.lens_blur
import mb28.monoP.icons.my_location
import mb28.monoP.icons.pageless
import mb28.monoP.icons.photo_camera
import java.io.File
import java.util.Date
import kotlin.math.roundToInt

private const val QMARKS3 = "???"

@SuppressLint("SdCardPath")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewerBottomDrawer(path: String) {
    val context = LocalActivity.current!!
    val exif = ExifInterface(path)
    val padding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val st = rememberBottomSheetScaffoldState()
    val isInFavorites = remember { mutableStateOf(Settings.favorites.contains(path)) }

    val tabsSpacing = animateDpAsState(if (st.bottomSheetState.currentValue
        != SheetValue.Expanded) (padding + 20.dp) else 10.dp)
    val tabs = listOf("Location", "Main Tags", "Advance")
    val selectedI = remember { mutableIntStateOf(1) }

    var deleteDia by remember { mutableStateOf(false) }
    var editCommentDia by remember { mutableStateOf(false) }

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
                IconButton({ deleteDia = true }) {
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
                IconButton({ editCommentDia = true }) {
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
                    .clip(RoundedCornerShape(15.dp))
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
                        val f = exif.getAttribute(ExifInterface.TAG_F_NUMBER)
                        val iso = exif.getAttribute(ExifInterface.TAG_PHOTOGRAPHIC_SENSITIVITY)
                        val mm = exif.getAttributeDouble(ExifInterface.TAG_FOCAL_LENGTH, -99.0)
                        val ev = exif.getAttributeDouble(ExifInterface.TAG_EXPOSURE_BIAS_VALUE, -99.0)
                        val ss = exif.getAttributeDouble(ExifInterface.TAG_SHUTTER_SPEED_VALUE, -99.0)
                        val flashUsed = when(exif.getAttribute(ExifInterface.TAG_FLASH)?.toShort())  {
                            ExifInterface.FLAG_FLASH_FIRED -> " • Flash used"
                            ExifInterface.FLAG_FLASH_MODE_AUTO -> " • Flash used (Auto)"
                            else -> ""
                        }

                        val make = exif.getAttribute(ExifInterface.TAG_MAKE) ?: QMARKS3
                        val model = exif.getAttribute(ExifInterface.TAG_MODEL) ?: QMARKS3
                        val software = exif.getAttribute(ExifInterface.TAG_SOFTWARE) ?: QMARKS3

                        val x = exif.getAttributeInt(ExifInterface.TAG_PIXEL_X_DIMENSION, -1)
                        val y = exif.getAttributeInt(ExifInterface.TAG_PIXEL_Y_DIMENSION, -1)
                        val mp = ((x * y) / 1000000f).roundToInt()
                        val mb = ((File(path).length() / 1024f / 1024f) * 100f).roundToInt() / 100f
                        val cmt = exif.getAttribute(ExifInterface.TAG_USER_COMMENT)

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
                            "${x}x$y • $mp MP\n$mb mb",
                            index = 2,
                            count
                        ) { }

                        if (!f.isNullOrBlank() && !iso.isNullOrBlank() && ev != -99.0) {
                            EasySegmentedListItem(
                                camera,
                                "ISO $iso • ${f}f • $ev ev • $mm mm • $ss s$flashUsed",
                                index = 3,
                                count
                            ) { }
                        }

                        if (make != QMARKS3 || model != QMARKS3 || software != QMARKS3) {
                            EasySegmentedListItem(
                                photo_camera,
                                "$make • $model\n$software",
                                index = 4,
                                count
                            ) { }
                        }

                    }
                    2 -> {
                        val count = 4
                        val sct = when(exif.getAttribute(ExifInterface.TAG_SCENE_CAPTURE_TYPE)?.toShort()) {
                            ExifInterface.SCENE_CAPTURE_TYPE_NIGHT -> "Night"
                            ExifInterface.SCENE_CAPTURE_TYPE_STANDARD -> "Standard"
                            ExifInterface.SCENE_CAPTURE_TYPE_PORTRAIT -> "Portrait"
                            ExifInterface.SCENE_CAPTURE_TYPE_LANDSCAPE -> "Landscape"
                            else -> QMARKS3
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
                            else -> QMARKS3
                        }
                        val lensMake = exif.getAttribute(ExifInterface.TAG_LENS_MAKE)
                        val desc = exif.getAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION)
                        val makerNote = exif.getAttribute(ExifInterface.TAG_MAKER_NOTE)
                        val aperture = exif.getAttribute(ExifInterface.TAG_APERTURE_VALUE)
                        val apertureMax = exif.getAttribute(ExifInterface.TAG_MAX_APERTURE_VALUE)

                        if (sct != QMARKS3 || mtm != QMARKS3) {
                            EasySegmentedListItem(
                                null,
                                "Scene Capture Type: $sct\nMetering Mode: $mtm",
                                index = 0,
                                count
                            ) { }
                        }

                        if (!aperture.isNullOrBlank() || !apertureMax.isNullOrBlank()) {
                            EasySegmentedListItem(
                                forest,
                                "Aperture: $aperture\nMax Aperture: $apertureMax",
                                index = 1,
                                count
                            ) { }
                        }

                        if (!lensMake.isNullOrBlank()) {
                            EasySegmentedListItem(
                                lens_blur,
                                "Lens Make: $lensMake"
                                        + "\nLens Model: ${exif.getAttribute(ExifInterface.TAG_LENS_MODEL)}"
                                        + "\nLens Specification: ${exif.getAttribute(ExifInterface.TAG_LENS_SPECIFICATION)}"
                                        + "\nLens Serial Number: ${exif.getAttribute(ExifInterface.TAG_LENS_SERIAL_NUMBER)}",
                                index = 2,
                                count
                            ) { }
                        }

                        if (!desc.isNullOrBlank() || !makerNote.isNullOrBlank()) {
                            EasySegmentedListItem(
                                null,
                                "Description:\n$desc"
                                        + "\n\nMaker Note: $makerNote",
                                index = 3,
                                count
                            ) { }
                        }

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

    if (deleteDia) {
        val todText = if (Settings.trashInstead) stringResource(R.string.move_to_trash) else stringResource(R.string.delete)
        AlertDialog(
            { deleteDia = false },
            {
                Button({
                    deleteOrTrash(path)
                    Toast.makeText(context, "${if (Settings.trashInstead) "Trashed" else "Deleted"} $path",
                        Toast.LENGTH_SHORT).show()
                    deleteDia = false
                    context.finish()
                }) {
                    Text(todText)
                }
            },
            dismissButton = {
                OutlinedButton({ editCommentDia = false }) {
                    Text(stringResource(R.string.cancel))
                }
            },
            title = {
                Text("$todText?")
            },
            text = {
                Text(path)
            }
        )
    }

    if (editCommentDia) {
        val c = getComment(path, false)
        var newComment by remember { mutableStateOf(c) }
        AlertDialog(
            { editCommentDia = false },
            {
                Button({
                    if (newComment.isNotBlank()) {
                        editComment(path, newComment)
                    } else {
                        editComment(path, null)
                    }
                    editCommentDia = false
                }) {
                    Text(stringResource(R.string.save))
                }
            },
            dismissButton = {
                OutlinedButton({ editCommentDia = false }) {
                    Text(stringResource(R.string.cancel))
                }
            },
            title = {
                Text(stringResource(R.string.change_comment))
            },
            text = {
                OutlinedTextField(
                    newComment,
                    {newComment = it},
                    minLines = 3,
                    maxLines = 3,
                    shape = OutlinedTextFieldDefaults.roundedShape,
                    textStyle = TextStyle(
                        fontSize = 18.sp
                    ),
                    label = {
                        Text(stringResource(R.string.comment))
                    }
                )
            }
        )
    }

}
