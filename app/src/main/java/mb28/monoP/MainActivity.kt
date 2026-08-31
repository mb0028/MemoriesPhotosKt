package mb28.monoP

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.NavigationItemIconPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShortNavigationBar
import androidx.compose.material3.ShortNavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import mb28.crystalHomeKt.ui.icons.settings
import mb28.monoP.core.PhotosListVM
import mb28.monoP.core.Settings.load
import mb28.monoP.core.Settings.requestAllFilesAccessOrFinish
import mb28.monoP.icons.add_a_photo
import mb28.monoP.icons.gallery_thumbnail
import mb28.monoP.icons.gallery_thumbnail_filled
import mb28.monoP.icons.photo_album
import mb28.monoP.icons.photo_album_filled
import mb28.monoP.icons.photo_prints
import mb28.monoP.icons.photo_prints_filled
import mb28.monoP.ui.MoreTab
import mb28.monoP.ui.PhotosGrid
import mb28.monoP.ui.theme.MemoriesPhotosTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.isAppearanceLightStatusBars = true
        controller.isAppearanceLightNavigationBars = true
        window.isNavigationBarContrastEnforced = false

        val shortcut = ShortcutInfoCompat.Builder(this, "settings")
            .setShortLabel("Settings")
            .setIcon(IconCompat.createWithResource(this, R.mipmap.shortcut_settings_icon))
            .setIntent(Intent(Intent.ACTION_SHOW_APP_INFO))
            .build()
        ShortcutManagerCompat.pushDynamicShortcut(this, shortcut)

        requestAllFilesAccessOrFinish()
        load()

        if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_MEDIA_IMAGES), 0)
        }

        super.onCreate(savedInstanceState)

        val photosVM = ViewModelProvider(this)[PhotosListVM::class.java]
        photosVM.refreshList(this, {})

        setContent {
            MemoriesPhotosTheme {
                val selectedIndex = rememberSaveable { mutableIntStateOf(0) }
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    bottomBar = {
                        NavBar(selectedIndex)
                    },
                    topBar = {
                        TopAppBar(
                            windowInsets = WindowInsets(),
                            contentPadding = PaddingValues(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()),
                            scrollBehavior = scrollBehavior,
                            colors = TopAppBarDefaults.topAppBarColors(
                                scrolledContainerColor = TopAppBarDefaults.topAppBarColors().containerColor
                            ),
                            title = { Text(
                                when(selectedIndex.intValue) {
                                    0 -> stringResource(R.string.photos)
                                    1 -> stringResource(R.string.more)
                                    2 -> stringResource(R.string.albums)
                                    else -> ""
                                }
                            ) },
                            actions = {
                                IconButton({
                                    startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                                }) { Icon(settings, null) }
                            }
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            {
                                val intent = Intent(this, Camera::class.java)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            },
                            elevation = FloatingActionButtonDefaults.elevation(0.dp,0.dp)
                        ) {
                            Icon(add_a_photo, null)
                        }
                    }
                ) { padding ->
                    val tp = padding.calculateTopPadding()
                    when(selectedIndex.intValue) {
                        0 -> PhotosGrid(tp, photosVM)
                        1 -> MoreTab(
                            Modifier.padding(top = tp),
                            photosVM
                        )
                        else -> {}
                    }
                }
            }
        }
    }
}

@Composable
fun NavBar(selectedIndex: MutableIntState) {
    val tabs = listOf(stringResource(R.string.photos), stringResource(R.string.more), stringResource(R.string.albums))
    val icons = listOf(photo_prints, gallery_thumbnail, photo_album)
    val sIcons = listOf(photo_prints_filled, gallery_thumbnail_filled, photo_album_filled)

    ShortNavigationBar {
        tabs.forEachIndexed { i, item ->
            ShortNavigationBarItem(
                selected = selectedIndex.intValue == i,
                iconPosition = NavigationItemIconPosition.Start,
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
