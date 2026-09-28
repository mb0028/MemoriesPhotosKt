package mb28.monoP

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationItemIconPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShortNavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mb28.monoP.core.Settings.load
import mb28.monoP.core.Settings.requestAllFilesAccessOrFinish
import mb28.monoP.core.refreshPhotosLists
import mb28.monoP.icons.add_a_photo
import mb28.monoP.icons.delete_forever
import mb28.monoP.icons.photo_album
import mb28.monoP.icons.photo_album_filled
import mb28.monoP.icons.photo_prints
import mb28.monoP.icons.photo_prints_filled
import mb28.monoP.icons.settings
import mb28.monoP.ui.AlbumsPage
import mb28.monoP.ui.TrashGrid
import mb28.monoP.ui.VideoPhotoGrid
import mb28.monoP.ui.theme.MemoriesPhotosTheme
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : ComponentActivity() {
    var isRefreshing by mutableStateOf(false)

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        window.isNavigationBarContrastEnforced = false

        val shortcut = ShortcutInfoCompat.Builder(this, "settings")
            .setShortLabel("Settings")
            .setIcon(IconCompat.createWithResource(this, R.mipmap.shortcut_settings_icon))
            .setIntent(Intent(Intent.ACTION_SHOW_APP_INFO))
            .build()
        ShortcutManagerCompat.pushDynamicShortcut(this, shortcut)

        requestAllFilesAccessOrFinish()
        load()

        super.onCreate(savedInstanceState)

        setContent {
            MemoriesPhotosTheme {
                val selectedIndex = rememberSaveable { mutableIntStateOf(0) }
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    bottomBar = {
                        Box(
                            Modifier.fillMaxSize().navigationBarsPadding(),
                            Alignment.BottomCenter
                        ) {
                            NavBar(selectedIndex, this@MainActivity)
                        }
                    },
                    topBar = {
                        TopAppBar(
                            windowInsets = WindowInsets(),
                            contentPadding = PaddingValues(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()),
                            scrollBehavior = scrollBehavior,
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                                scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                            ),
                            title = { Text(
                                when(selectedIndex.intValue) {
                                    -1 -> stringResource(R.string.trash)
                                    0 -> stringResource(R.string.photos)
                                    1 -> stringResource(R.string.videos)
                                    else -> stringResource(R.string.albums)
                                }
                            ) },
                            actions = {
                                IconButton({
                                    startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                                }) { Icon(settings, null) }
                            }
                        )
                    }
                ) { padding ->
                    if (isRefreshing) {
                        Box(Modifier.fillMaxSize(), Alignment.Center) {
                            ContainedLoadingIndicator()
                        }
                    } else when(selectedIndex.intValue) {
                        -1 -> TrashGrid(padding, this)
                        0 -> VideoPhotoGrid(padding, this)
                        1 -> VideoPhotoGrid(padding, this, true)
                        else -> AlbumsPage(padding, this)
                    }
                }
            }
        }
    }

    override fun onResume() {
        lifecycleScope.launch {
            isRefreshing = true
            refreshPhotosLists(this@MainActivity)
            delay(50.milliseconds)
            isRefreshing = false
        }
        super.onResume()
    }
}

@Composable
fun NavBar(selectedIndex: MutableIntState, activity: Activity) {
    val tabs = listOf(stringResource(R.string.photos), stringResource(R.string.videos), stringResource(R.string.albums))
    val icons = remember { listOf(photo_prints, photo_prints, photo_album) }
    val sIcons = remember { listOf(photo_prints_filled, photo_prints_filled, photo_album_filled) }

    HorizontalFloatingToolbar(
        true,
        contentPadding = PaddingValues(5.dp),
        colors =  FloatingToolbarDefaults.standardFloatingToolbarColors(
            MaterialTheme.colorScheme.surfaceContainerLowest.copy(0.95f)
        ),
        leadingContent = {
            IconButton(
                { selectedIndex.intValue = -1 }
            ) { Icon(delete_forever, null) }
        },
        trailingContent = {
            IconButton(
                {
                    val intent = Intent(activity, Camera::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    activity.startActivity(intent)
                }
            ) {
                Icon(add_a_photo, null)
            }
        }
    ) {
        tabs.forEachIndexed { i, item ->
            ShortNavigationBarItem(
                selected = selectedIndex.intValue == i,
                iconPosition = NavigationItemIconPosition.Top,
                icon = {
                    Icon(
                        if (selectedIndex.intValue == i) sIcons[i] else icons[i],
                        null
                    )
                },
                label = { Text(item) },
                onClick = { selectedIndex.intValue = i }
            )
        }
    }
}
