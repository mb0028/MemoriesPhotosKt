package mb28.monoP.ui

import android.graphics.BitmapFactory
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mb28.monoP.R
import mb28.monoP.core.Photo
import mb28.monoP.core.createOrGetThumbnail
import mb28.monoP.core.failedThumbnailIcon
import mb28.monoP.core.openPhoto

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PhotosGrid(padding: PaddingValues, list: SnapshotStateList<Photo>) {
    val context = LocalActivity.current!!

    LazyVerticalGrid(
        columns = GridCells.FixedSize(120.dp),
        contentPadding = padding,
    horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(list.count()) { i ->
            val path = createOrGetThumbnail(list[i].path)
            Image(
                if (path != null) BitmapFactory.decodeFile(path).asImageBitmap()
                    else failedThumbnailIcon(context),
                null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp, 120.dp)
                    .padding(vertical = 3.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .combinedClickable(
                        onClick = {
                            openPhoto(list[i], context)
                        }
                    )
            )
        }
    }
}
