package mb28.monoP.core

import android.app.Activity
import android.content.Intent
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.core.net.toUri
import mb28.monoP.R
import java.io.File

object Settings {
    const val appFolder = "/sdcard/DCIM/Memories Photos"
    const val appCacheFolder = "/sdcard/DCIM/Memories Photos/.temp"
    const val appCacheThumbsFolder = "/sdcard/DCIM/Memories Photos/.temp/Thumbnails"
    const val settingsFile = "$appFolder/Settings/Settings.txt"

    val favorites = mutableListOf<String>()
    var specialSectionsCount = 10
    var inAppPhotoViewer = true
    var inAppCamera = true
    var allowRotationGesture = true
    var onlyShowDCIM = true
    var trashInstead = true
    var useMediaStoreDelete = false

    var lockFocusWithShutter = true
    var lockExposureWithShutter = true
    var addCommentAfterCapture = false

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

        data += "\n[Camera Settings]\n"
        data += "[Cam Lock Focus]$lockFocusWithShutter\n"
        data += "[Cam Lock Expo]$lockExposureWithShutter\n"
        data += "[Cam Add Comment]$addCommentAfterCapture\n"

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