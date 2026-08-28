package mb28.monoP.icons


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
public val comic_bubble: ImageVector
    get() {
        if (_comic_bubble != null) {
            return _comic_bubble!!
        }
        _comic_bubble =
            ImageVector.Builder(
                name = "comic_bubble",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.Companion.NonZero,
                    ) {
                        moveTo(11f, 3.92f)
                        lineTo(8.93f, 6f)
                        horizontalLineTo(6f)
                        verticalLineTo(8.92f)
                        lineTo(3.93f, 11f)
                        lineTo(6f, 13.08f)
                        verticalLineTo(16f)
                        horizontalLineTo(8.93f)
                        lineTo(11f, 18.08f)
                        lineToRelative(2.5f, -2.5f)
                        lineToRelative(4.2f, 2.13f)
                        lineTo(15.55f, 13.52f)
                        lineTo(18.08f, 11f)
                        lineTo(16f, 8.92f)
                        verticalLineTo(6f)
                        horizontalLineTo(13.08f)
                        lineTo(11f, 3.92f)
                        close()
                        moveTo(11f, 1.1f)
                        lineTo(13.9f, 4f)
                        horizontalLineTo(18f)
                        verticalLineTo(8.1f)
                        lineTo(20.9f, 11f)
                        lineTo(18f, 13.9f)
                        lineToRelative(2.88f, 5.65f)
                        quadToRelative(0.18f, 0.32f, 0.1f, 0.64f)
                        reflectiveQuadTo(20.7f, 20.7f)
                        reflectiveQuadToRelative(-0.51f, 0.28f)
                        reflectiveQuadToRelative(-0.64f, -0.1f)
                        lineTo(13.9f, 18f)
                        lineTo(11f, 20.9f)
                        lineTo(8.1f, 18f)
                        horizontalLineTo(4f)
                        verticalLineTo(13.9f)
                        lineTo(1.1f, 11f)
                        lineTo(4f, 8.1f)
                        verticalLineTo(4f)
                        horizontalLineTo(8.1f)
                        lineTo(11f, 1.1f)
                        close()
                        moveTo(11f, 11f)
                        close()
                    }
                }
                .build()
        return _comic_bubble!!
    }

private var _comic_bubble: ImageVector? = null
