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
    var allowRotationGesture = true

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
                }
            }
        } else {
            File("$appFolder/Settings").mkdirs()
            file.createNewFile()
            save()
        }
    }

    fun save() {

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