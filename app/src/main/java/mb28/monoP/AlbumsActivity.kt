package mb28.monoP

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import mb28.monoP.ui.theme.MemoriesPhotosTheme
import java.io.File

const val EXTRA_ALBUM_FOLDER_PATH = "EXTRA_ALBUM_FOLDER_PATH"

class AlbumsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.isNavigationBarContrastEnforced = false

        val path = intent.getStringExtra(EXTRA_ALBUM_FOLDER_PATH)!!

        setContent {
            MemoriesPhotosTheme {
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
                var refreshing by remember { mutableStateOf(true) }
                val folderPhotoVideos = remember { mutableStateListOf<String>() }

                Scaffold(
                    Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    topBar = {
                        TopAppBar(
                            windowInsets = WindowInsets(),
                            contentPadding = PaddingValues(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()),
                            scrollBehavior = scrollBehavior,
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                                scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                            ),
                            title = {
                                Text(path.substring((path.lastIndexOf("/") + 1)) + " (${folderPhotoVideos.count()})")
                            },
                        )
                    },
                ) { paddingValues ->

                    LaunchedEffect(Unit) {
                        withContext(Dispatchers.IO) {
                             File(path).listFiles()?.let {
                                it.sortBy { file -> file.lastModified() }
                                it.reverse()
                                it.forEach { file ->
                                    val f = file.path
                                    if (!f.contains(TRASH_NAME) && (f.endsWith(".jpg") || f.endsWith(".jpeg") ||
                                        f.endsWith(".png") || f.endsWith(".mp4")))
                                        folderPhotoVideos.add(f)
                                }
                            }
                            refreshing = false
                        }
                    }
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxSize(),
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        contentPadding = paddingValues
                    ) {
                        items(folderPhotoVideos.count()) { i ->
                            val isVideo = folderPhotoVideos[i].endsWith(".mp4")
                            var thumb by remember { mutableStateOf<ImageBitmap?>(null) }
                            LaunchedEffect(Unit)  {
                                val thumbPath = if (isVideo) createOrGetVideoThumbnail(folderPhotoVideos[i])
                                    else createOrGetThumbnail(folderPhotoVideos[i])
                                withContext(Dispatchers.IO) {
                                    thumb = if (thumbPath != null) BitmapFactory.decodeFile(thumbPath)
                                        .asImageBitmap() else null
                                }
                            }

                            Image(
                                thumb ?: failedThumbnailIcon(this@AlbumsActivity),
                                null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .padding(2.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .combinedClickable(
                                        onClick = {
                                            if (isVideo)
                                                openVideo(folderPhotoVideos[i], this@AlbumsActivity)
                                            else
                                                openPhoto(folderPhotoVideos[i], this@AlbumsActivity)
                                        }
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}
