package mb28.monoP.ui.other

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale

@Composable
fun InfinityImage(
    image: ImageBitmap,
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
    filterQuality: FilterQuality = DefaultFilterQuality
) {
    Box(
        modifier
            .paint(
                BitmapPainter(
                    image,
                    filterQuality =  filterQuality,
                ),
                contentScale = contentScale,
            )
    )
}