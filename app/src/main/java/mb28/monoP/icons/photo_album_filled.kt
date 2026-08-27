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
public val photo_album_filled: ImageVector
    get() {
        if (_photo_album != null) {
            return _photo_album!!
        }
        _photo_album =
            ImageVector.Builder(
                name = "photo_album",
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
                        moveTo(6f, 22f)
                        quadTo(5.18f, 22f, 4.59f, 21.41f)
                        reflectiveQuadTo(4f, 20f)
                        verticalLineTo(4f)
                        quadTo(4f, 3.17f, 4.59f, 2.59f)
                        reflectiveQuadTo(6f, 2f)
                        horizontalLineTo(18f)
                        quadToRelative(0.82f, 0f, 1.41f, 0.59f)
                        reflectiveQuadTo(20f, 4f)
                        verticalLineTo(20f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(18f, 22f)
                        horizontalLineTo(6f)
                        close()
                        moveTo(7f, 18f)
                        horizontalLineTo(17f)
                        lineTo(13.63f, 13.5f)
                        lineTo(11f, 17f)
                        lineTo(9.38f, 14.83f)
                        lineTo(7f, 18f)
                        close()
                        moveToRelative(4f, -7f)
                        lineTo(13.5f, 9.5f)
                        lineTo(16f, 11f)
                        verticalLineTo(4f)
                        horizontalLineTo(11f)
                        verticalLineToRelative(7f)
                        close()
                    }
                }
                .build()
        return _photo_album!!
    }

private var _photo_album: ImageVector? = null
