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
public val forest: ImageVector
    get() {
        if (_forest != null) {
            return _forest!!
        }
        _forest =
            ImageVector.Builder(
                name = "forest",
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
                        moveTo(7f, 22f)
                        verticalLineTo(18f)
                        horizontalLineTo(0f)
                        lineTo(3.85f, 12f)
                        horizontalLineTo(2f)
                        lineTo(9f, 2f)
                        lineToRelative(3f, 4.3f)
                        lineTo(15f, 2f)
                        lineToRelative(7f, 10f)
                        horizontalLineTo(20.15f)
                        lineTo(24f, 18f)
                        horizontalLineTo(17f)
                        verticalLineToRelative(4f)
                        horizontalLineTo(13f)
                        verticalLineTo(18f)
                        horizontalLineTo(11f)
                        verticalLineToRelative(4f)
                        horizontalLineTo(7f)
                        close()
                        moveToRelative(9.73f, -6f)
                        horizontalLineToRelative(3.63f)
                        lineTo(16.48f, 10f)
                        horizontalLineToRelative(1.68f)
                        lineTo(15f, 5.5f)
                        lineTo(13.23f, 8.02f)
                        lineTo(16f, 12f)
                        horizontalLineTo(14.15f)
                        lineToRelative(2.58f, 4f)
                        close()
                        moveTo(3.65f, 16f)
                        horizontalLineToRelative(10.7f)
                        lineTo(10.48f, 10f)
                        horizontalLineToRelative(1.67f)
                        lineTo(9f, 5.5f)
                        lineTo(5.85f, 10f)
                        horizontalLineTo(7.53f)
                        lineTo(3.65f, 16f)
                        close()
                        moveToRelative(0f, 0f)
                        horizontalLineTo(7.53f)
                        horizontalLineTo(5.85f)
                        horizontalLineTo(9f)
                        horizontalLineToRelative(3.15f)
                        horizontalLineTo(10.48f)
                        horizontalLineToRelative(3.88f)
                        horizontalLineTo(3.65f)
                        close()
                        moveToRelative(13.08f, 0f)
                        horizontalLineTo(14.15f)
                        horizontalLineTo(16f)
                        horizontalLineTo(13.23f)
                        horizontalLineTo(15f)
                        horizontalLineToRelative(3.15f)
                        horizontalLineTo(16.48f)
                        horizontalLineToRelative(3.88f)
                        horizontalLineTo(16.73f)
                        close()
                        moveTo(13f, 18f)
                        horizontalLineToRelative(4f)
                        horizontalLineTo(13f)
                        close()
                        moveToRelative(5.03f, 0f)
                        close()
                    }
                }
                .build()
        return _forest!!
    }

private var _forest: ImageVector? = null
