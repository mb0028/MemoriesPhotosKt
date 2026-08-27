package mb28.crystalHomeKt.ui.icons


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
public val format_paint: ImageVector
    get() {
        if (_format_paint != null) {
            return _format_paint!!
        }
        _format_paint =
            ImageVector.Builder(
                name = "format_paint",
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
                        moveTo(11f, 22f)
                        quadTo(10.18f, 22f, 9.59f, 21.41f)
                        reflectiveQuadTo(9f, 20f)
                        verticalLineTo(16f)
                        horizontalLineTo(6f)
                        quadTo(5.18f, 16f, 4.59f, 15.41f)
                        reflectiveQuadTo(4f, 14f)
                        verticalLineTo(7f)
                        quadTo(4f, 5.35f, 5.18f, 4.17f)
                        reflectiveQuadTo(8f, 3f)
                        horizontalLineTo(20f)
                        verticalLineTo(14f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(18f, 16f)
                        horizontalLineTo(15f)
                        verticalLineToRelative(4f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(13f, 22f)
                        horizontalLineTo(11f)
                        close()
                        moveTo(6f, 10f)
                        horizontalLineTo(18f)
                        verticalLineTo(5f)
                        horizontalLineTo(17f)
                        verticalLineTo(9f)
                        horizontalLineTo(15f)
                        verticalLineTo(5f)
                        horizontalLineTo(14f)
                        verticalLineTo(7f)
                        horizontalLineTo(12f)
                        verticalLineTo(5f)
                        horizontalLineTo(8f)
                        quadTo(7.18f, 5f, 6.59f, 5.59f)
                        quadTo(6f, 6.18f, 6f, 7f)
                        verticalLineToRelative(3f)
                        close()
                        moveToRelative(0f, 4f)
                        horizontalLineTo(18f)
                        verticalLineTo(12f)
                        horizontalLineTo(6f)
                        verticalLineToRelative(2f)
                        close()
                        moveToRelative(0f, 0f)
                        verticalLineTo(12f)
                        verticalLineToRelative(2f)
                        close()
                    }
                }
                .build()
        return _format_paint!!
    }

private var _format_paint: ImageVector? = null
