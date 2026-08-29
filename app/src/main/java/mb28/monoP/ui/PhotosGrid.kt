package mb28.monoP.ui

import android.graphics.BitmapFactory
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import mb28.monoP.core.PhotosListVM
import mb28.monoP.core.createOrGetThumbnail
import mb28.monoP.core.openPhoto

@Composable
fun PhotosGrid(photosVM: PhotosListVM) {
    val context = LocalActivity.current!!
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(vertical = 200.dp),
    ) {
        val cr = context.contentResolver
        items(photosVM.photosList.count()) { i ->
            Image(
                BitmapFactory.decodeFile(createOrGetThumbnail(photosVM.photosList[i].path))
                    .asImageBitmap(),
                null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .height(140.dp)
                    .padding(2.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .combinedClickable(
                        onClick = {
                            openPhoto(photosVM.photosList[i].path, context)
                        }
                    )
            )
        }
    }
}
