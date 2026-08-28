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
public val pageless: ImageVector
    get() {
        if (_pageless != null) {
            return _pageless!!
        }
        _pageless =
            ImageVector.Builder(
                name = "pageless",
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
                        moveTo(10f, 22f)
                        horizontalLineTo(3f)
                        quadTo(2.18f, 22f, 1.59f, 21.41f)
                        reflectiveQuadTo(1f, 20f)
                        verticalLineTo(14f)
                        horizontalLineTo(3f)
                        verticalLineToRelative(6f)
                        horizontalLineToRelative(7f)
                        verticalLineToRelative(2f)
                        close()
                        moveToRelative(4f, 0f)
                        verticalLineTo(20f)
                        horizontalLineToRelative(7f)
                        verticalLineTo(14f)
                        horizontalLineToRelative(2f)
                        verticalLineToRelative(6f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(21f, 22f)
                        horizontalLineTo(14f)
                        close()
                        moveTo(1f, 10f)
                        verticalLineTo(4f)
                        quadTo(1f, 3.17f, 1.59f, 2.59f)
                        reflectiveQuadTo(3f, 2f)
                        horizontalLineToRelative(7f)
                        verticalLineTo(4f)
                        horizontalLineTo(3f)
                        verticalLineToRelative(6f)
                        horizontalLineTo(1f)
                        close()
                        moveToRelative(20f, 0f)
                        verticalLineTo(4f)
                        horizontalLineTo(14f)
                        verticalLineTo(2f)
                        horizontalLineToRelative(7f)
                        quadToRelative(0.83f, 0f, 1.41f, 0.59f)
                        reflectiveQuadTo(23f, 4f)
                        verticalLineToRelative(6f)
                        horizontalLineTo(21f)
                        close()
                    }
                }
                .build()
        return _pageless!!
    }

private var _pageless: ImageVector? = null
