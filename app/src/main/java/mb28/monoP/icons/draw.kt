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
public val draw: ImageVector
    get() {
        if (_draw != null) {
            return _draw!!
        }
        _draw =
            ImageVector.Builder(
                name = "draw",
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
                        moveTo(4f, 21f)
                        verticalLineTo(16.75f)
                        lineTo(17.18f, 3.6f)
                        quadToRelative(0.3f, -0.3f, 0.68f, -0.45f)
                        reflectiveQuadTo(18.6f, 3f)
                        quadTo(19f, 3f, 19.36f, 3.15f)
                        reflectiveQuadTo(20f, 3.6f)
                        lineTo(21.4f, 5f)
                        quadToRelative(0.3f, 0.27f, 0.45f, 0.64f)
                        reflectiveQuadTo(22f, 6.4f)
                        quadToRelative(0f, 0.38f, -0.15f, 0.75f)
                        reflectiveQuadTo(21.4f, 7.82f)
                        lineTo(8.25f, 21f)
                        horizontalLineTo(4f)
                        close()
                        moveTo(6f, 19f)
                        horizontalLineTo(7.4f)
                        lineTo(17.23f, 9.2f)
                        lineTo(16.53f, 8.48f)
                        lineTo(15.8f, 7.77f)
                        lineTo(6f, 17.6f)
                        verticalLineTo(19f)
                        close()
                        moveTo(20f, 6.43f)
                        lineTo(18.58f, 5f)
                        lineTo(20f, 6.43f)
                        close()
                        moveTo(16.53f, 8.48f)
                        lineTo(15.8f, 7.77f)
                        lineTo(17.23f, 9.2f)
                        lineTo(16.53f, 8.48f)
                        close()
                        moveTo(14f, 21f)
                        quadToRelative(1.85f, 0f, 3.43f, -0.93f)
                        reflectiveQuadTo(19f, 17.5f)
                        quadToRelative(0f, -0.9f, -0.47f, -1.55f)
                        reflectiveQuadTo(17.25f, 14.83f)
                        lineTo(15.78f, 16.3f)
                        quadToRelative(0.58f, 0.25f, 0.9f, 0.55f)
                        reflectiveQuadTo(17f, 17.5f)
                        quadToRelative(0f, 0.57f, -0.91f, 1.04f)
                        reflectiveQuadTo(14f, 19f)
                        quadToRelative(-0.42f, 0f, -0.71f, 0.29f)
                        reflectiveQuadTo(13f, 20f)
                        reflectiveQuadToRelative(0.29f, 0.71f)
                        reflectiveQuadTo(14f, 21f)
                        close()
                        moveTo(4.58f, 13.35f)
                        lineToRelative(1.5f, -1.5f)
                        quadTo(5.58f, 11.65f, 5.29f, 11.44f)
                        reflectiveQuadTo(5f, 11f)
                        quadTo(5f, 10.7f, 5.45f, 10.4f)
                        reflectiveQuadTo(7.35f, 9.48f)
                        quadTo(9.55f, 8.52f, 10.28f, 7.75f)
                        quadTo(11f, 6.97f, 11f, 6f)
                        quadTo(11f, 4.63f, 9.9f, 3.81f)
                        reflectiveQuadTo(7f, 3f)
                        quadTo(5.88f, 3f, 4.99f, 3.4f)
                        reflectiveQuadTo(3.63f, 4.38f)
                        quadTo(3.35f, 4.7f, 3.4f, 5.1f)
                        reflectiveQuadTo(3.78f, 5.75f)
                        quadTo(4.1f, 6.02f, 4.5f, 5.97f)
                        quadTo(4.9f, 5.93f, 5.18f, 5.65f)
                        quadTo(5.53f, 5.3f, 5.95f, 5.15f)
                        reflectiveQuadTo(7f, 5f)
                        quadTo(8.03f, 5f, 8.51f, 5.3f)
                        reflectiveQuadTo(9f, 6f)
                        quadTo(9f, 6.35f, 8.56f, 6.64f)
                        reflectiveQuadTo(6.55f, 7.65f)
                        quadToRelative(-2f, 0.88f, -2.78f, 1.59f)
                        reflectiveQuadTo(3f, 11f)
                        quadToRelative(0f, 0.8f, 0.43f, 1.36f)
                        reflectiveQuadToRelative(1.15f, 0.99f)
                        close()
                    }
                }
                .build()
        return _draw!!
    }

private var _draw: ImageVector? = null
