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
import androidx.compose.runtime.mutableStateMapOf
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import mb28.monoP.EXTRA_PATH
import mb28.monoP.PhotoViewerActivity
import mb28.monoP.core.Settings.inAppPhotoViewer
import java.io.File
import java.io.FileOutputStream


data class Photo(
    val uri : Uri,
    val path : String,
    val duration: Long = 0L
)


var folders = mutableStateMapOf<String, MutableList<Photo>>()
var photosList = mutableStateListOf<Photo>()
var videosList = mutableStateListOf<Photo>()
var photosInTrash = mutableStateListOf<Photo>()

fun refreshPhotosLists(context: Context) {
    photosList.clear()
    videosList.clear()
    folders.clear()
    videosList.clear()
    photosInTrash.clear()

    val projection = arrayOf(
        MediaStore.MediaColumns.DATA,
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DATE_ADDED,
        MediaStore.Images.Media.DATE_MODIFIED,
        MediaStore.Images.Media.DATE_TAKEN,
    )

    val projectionVideo = arrayOf(
        MediaStore.MediaColumns.DATA,
        MediaStore.Video.Media._ID,
        MediaStore.Video.Media.DURATION,
        MediaStore.Video.Media.DATE_ADDED,
        MediaStore.Video.Media.DATE_MODIFIED
    )

    context.contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        projection,
        null,
        null,
        Settings.mediaStore_sql_sorting,

        )?.use { cursor ->
        val idc = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
        val pc = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(idc)
            val path = cursor.getString(pc)
            val contentUri: Uri = ContentUris.withAppendedId(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                id
            )

            val p = Photo(contentUri, path)
            if(Settings.onlyShowDCIM) {
                if (path.contains("DCIM/")) {
                    photosList += p
                }
            } else {
                photosList += p
            }
            val parentPath = File(path).parent!!
            if (folders[parentPath] == null) {
                folders[parentPath] = mutableListOf()
            }
            folders[parentPath]!!.add(p)
        }
    }

    context.contentResolver.query(
        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
        projectionVideo,
        null,
        null,
        "${MediaStore.Video.Media.DATE_ADDED} DESC, ${MediaStore.Video.Media.DATE_MODIFIED} DESC",
        )?.use { cursor ->
        val idc = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
        val dc = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
        val pc = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)

        while (cursor.moveToNext()) {
            val duration = cursor.getLong(dc)
            val path = cursor.getString(pc)
            val id = cursor.getLong(idc)
            val contentUri: Uri = ContentUris.withAppendedId(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                id
            )

            val v = Photo(contentUri, path, duration)
            if (Settings.onlyShowDCIM) {
                if (path.contains("DCIM/")) {
                    videosList += v
                }
            } else {
                videosList += v
            }
            val parentPath = File(path).parent!!
            if (folders[parentPath] == null) {
                folders[parentPath] = mutableListOf()
            }
            folders[parentPath]!!.add(v)
        }
    }
}

fun openPhoto(photo: Photo, context: Activity) {
    if (inAppPhotoViewer) {
        val intent = Intent(context, PhotoViewerActivity::class.java)
            .putExtra(EXTRA_PATH, photo.path)
        context.startActivity(intent)
    } else {
        val intent = Intent(Intent.ACTION_VIEW)
            .setDataAndType(photo.uri, "image/*")
        context.startActivity(intent)
    }
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

fun createOrGetVideoThumbnail(path: String): String {
    val pathHash = path.hashCode()
    val thumbnailFile = File("${Settings.appCacheThumbsFolder}/$pathHash.jpeg")
    if (!thumbnailFile.exists()) {
        thumbnailFile.createNewFile()
        val t = ThumbnailUtils.createVideoThumbnail(File(path),
            Size(350, 500), null)
        val outputStream = FileOutputStream(thumbnailFile)
        t.compress(Bitmap.CompressFormat.JPEG, 60, outputStream)
        outputStream.flush()
        outputStream.close()
    }
    return thumbnailFile.path
}

