package mb28.monoP

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import mb28.monoP.core.Settings
import mb28.monoP.core.applyExifRotation
import mb28.monoP.core.deleteOrTrash
import mb28.monoP.core.photosList
import mb28.monoP.icons.arrow_back
import mb28.monoP.icons.delete_forever
import mb28.monoP.ui.theme.MemoriesPhotosTheme
import kotlin.random.Random

class KodActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.isNavigationBarContrastEnforced = false

        setContent {
            MemoriesPhotosTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    topBar = {
                        TopAppBar(
                            windowInsets = WindowInsets(),
                            contentPadding = PaddingValues(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()),
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                                scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                            ),
                            title = {
                                Text("Keep Or Delete")
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
                            }
                        )
                    }
                ) {
                    val r = remember { Random(System.currentTimeMillis()) }
                    var current by remember { mutableIntStateOf(r.nextInt(0, photosList.count())) }

                    Column(
                        Modifier.fillMaxSize(),
                        Arrangement.Center,
                        Alignment.CenterHorizontally
                    ) {
                        val path = photosList[current]
                        Image(
                            BitmapFactory.decodeFile(path).applyExifRotation(path).asImageBitmap(),
                            null,
                            modifier = Modifier.aspectRatio(1f).padding(5.dp)
                                .clip(RoundedCornerShape(25.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(Modifier.height(80.dp))

                        Row(
                            Modifier.fillMaxWidth().padding(horizontal = 25.dp),
                            Arrangement.SpaceBetween
                        ) {
                            FloatingActionButton(
                                {
                                    deleteOrTrash(photosList[current])
                                    Toast.makeText(this@KodActivity, "${if (Settings.trashInstead) "Trashed" else "Deleted"} $photosList[current]",
                                        Toast.LENGTH_SHORT).show()
                                    photosList.removeAt(current)
                                    current = r.nextInt(0, photosList.count())
                                },
                                elevation = FloatingActionButtonDefaults.elevation(0.dp,0.dp,0.dp,0.dp)
                            ) {
                                Icon(delete_forever, null)
                            }

                            FloatingActionButton(
                                {
                                    current = r.nextInt(0, photosList.count())
                                },
                                elevation = FloatingActionButtonDefaults.elevation(0.dp,0.dp,0.dp,0.dp)
                            ) {
                                Icon(arrow_back, null)
                            }
                        }
                    }

                }
            }
        }
    }
}
