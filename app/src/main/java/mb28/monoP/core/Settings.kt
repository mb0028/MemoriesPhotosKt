@file:Suppress("ConstPropertyName")

package mb28.monoP.core

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import mb28.monoP.R
import java.io.File

@SuppressLint("SdCardPath")
object Settings {

    const val appFolder = "/sdcard/DCIM/Memories Photos"
    const val appCacheFolder = "/sdcard/DCIM/Memories Photos/.temp"
    const val appCacheThumbsFolder = "/sdcard/DCIM/Memories Photos/.temp/Thumbnails"
    const val settingsFile = "$appFolder/Settings/Settings.txt"

    val favorites = mutableStateListOf<String>()
    var specialSectionsCount by mutableIntStateOf(10)
    var inAppPhotoViewer by mutableStateOf(true)
    var inAppCamera by mutableStateOf(true)
    var allowRotationGesture by mutableStateOf(true)
    var onlyShowDCIM by mutableStateOf(false)
    var trashInstead by mutableStateOf(true)
    var useMediaStoreDelete by mutableStateOf(false)

    var lockFocusWithShutter by mutableStateOf(true)
    var lockExposureWithShutter by mutableStateOf(true)
    var addCommentAfterCapture by mutableStateOf(true)
    var imageCaptureMode by mutableIntStateOf(1)
    var imageCaptureFlashMode by mutableIntStateOf(2)
    var startCameraMode by mutableIntStateOf(0)
    var cameraAspect by mutableIntStateOf(0)
    var mediaStore_sql_sorting by mutableStateOf("${MediaStore.Images.Media.DATE_ADDED} DESC, ${MediaStore.Images.Media.DATE_MODIFIED} DESC, ${MediaStore.Images.Media.DATE_TAKEN} DESC")

    fun load() {
        favorites.clear()
        val a = File(appFolder)
        val ac = File(appCacheFolder)
        val atc = File(appCacheThumbsFolder)
        if (!a.exists() || !ac.exists() || !atc.exists()) {
            atc.mkdirs()
        }

        val file = File(settingsFile)
        if (file.exists()) {
            val data = file.readLines()
            data.forEach { s ->
                when {
                    s.startsWith("[FAV]") -> {
                        val path = s.removePrefix("[FAV]")
                        if (File(path).exists()) {
                            favorites.add(path)
                        }
                    }
                    s.startsWith("[SSC]") -> specialSectionsCount = s.removePrefix("[SSC]").toInt()
                    s.startsWith("[ROTA]") -> allowRotationGesture = s.removePrefix("[ROTA]").toBooleanStrict()
                    s.startsWith("[OSDCIM]") -> onlyShowDCIM = s.removePrefix("[OSDCIM]").toBooleanStrict()
                    s.startsWith("[InAppPV]") -> inAppPhotoViewer = s.removePrefix("[InAppPV]").toBooleanStrict()
                    s.startsWith("[InAppCam]") -> inAppCamera = s.removePrefix("[InAppCam]").toBooleanStrict()
                    s.startsWith("[IT]") -> trashInstead = s.removePrefix("[IT]").toBooleanStrict()
                    s.startsWith("[MSD]") -> useMediaStoreDelete = s.removePrefix("[MSD]").toBooleanStrict()
                    s.startsWith("[Cam Lock Focus]") -> lockFocusWithShutter = s.removePrefix("[Cam Lock Focus]").toBooleanStrict()
                    s.startsWith("[Cam Lock Expo]") -> lockExposureWithShutter = s.removePrefix("[Cam Lock Expo]").toBooleanStrict()
                    s.startsWith("[Cam Add Comment]") -> addCommentAfterCapture = s.removePrefix("[Cam Add Comment]").toBooleanStrict()
                    s.startsWith("[Cam Capture Mode]") -> imageCaptureMode = s.removePrefix("[Cam Capture Mode]").toInt().coerceIn(0, 2)
                    s.startsWith("[Cam Flash Mode]") -> imageCaptureFlashMode = s.removePrefix("[Cam Flash Mode]").toInt().coerceIn(0, 4)
                    s.startsWith("[Cam Start Mode]") -> startCameraMode = s.removePrefix("[Cam Start Mode]").toInt().coerceIn(0, 2)
                    s.startsWith("[Cam Aspect]") -> cameraAspect = s.removePrefix("[Cam Aspect]").toInt().coerceIn(0, 4)
                    s.startsWith("[Sorting]") -> mediaStore_sql_sorting = s.removePrefix("[Sorting]")
                }
            }
        } else {
            File("$appFolder/Settings").mkdirs()
            file.createNewFile()
            save()
        }
    }

    fun save() {
        var data = "[Settings]\n"
        data += "[InAppCam]$inAppCamera\n"
        data += "[InAppPV]$inAppPhotoViewer\n"
        data += "[ROTA]$allowRotationGesture\n"
        data += "[OSDCIM]$onlyShowDCIM\n"
        data += "[IT]$trashInstead\n"
        data += "[MSD]$useMediaStoreDelete\n"
        data += "[SSC]$specialSectionsCount\n"
        data += "[Sorting]$mediaStore_sql_sorting\n"

        data += "\n[Camera Settings]\n"
        data += "[Cam Lock Focus]$lockFocusWithShutter\n"
        data += "[Cam Lock Expo]$lockExposureWithShutter\n"
        data += "[Cam Add Comment]$addCommentAfterCapture\n"
        data += "[Cam Capture Mode]$imageCaptureMode\n"
        data += "[Cam Flash Mode]$imageCaptureFlashMode\n"
        data += "[Cam Start Mode]$startCameraMode\n"
        data += "[Cam Aspect]$cameraAspect\n"

        data += "\n[Favorites]\n"
        favorites.forEach {
            data += "[FAV]$it\n"
        }

        val file = File(settingsFile)
        file.writeText(data)
    }

    fun Activity.requestAllFilesAccessOrFinish() {
        if (!Environment.isExternalStorageManager()) {
            Toast.makeText(this, getString(R.string.needs_all_files_access), Toast.LENGTH_LONG).show()
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                .setData("package:$packageName".toUri())
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}