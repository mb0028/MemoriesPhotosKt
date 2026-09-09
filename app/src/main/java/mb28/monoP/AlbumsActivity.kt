package mb28.monoP

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import mb28.monoP.core.folders
import mb28.monoP.ui.PhotosGrid
import mb28.monoP.ui.VideosGrid
import mb28.monoP.ui.theme.MemoriesPhotosTheme

const val EXTRA_ALBUM_FOLDER_PATH = "EXTRA_ALBUM_FOLDER_PATH"
const val EXTRA_IS_VIDEO_ALBUM = "EXTRA_IS_VIDEO_ALBUM"

class AlbumsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        with(window) {
            window.isNavigationBarContrastEnforced = false
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        }
        super.onCreate(savedInstanceState)

        val path = intent.getStringExtra(EXTRA_ALBUM_FOLDER_PATH)!!
        val isVideo = intent.getBooleanExtra(EXTRA_IS_VIDEO_ALBUM, false)

        setContent {
            MemoriesPhotosTheme {
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
                Scaffold(
                    Modifier.fillMaxSize()
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
                                Text(path.substring((path.lastIndexOf("/") + 1)))
                            },
                        )
                    },
                ) { innerPadding ->
                    if (isVideo) {
                        VideosGrid(innerPadding, folders[path]!!.toMutableStateList())
                    } else {
                        PhotosGrid(innerPadding, folders[path]!!.toMutableStateList())
                    }
                }
            }
        }
    }
}
