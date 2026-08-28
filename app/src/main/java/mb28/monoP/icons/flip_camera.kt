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
public val flip_camera_android: ImageVector
    get() {
        if (_flip_camera_android != null) {
            return _flip_camera_android!!
        }
        _flip_camera_android =
            ImageVector.Builder(
                name = "flip_camera_android",
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
                        moveTo(12f, 22f)
                        quadTo(8.43f, 22f, 5.68f, 19.75f)
                        reflectiveQuadTo(2.2f, 14f)
                        horizontalLineTo(4.25f)
                        quadToRelative(0.7f, 2.65f, 2.85f, 4.32f)
                        reflectiveQuadTo(12f, 20f)
                        quadToRelative(2.15f, 0f, 4f, -1.06f)
                        reflectiveQuadTo(18.9f, 16f)
                        horizontalLineTo(16f)
                        verticalLineTo(14f)
                        horizontalLineToRelative(6f)
                        verticalLineToRelative(6f)
                        horizontalLineTo(20f)
                        verticalLineTo(18f)
                        quadToRelative(-1.43f, 1.9f, -3.52f, 2.95f)
                        reflectiveQuadTo(12f, 22f)
                        close()
                        moveTo(9.88f, 14.13f)
                        quadTo(9f, 13.25f, 9f, 12f)
                        reflectiveQuadTo(9.88f, 9.88f)
                        reflectiveQuadTo(12f, 9f)
                        reflectiveQuadToRelative(2.13f, 0.88f)
                        reflectiveQuadTo(15f, 12f)
                        reflectiveQuadToRelative(-0.88f, 2.13f)
                        reflectiveQuadTo(12f, 15f)
                        reflectiveQuadTo(9.88f, 14.13f)
                        close()
                        moveTo(2f, 10f)
                        verticalLineTo(4f)
                        horizontalLineTo(4f)
                        verticalLineTo(6f)
                        quadTo(5.43f, 4.1f, 7.53f, 3.05f)
                        reflectiveQuadTo(12f, 2f)
                        quadToRelative(3.58f, 0f, 6.32f, 2.25f)
                        quadTo(21.08f, 6.5f, 21.8f, 10f)
                        horizontalLineTo(19.75f)
                        quadTo(19.05f, 7.35f, 16.9f, 5.68f)
                        reflectiveQuadTo(12f, 4f)
                        quadTo(9.85f, 4f, 8f, 5.06f)
                        reflectiveQuadTo(5.1f, 8f)
                        horizontalLineTo(8f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(2f)
                        close()
                    }
                }
                .build()
        return _flip_camera_android!!
    }

private var _flip_camera_android: ImageVector? = null
