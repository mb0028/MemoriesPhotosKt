package mb28.monoP

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import mb28.monoP.icons.arrow_back
import mb28.monoP.icons.delete_forever
import mb28.monoP.ui.TrashGrid
import mb28.monoP.ui.popups.ClearTrashPopup
import mb28.monoP.ui.theme.MemoriesPhotosTheme

class TrashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.isNavigationBarContrastEnforced = false

        setContent {
            MemoriesPhotosTheme {
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
                Scaffold(
                    modifier = Modifier.fillMaxSize()
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
                                Text("Trash")
                            },
                            navigationIcon = {
                                IconButton(
                                    { finish() },
                                    colors = IconButtonDefaults.iconButtonColors().copy(
                                        MaterialTheme.colorScheme.surfaceContainerHigh
                                    ),
                                    modifier = Modifier.padding(horizontal = 15.dp)
                                ) {
                                    Icon(
                                        arrow_back,
                                        contentDescription = null
                                    )
                                }
                            },
                            actions = {
                                var clearDia by remember { mutableStateOf(false) }
                                IconButton({
                                    clearDia = true
                                }) { Icon(delete_forever, null) }
                                if (clearDia) {
                                    ClearTrashPopup {
                                        clearDia = false
                                        if (it) {
                                            Toast.makeText(this@TrashActivity, "Cleared trash",
                                                Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    TrashGrid(innerPadding, this)
                }
            }
        }
    }
}
