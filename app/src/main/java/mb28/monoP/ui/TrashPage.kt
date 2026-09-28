package mb28.monoP.ui

import android.app.Activity
import android.graphics.BitmapFactory
import android.os.Environment
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mb28.monoP.core.TRASH_NAME
import mb28.monoP.core.createOrGetThumbnail
import mb28.monoP.core.createOrGetVideoThumbnail
import mb28.monoP.core.failedThumbnailIcon
import mb28.monoP.core.openPhoto
import mb28.monoP.core.openVideo
import java.io.File


var photosInTrash = mutableStateListOf<String>()

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TrashGrid(padding: PaddingValues, activity: Activity, showVideos: Boolean = false) {
    var refreshing by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            photosInTrash.clear()
            File(Environment.getExternalStorageDirectory().path).walkTopDown().forEach {
                if (it.path.contains(TRASH_NAME))
                    photosInTrash.add(it.path)
            }
            refreshing = false
        }
    }

    if (refreshing) {
        Box(Modifier.fillMaxSize(), Alignment.Center) {
            ContainedLoadingIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(bottom = 250.dp, top = padding.calculateTopPadding()),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(photosInTrash.count()) { i ->
                var thumb by remember { mutableStateOf<ImageBitmap?>(null) }
                LaunchedEffect(Unit)  {
                    val thumbPath = if (showVideos) createOrGetVideoThumbnail(photosInTrash[i])
                    else createOrGetThumbnail(photosInTrash[i])
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
                                if (showVideos)
                                    openVideo(photosInTrash[i], activity)
                                else
                                    openPhoto(photosInTrash[i], activity)
                            }
                        )
                )
            }
        }
    }

}
