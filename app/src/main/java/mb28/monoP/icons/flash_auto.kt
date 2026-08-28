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
public val flash_auto: ImageVector
    get() {
        if (_flash_auto != null) {
            return _flash_auto!!
        }
        _flash_auto =
            ImageVector.Builder(
                name = "flash_auto",
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
                        moveTo(7f, 15.6f)
                        lineTo(10.2f, 11f)
                        horizontalLineTo(7.35f)
                        lineToRelative(2f, -7f)
                        horizontalLineTo(4f)
                        verticalLineToRelative(8f)
                        horizontalLineTo(7f)
                        verticalLineToRelative(3.6f)
                        close()
                        moveTo(5f, 22f)
                        verticalLineTo(14f)
                        horizontalLineTo(2f)
                        verticalLineTo(2f)
                        horizontalLineTo(12f)
                        lineTo(10f, 9f)
                        horizontalLineToRelative(4f)
                        lineTo(5f, 22f)
                        close()
                        moveTo(7f, 12f)
                        horizontalLineTo(4f)
                        horizontalLineTo(7f)
                        close()
                        moveToRelative(7.63f, -1f)
                        lineTo(18f, 2f)
                        horizontalLineToRelative(1.6f)
                        lineToRelative(3.43f, 9f)
                        horizontalLineTo(21.48f)
                        lineTo(20.68f, 8.7f)
                        horizontalLineToRelative(-3.7f)
                        lineTo(16.18f, 11f)
                        horizontalLineTo(14.63f)
                        close()
                        moveToRelative(2.8f, -3.6f)
                        horizontalLineToRelative(2.75f)
                        lineTo(18.85f, 3.65f)
                        horizontalLineTo(18.8f)
                        lineTo(17.43f, 7.4f)
                        close()
                    }
                }
                .build()
        return _flash_auto!!
    }

private var _flash_auto: ImageVector? = null
