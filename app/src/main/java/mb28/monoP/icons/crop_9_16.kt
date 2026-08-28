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
public val crop_9_16: ImageVector
    get() {
        if (_crop_9_16 != null) {
            return _crop_9_16!!
        }
        _crop_9_16 =
            ImageVector.Builder(
                name = "crop_9_16",
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
                        moveTo(9f, 21f)
                        quadTo(8.18f, 21f, 7.59f, 20.41f)
                        reflectiveQuadTo(7f, 19f)
                        verticalLineTo(5f)
                        quadTo(7f, 4.17f, 7.59f, 3.59f)
                        reflectiveQuadTo(9f, 3f)
                        horizontalLineToRelative(6f)
                        quadToRelative(0.83f, 0f, 1.41f, 0.59f)
                        reflectiveQuadTo(17f, 5f)
                        verticalLineTo(19f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(15f, 21f)
                        horizontalLineTo(9f)
                        close()
                        moveTo(9f, 5f)
                        verticalLineTo(19f)
                        horizontalLineToRelative(6f)
                        verticalLineTo(5f)
                        horizontalLineTo(9f)
                        close()
                        moveTo(9f, 5f)
                        verticalLineTo(19f)
                        verticalLineTo(5f)
                        close()
                    }
                }
                .build()
        return _crop_9_16!!
    }

private var _crop_9_16: ImageVector? = null
