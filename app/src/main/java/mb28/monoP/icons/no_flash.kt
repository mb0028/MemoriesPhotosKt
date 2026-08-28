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
public val no_flash: ImageVector
    get() {
        if (_no_flash != null) {
            return _no_flash!!
        }
        _no_flash =
            ImageVector.Builder(
                name = "no_flash",
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
                        moveTo(20.48f, 23.3f)
                        lineToRelative(-2.5f, -2.5f)
                        quadToRelative(-0.1f, 0.5f, -0.51f, 0.85f)
                        reflectiveQuadTo(16.5f, 22f)
                        horizontalLineTo(3.5f)
                        quadTo(2.88f, 22f, 2.44f, 21.56f)
                        reflectiveQuadTo(2f, 20.5f)
                        verticalLineTo(10.9f)
                        quadTo(2f, 10.27f, 2.44f, 9.84f)
                        reflectiveQuadTo(3.5f, 9.4f)
                        horizontalLineTo(6.15f)
                        lineTo(6.35f, 9.17f)
                        lineTo(0.68f, 3.5f)
                        lineTo(2.1f, 2.07f)
                        lineToRelative(19.8f, 19.8f)
                        lineTo(20.48f, 23.3f)
                        close()
                        moveTo(4f, 20f)
                        horizontalLineTo(16f)
                        verticalLineTo(18.83f)
                        lineTo(13.43f, 16.25f)
                        quadToRelative(-0.28f, 1.2f, -1.21f, 1.98f)
                        reflectiveQuadTo(10f, 19f)
                        quadTo(8.55f, 19f, 7.53f, 17.98f)
                        reflectiveQuadTo(6.5f, 15.5f)
                        quadToRelative(0f, -1.28f, 0.78f, -2.21f)
                        reflectiveQuadTo(9.25f, 12.08f)
                        lineTo(8.58f, 11.4f)
                        horizontalLineTo(4f)
                        verticalLineTo(20f)
                        close()
                        moveToRelative(6f, -3f)
                        quadToRelative(0.63f, 0f, 1.06f, -0.44f)
                        reflectiveQuadTo(11.5f, 15.5f)
                        reflectiveQuadTo(11.06f, 14.44f)
                        reflectiveQuadTo(10f, 14f)
                        reflectiveQuadTo(8.94f, 14.44f)
                        reflectiveQuadTo(8.5f, 15.5f)
                        reflectiveQuadToRelative(0.44f, 1.06f)
                        reflectiveQuadTo(10f, 17f)
                        close()
                        moveToRelative(8f, -1.88f)
                        lineToRelative(-2f, -2f)
                        verticalLineTo(11.4f)
                        horizontalLineTo(14.28f)
                        lineTo(10.88f, 8f)
                        horizontalLineToRelative(1.7f)
                        lineToRelative(1.28f, 1.4f)
                        horizontalLineTo(16.5f)
                        quadToRelative(0.63f, 0f, 1.06f, 0.44f)
                        reflectiveQuadTo(18f, 10.9f)
                        verticalLineToRelative(4.23f)
                        close()
                        moveTo(19f, 11f)
                        verticalLineTo(7f)
                        horizontalLineTo(18f)
                        verticalLineTo(2f)
                        horizontalLineToRelative(4f)
                        lineTo(20.4f, 5.6f)
                        horizontalLineTo(22f)
                        lineTo(19f, 11f)
                        close()
                        moveToRelative(-3f, 2.13f)
                        close()
                        moveTo(12.3f, 15.1f)
                        close()
                    }
                }
                .build()
        return _no_flash!!
    }

private var _no_flash: ImageVector? = null
