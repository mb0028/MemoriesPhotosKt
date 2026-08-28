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
public val photo_camera_front: ImageVector
    get() {
        if (_photo_camera_front != null) {
            return _photo_camera_front!!
        }
        _photo_camera_front =
            ImageVector.Builder(
                name = "photo_camera_front",
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
                        moveTo(8f, 17f)
                        horizontalLineToRelative(8f)
                        verticalLineTo(16.45f)
                        quadToRelative(0f, -1.13f, -1.1f, -1.79f)
                        reflectiveQuadTo(12f, 14f)
                        reflectiveQuadTo(9.1f, 14.66f)
                        reflectiveQuadTo(8f, 16.45f)
                        verticalLineTo(17f)
                        close()
                        moveToRelative(5.41f, -4.59f)
                        quadTo(14f, 11.83f, 14f, 11f)
                        quadTo(14f, 10.17f, 13.41f, 9.59f)
                        reflectiveQuadTo(12f, 9f)
                        reflectiveQuadTo(10.59f, 9.59f)
                        reflectiveQuadTo(10f, 11f)
                        reflectiveQuadToRelative(0.59f, 1.41f)
                        reflectiveQuadTo(12f, 13f)
                        reflectiveQuadToRelative(1.41f, -0.59f)
                        close()
                        moveTo(4f, 21f)
                        quadTo(3.18f, 21f, 2.59f, 20.41f)
                        reflectiveQuadTo(2f, 19f)
                        verticalLineTo(7f)
                        quadTo(2f, 6.18f, 2.59f, 5.59f)
                        reflectiveQuadTo(4f, 5f)
                        horizontalLineTo(7.15f)
                        lineTo(9f, 3f)
                        horizontalLineToRelative(6f)
                        lineToRelative(1.85f, 2f)
                        horizontalLineTo(20f)
                        quadToRelative(0.83f, 0f, 1.41f, 0.59f)
                        quadTo(22f, 6.18f, 22f, 7f)
                        verticalLineTo(19f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(20f, 21f)
                        horizontalLineTo(4f)
                        close()
                        moveTo(4f, 19f)
                        horizontalLineTo(20f)
                        verticalLineTo(7f)
                        horizontalLineTo(15.95f)
                        lineTo(14.13f, 5f)
                        horizontalLineTo(9.88f)
                        lineTo(8.05f, 7f)
                        horizontalLineTo(4f)
                        verticalLineTo(19f)
                        close()
                        moveToRelative(8f, -6f)
                        close()
                    }
                }
                .build()
        return _photo_camera_front!!
    }

private var _photo_camera_front: ImageVector? = null
