package mb28.monoP.ui

import android.graphics.BitmapFactory
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mb28.monoP.core.PhotoIndexer
import mb28.monoP.core.PhotoIndexer.createOrGetThumbnail
import mb28.monoP.core.openPhoto

@Composable
fun PhotosGrid(modifier: Modifier = Modifier) {
    val context = LocalActivity.current!!
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(vertical = 200.dp),
    ) {
        val cr = context.contentResolver
        items(PhotoIndexer.photosList.count()) { i ->
            Image(
                BitmapFactory.decodeFile(createOrGetThumbnail(PhotoIndexer.photosList[i].path))
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
                            openPhoto(PhotoIndexer.photosList[i].path, context)
                        }
                    )
            )
        }
    }
}

@Composable
fun Modifier.verticalColumnScrollbar(
    scrollState: ScrollState,
    width: Dp = 4.dp,
    showScrollBarTrack: Boolean = true,
    scrollBarTrackColor: Color = Color.Gray,
    scrollBarColor: Color = Color.Black,
    scrollBarCornerRadius: Float = 4f,
    endPadding: Float = 12f
): Modifier {
    return drawWithContent {
        // Draw the column's content
        drawContent()
        // Dimensions and calculations
        val viewportHeight = this.size.height
        val totalContentHeight = scrollState.maxValue.toFloat() + viewportHeight
        val scrollValue = scrollState.value.toFloat()
        // Compute scrollbar height and position
        val scrollBarHeight =
            (viewportHeight / totalContentHeight) * viewportHeight
        val scrollBarStartOffset =
            (scrollValue / totalContentHeight) * viewportHeight
        // Draw the track (optional)
        if (showScrollBarTrack) {
            drawRoundRect(
                cornerRadius = CornerRadius(scrollBarCornerRadius),
                color = scrollBarTrackColor,
                topLeft = Offset(this.size.width - endPadding, 0f),
                size = Size(width.toPx(), viewportHeight),
            )
        }
        // Draw the scrollbar
        drawRoundRect(
            cornerRadius = CornerRadius(scrollBarCornerRadius),
            color = scrollBarColor,
            topLeft = Offset(this.size.width - endPadding, scrollBarStartOffset),
            size = Size(width.toPx(), scrollBarHeight)
        )
    }
}