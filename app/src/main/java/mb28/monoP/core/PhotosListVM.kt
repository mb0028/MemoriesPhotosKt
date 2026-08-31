package mb28.monoP.core

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.ViewModel

class PhotosListVM : ViewModel() {
    private var _photosList = mutableListOf<Photo>()
    private var _photosInTrash = mutableListOf<Photo>()
    val photosList = _photosList
    val photosInTrash = _photosInTrash

    fun refreshList(context: Context, onRefresh: () -> Unit) {
        _photosList.clear()
        _photosInTrash.clear()

        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Video.Media._ID)

        context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            Settings.mediaStore_sql_sorting,

            )?.use { cursor ->
            val idc = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val pc = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idc)
                val path = cursor.getString(pc)
                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                val p = Photo(contentUri, path)
                if(Settings.onlyShowDCIM) {
                    if (path.contains("DCIM/")) {
                        _photosList += p
                    }
                } else {
                    photosList += p
                }
            }
        }
        onRefresh()
    }
}