package mb28.monoP.ui

import android.graphics.BitmapFactory
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import mb28.monoP.core.Settings
import mb28.monoP.core.createOrGetThumbnail
import mb28.monoP.core.openPhoto

@Composable
fun MoreTabFavorites() {
    val context = LocalActivity.current!!
    LazyHorizontalGrid(
        rows = GridCells.Fixed(3),
        contentPadding = PaddingValues(start = 10.dp, end = 250.dp),
    ) {
        items(Settings.favorites.count()) { i ->
            Image(
                BitmapFactory.decodeFile(createOrGetThumbnail(Settings.favorites[i])).asImageBitmap(),
                null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(140.dp)
                    .padding(2.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .combinedClickable(
                        onClick = {
                            openPhoto(Settings.favorites[i], context)
                        }
                    )
            )
        }
    }
}