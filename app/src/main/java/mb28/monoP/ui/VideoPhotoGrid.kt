package mb28.monoP.ui

import android.app.Activity
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mb28.monoP.core.createOrGetThumbnail
import mb28.monoP.core.createOrGetVideoThumbnail
import mb28.monoP.core.failedThumbnailIcon
import mb28.monoP.core.openPhoto
import mb28.monoP.core.openVideo

val photoVideoGridState = LazyGridState()

    @Composable
fun VideoPhotoGrid(padding: PaddingValues, activity: Activity, list: SnapshotStateList<String>, isVideos: Boolean = false) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(bottom = 250.dp, top = padding.calculateTopPadding()),
        horizontalArrangement = Arrangement.SpaceEvenly,
        state = photoVideoGridState
    ) {
        items(list.count()) { i ->
            var thumb by remember { mutableStateOf<ImageBitmap?>(null) }
            LaunchedEffect(Unit)  {
                val thumbPath = if (isVideos) createOrGetVideoThumbnail(list[i])
                    else createOrGetThumbnail(list[i])
                withContext(Dispatchers.IO) {
                    thumb = if (thumbPath != null) BitmapFactory.decodeFile(thumbPath)
                        .asImageBitmap() else null
                }
            }

            Image(
                thumb ?: failedThumbnailIcon(activity),
                null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f).padding(2.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .combinedClickable(
                        onClick = {
                            if (isVideos)
                                openVideo(list[i], activity)
                            else
                                openPhoto(list[i], activity)
                        }
                    )
            )
        }
    }
}
