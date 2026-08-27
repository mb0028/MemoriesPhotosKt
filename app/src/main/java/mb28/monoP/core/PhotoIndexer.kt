package mb28.monoP.core

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Size
import androidx.core.content.FileProvider
import mb28.monoP.EXTRA_PATH
import mb28.monoP.PhotoViewerActivity
import mb28.monoP.core.Settings.inAppPhotoViewer
import java.io.File
import java.io.FileOutputStream

fun openPhoto(path: String, context: Activity) {
    if (inAppPhotoViewer) {
        val intent = Intent(context, PhotoViewerActivity::class.java)
            .putExtra(EXTRA_PATH, path)
        context.startActivity(intent)
    } else {
        val intent = Intent(Intent.ACTION_VIEW)
            .setDataAndType(
                FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.monop",
                    File(path)
                ), "image/*"
            )
        context.startActivity(intent)
    }
}

data class Photo(
    val uri: Uri,
    val path : String,
    val date: String,
    val id: Long,
)

object PhotoIndexer {
    val photosList = mutableListOf<Photo>()

    fun getPhotos(context: Context) {
        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DATE_MODIFIED,
            MediaStore.Video.Media._ID)

        context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            "${MediaStore.Images.Media.DATE_TAKEN} DESC"
        )?.use { cursor ->
            val idc = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val pc = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            val dmc = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idc)
                val path = cursor.getString(pc)
                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                photosList += Photo(
                    contentUri,
                    path,
                    cursor.getString(dmc),
                    id
                )
            }
        }
    }

    fun createOrGetThumbnail(path: String): String {
        val pathHash = path.hashCode()
        val thumbnailFile = File("${Settings.appCacheThumbsFolder}/$pathHash.jpeg")
        if (!thumbnailFile.exists()) {
            thumbnailFile.createNewFile()
            val t = ThumbnailUtils.createImageThumbnail(File(path),
                Size(350, 500), null)
            val outputStream = FileOutputStream(thumbnailFile)
            t.compress(Bitmap.CompressFormat.JPEG, 60, outputStream)
            outputStream.flush()
            outputStream.close()
        }
        return thumbnailFile.path
    }
}