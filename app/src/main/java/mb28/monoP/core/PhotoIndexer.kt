package mb28.monoP.core

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.provider.MediaStore
import android.util.Size
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.exifinterface.media.ExifInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mb28.monoP.PhotoViewerActivity
import mb28.monoP.R
import mb28.monoP.core.Settings.inAppPhotoViewer
import java.io.File
import java.io.FileOutputStream

data class Video(
    val path: String,
    val duration: Long
)

var folders = mutableStateSetOf<String>()
var photosList = mutableStateListOf<String>()
var videosList = mutableStateListOf<Video>()

fun refreshPhotosLists(context: Context) {
    val tf = mutableSetOf<String>()
    val tp = mutableListOf<String>()
    val tv = mutableListOf<Video>()

    context.contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        arrayOf(MediaStore.MediaColumns.DATA),
        null,
        null,
        Settings.mediaStore_sql_sorting,

        )?.use { cursor ->
        val pc = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

        while (cursor.moveToNext()) {
            val path = cursor.getString(pc)

            if (Settings.onlyShowDCIM) {
                if (path.contains("DCIM/")) tp += path
            } else tp += path
            File(path).parent?.let { tf.add(it) }
        }
    }

    context.contentResolver.query(
        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
        arrayOf(
            MediaStore.MediaColumns.DATA,
            MediaStore.Video.Media.DURATION
        ),
        null,
        null,
        "${MediaStore.Video.Media.DATE_ADDED} DESC, ${MediaStore.Video.Media.DATE_MODIFIED} DESC",
        )?.use { cursor ->
        val dc = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
        val pc = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)

        while (cursor.moveToNext()) {
            val duration = cursor.getLong(dc)
            val path = cursor.getString(pc)

            val v = Video(path, duration)
            if (Settings.onlyShowDCIM) {
                if (path.contains("DCIM/")) tv += v
            } else tv += v
            File(path).parent?.let { tf.add(it) }
        }
    }

    photosList.clear(); photosList.addAll(tp)
    videosList.clear(); videosList.addAll(tv)
    folders.clear(); folders.addAll(tf.sorted())
}

fun openPhoto(path: String, context: Activity) {
    if (inAppPhotoViewer) {
        val intent = Intent(context, PhotoViewerActivity::class.java)
            .setData(Uri.fromFile(File(path)))
        context.startActivity(intent)
    } else {
        val intent = Intent(Intent.ACTION_VIEW)
            .setDataAndType(getUriImage(path, context), "image/*")
        context.startActivity(intent)
    }
}

fun openVideo(path: String, context: Activity) {
    val intent = Intent(Intent.ACTION_VIEW)
        .setDataAndType(getUriVideo(path, context), "video/*")
    context.startActivity(intent)
}

fun editComment(path: String, comment: String?) {
    val e = ExifInterface(path)
    e.setAttribute(ExifInterface.TAG_USER_COMMENT, comment)
    e.saveAttributes()
}

fun getComment(path: String, getNameIfNull: Boolean = true) : String =
    ExifInterface(path).getAttribute(ExifInterface.TAG_USER_COMMENT)
        ?: if (getNameIfNull) path.substring(path.lastIndexOf('/') + 1, path.lastIndexOf('.')) else ""

const val TRASH_NAME = ".monop-trashed-"

fun deleteOrTrash(path: String) {
    if (Settings.trashInstead) {
        val p = path.substring(0, path.lastIndexOf('/') + 1)
        val n = path.substring(path.lastIndexOf('/') + 1)
        File(path).renameTo(File(p + TRASH_NAME + n))
    } else {
        File(path).delete()
    }
}

fun restore(path: String) {
    File(path).renameTo(File(path.removePrefix(TRASH_NAME)))
}

private var pFailedThumbnailIcon: ImageBitmap? = null
fun failedThumbnailIcon(context: Context): ImageBitmap {
    if(pFailedThumbnailIcon == null) {
        pFailedThumbnailIcon = context.getDrawable(R.mipmap.app_icon_foreground)!!.toBitmap().asImageBitmap()
    }
    return pFailedThumbnailIcon!!
}

suspend fun createOrGetThumbnail(path: String): String? = withContext(Dispatchers.IO)  {
    val pathHash = path.hashCode()
    val thumbnailFile = File("${Settings.appCacheThumbsFolder}/$pathHash.jpeg")
    if (!thumbnailFile.exists()) {
        try {
            thumbnailFile.createNewFile()
            val t = ThumbnailUtils.createImageThumbnail(File(path),
                Size(400, 400), null)
            val outputStream = FileOutputStream(thumbnailFile)
            t.compress(Bitmap.CompressFormat.JPEG, 60, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (_: Exception) {
            return@withContext null
        }
    }
    return@withContext if (thumbnailFile.length() > 0) thumbnailFile.path else null
}

suspend fun createOrGetVideoThumbnail(path: String): String? = withContext(Dispatchers.IO) {
    val pathHash = path.hashCode()
    val thumbnailFile = File("${Settings.appCacheThumbsFolder}/$pathHash.jpeg")
    if (!thumbnailFile.exists()) {
        try {
            thumbnailFile.createNewFile()
            val t = ThumbnailUtils.createVideoThumbnail(File(path),
                Size(400, 400), null)
            val outputStream = FileOutputStream(thumbnailFile)
            t.compress(Bitmap.CompressFormat.JPEG, 60, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (_: Exception) {
            return@withContext null
        }
    }
    return@withContext if (thumbnailFile.length() > 0) thumbnailFile.path else null
}

fun getUriVideo(path: String, context: Context): Uri? {
    context.contentResolver.query(
        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
        arrayOf(MediaStore.Video.Media._ID),
        "${MediaStore.Video.Media.DATA} = ?",
        arrayOf(path),
        null
    )?.use {
        if (it.moveToFirst()) {
            val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Video.Media._ID))
            return ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
        }
    }
    return null
}

fun getUriImage(path: String, context: Context): Uri? {
    context.contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        arrayOf(MediaStore.Images.Media._ID),
        "${MediaStore.Images.Media.DATA} = ?",
        arrayOf(path),
        null
    )?.use {
        if (it.moveToFirst()) {
            val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
            return ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
        }
    }
    return null
}
