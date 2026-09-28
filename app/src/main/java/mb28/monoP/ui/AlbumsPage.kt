package mb28.monoP.ui

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mb28.monoP.AlbumsActivity
import mb28.monoP.EXTRA_ALBUM_FOLDER_PATH
import mb28.monoP.core.TRASH_NAME
import mb28.monoP.core.createOrGetThumbnail
import mb28.monoP.core.createOrGetVideoThumbnail
import mb28.monoP.core.failedThumbnailIcon
import mb28.monoP.core.folders
import mb28.monoP.core.openPhoto
import mb28.monoP.core.openVideo
import mb28.monoP.core.photosList
import mb28.monoP.core.videosList
import java.io.File

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AlbumsPage(padding: PaddingValues, activity: Activity) {
    Column(
        Modifier.padding(top = padding.calculateTopPadding())
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.SpaceEvenly,
            contentPadding = PaddingValues(vertical = 150.dp)
        ) {
            items(folders.count()) { i ->
                val folder = folders.elementAt(i)
                var thumb by remember { mutableStateOf<ImageBitmap?>(null) }
                LaunchedEffect(Unit)  {
                    withContext(Dispatchers.IO) {
                        val files = File(folder).listFiles()?.toMutableList()
                        if (!files.isNullOrEmpty()) {
                            files.removeIf {
                                val path = it.path
                                path.contains(TRASH_NAME) || !(path.endsWith(".jpg") || path.endsWith(".jpeg")
                                        || path.endsWith(".png") || path.endsWith(".mp4"))
                            }
                            if (files.isNotEmpty()) {
                                files.sortBy { img -> img.lastModified() }
                                files.reverse()
                                val thumbPath = if (files.first().path.endsWith(".mp4"))
                                    createOrGetVideoThumbnail(files.first().path) else createOrGetThumbnail(files.first().path)
                                thumb = if (thumbPath != null) BitmapFactory.decodeFile(thumbPath)
                                        .asImageBitmap() else null
                            }
                        }
                    }
                }
                Card(
                    Modifier
                        .padding(3.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .clickable {
                            activity.startActivity(
                                Intent(activity, AlbumsActivity::class.java)
                                    .putExtra(EXTRA_ALBUM_FOLDER_PATH, folder)
                            )
                        },
                    colors = CardDefaults.cardColors(
                        MaterialTheme.colorScheme.surfaceContainerHigh
                    )
                ) {
                    Column {
                        Image(
                            thumb ?: failedThumbnailIcon(activity),
                            null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .padding(5.dp)
                                .clip(RoundedCornerShape(20.dp))
                        )
                        Text(
                            folder.substring(folder.lastIndexOf("/") + 1),
                            modifier = Modifier.padding(5.dp),
                            minLines = 2,
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }
}
