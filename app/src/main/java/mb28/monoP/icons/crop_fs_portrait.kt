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
public val fullscreen_portrait: ImageVector
    get() {
        if (_fullscreen_portrait != null) {
            return _fullscreen_portrait!!
        }
        _fullscreen_portrait =
            ImageVector.Builder(
                name = "fullscreen_portrait",
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
                        moveTo(8f, 18f)
                        horizontalLineToRelative(8f)
                        verticalLineTo(6f)
                        horizontalLineTo(8f)
                        verticalLineTo(18f)
                        close()
                        moveToRelative(12f, 2f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(18f, 22f)
                        horizontalLineTo(6f)
                        quadTo(5.18f, 22f, 4.59f, 21.41f)
                        reflectiveQuadTo(4f, 20f)
                        verticalLineTo(4f)
                        quadTo(4f, 3.17f, 4.59f, 2.59f)
                        reflectiveQuadTo(6f, 2f)
                        horizontalLineTo(18f)
                        quadToRelative(0.82f, 0f, 1.41f, 0.59f)
                        reflectiveQuadTo(20f, 4f)
                        verticalLineTo(20f)
                        close()
                        moveToRelative(-2f, 0f)
                        verticalLineTo(4f)
                        horizontalLineTo(6f)
                        verticalLineTo(20f)
                        horizontalLineTo(18f)
                        close()
                        moveTo(18f, 4f)
                        horizontalLineTo(6f)
                        horizontalLineTo(18f)
                        close()
                    }
                }
                .build()
        return _fullscreen_portrait!!
    }

private var _fullscreen_portrait: ImageVector? = null
