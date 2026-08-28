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
public val reset_shutter_speed: ImageVector
    get() {
        if (_reset_shutter_speed != null) {
            return _reset_shutter_speed!!
        }
        _reset_shutter_speed =
            ImageVector.Builder(
                name = "reset_shutter_speed",
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
                        moveTo(12f, 17f)
                        quadToRelative(0f, -1.13f, 0.46f, -2.08f)
                        quadToRelative(0.46f, -0.95f, 1.24f, -1.65f)
                        lineTo(15.85f, 17f)
                        horizontalLineTo(12f)
                        close()
                        moveToRelative(2.5f, 4.3f)
                        quadTo(13.58f, 20.78f, 12.95f, 19.93f)
                        reflectiveQuadTo(12.1f, 18f)
                        horizontalLineToRelative(4.32f)
                        lineTo(14.5f, 21.3f)
                        close()
                        moveTo(16.43f, 16f)
                        lineTo(14.5f, 12.7f)
                        quadToRelative(0.58f, -0.32f, 1.2f, -0.51f)
                        reflectiveQuadTo(17f, 12f)
                        quadToRelative(0.43f, 0f, 0.81f, 0.07f)
                        reflectiveQuadToRelative(0.76f, 0.2f)
                        lineTo(16.43f, 16f)
                        close()
                        moveTo(17f, 22f)
                        quadToRelative(-0.43f, 0f, -0.81f, -0.07f)
                        reflectiveQuadToRelative(-0.76f, -0.2f)
                        lineTo(17.58f, 18f)
                        lineToRelative(1.93f, 3.3f)
                        quadToRelative(-0.57f, 0.32f, -1.2f, 0.51f)
                        reflectiveQuadTo(17f, 22f)
                        close()
                        moveToRelative(0.57f, -6f)
                        lineTo(19.5f, 12.7f)
                        quadToRelative(0.93f, 0.53f, 1.55f, 1.38f)
                        reflectiveQuadTo(21.9f, 16f)
                        horizontalLineTo(17.58f)
                        close()
                        moveToRelative(2.73f, 4.73f)
                        lineTo(18.15f, 17f)
                        horizontalLineTo(22f)
                        quadToRelative(0f, 1.13f, -0.45f, 2.07f)
                        reflectiveQuadTo(20.3f, 20.73f)
                        close()
                        moveTo(20.78f, 10f)
                        horizontalLineTo(18.7f)
                        quadTo(18.05f, 7.8f, 16.23f, 6.4f)
                        reflectiveQuadTo(12f, 5f)
                        quadTo(9.08f, 5f, 7.04f, 7.04f)
                        reflectiveQuadTo(5f, 12f)
                        quadToRelative(0f, 1.8f, 0.81f, 3.3f)
                        reflectiveQuadTo(8f, 17.75f)
                        verticalLineTo(15f)
                        horizontalLineToRelative(2f)
                        verticalLineToRelative(6f)
                        horizontalLineTo(4f)
                        verticalLineTo(19f)
                        horizontalLineTo(6.35f)
                        quadTo(4.8f, 17.75f, 3.9f, 15.94f)
                        reflectiveQuadTo(3f, 12f)
                        quadTo(3f, 10.13f, 3.71f, 8.49f)
                        reflectiveQuadTo(5.64f, 5.64f)
                        quadTo(6.85f, 4.42f, 8.49f, 3.71f)
                        reflectiveQuadTo(12f, 3f)
                        quadToRelative(3.23f, 0f, 5.66f, 1.99f)
                        quadTo(20.1f, 6.97f, 20.78f, 10f)
                        close()
                    }
                }
                .build()
        return _reset_shutter_speed!!
    }

private var _reset_shutter_speed: ImageVector? = null
