package mb28.monoP.ui

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import mb28.monoP.AlbumsActivity
import mb28.monoP.EXTRA_ALBUM_FOLDER_PATH
import mb28.monoP.core.createOrGetThumbnail
import mb28.monoP.core.failedThumbnailIcon
import mb28.monoP.core.folders

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AlbumsPage(padding: PaddingValues) {
    val context = LocalActivity.current!!

    Column(
        Modifier.padding(top = padding.calculateTopPadding())
    ) {
        LazyVerticalGrid(
            columns = GridCells.FixedSize(180.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            contentPadding = PaddingValues(vertical = 150.dp)
        ) {
            items(folders.count()) { i ->
                val f = folders.keys.elementAt(i)
                val t = folders.values.elementAt(i).first()
                Column(
                    Modifier.background(
                        MaterialTheme.colorScheme.surfaceContainerHigh,
                        RoundedCornerShape(20.dp)
                    )
                ) {
                    val path = createOrGetThumbnail(t.path)
                    Image(
                        if (path != null) BitmapFactory.decodeFile(path).asImageBitmap()
                        else failedThumbnailIcon(context),
                        null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(180.dp, 180.dp)
                            .padding(5.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .combinedClickable(
                                onClick = {
                                    val intent = Intent(context, AlbumsActivity::class.java)
                                        .putExtra(EXTRA_ALBUM_FOLDER_PATH, f)
                                    context.startActivity(intent)
                                }
                            )
                    )
                    Text(
                        f.substring((f.lastIndexOf("/") + 1)),
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
                        minLines = 2,
                        maxLines = 2
                    )
                }

            }
        }
    }

}
