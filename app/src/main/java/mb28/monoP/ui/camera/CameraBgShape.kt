package mb28.monoP.ui.camera

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.star
import androidx.graphics.shapes.toPath

@Composable
fun CameraBgShape(modifier: Modifier = Modifier, rotation: Int = 0, scale: Dp = 400.dp) {
    val col = MaterialTheme.colorScheme.secondaryContainer
    val col2 = MaterialTheme.colorScheme.primaryContainer
    Box(
        modifier = modifier
            .drawWithCache {
                val brush = Brush.radialGradient(listOf(
                    col2,
                    col,
                ))
                val roundedPolygon = RoundedPolygon.star(
                    12,
                    radius = size.minDimension / 2,
                    innerRadius = size.minDimension / 2.5f,
                    centerX = size.width / 2,
                    centerY = size.height / 2,
                    rounding = CornerRounding(80f)
                )
                val roundedPolygonPath = roundedPolygon.toPath().asComposePath()
                onDrawBehind {
                    drawPath(roundedPolygonPath, brush = brush)
                }
            }
            .size(scale)
    )
}
