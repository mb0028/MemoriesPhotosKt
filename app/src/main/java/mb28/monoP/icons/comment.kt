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
public val comment: ImageVector
    get() {
        if (_comment != null) {
            return _comment!!
        }
        _comment =
            ImageVector.Builder(
                name = "comment",
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
                        moveTo(6f, 14f)
                        horizontalLineTo(18f)
                        verticalLineTo(12f)
                        horizontalLineTo(6f)
                        verticalLineToRelative(2f)
                        close()
                        moveTo(6f, 11f)
                        horizontalLineTo(18f)
                        verticalLineTo(9f)
                        horizontalLineTo(6f)
                        verticalLineToRelative(2f)
                        close()
                        moveTo(6f, 8f)
                        horizontalLineTo(18f)
                        verticalLineTo(6f)
                        horizontalLineTo(6f)
                        verticalLineTo(8f)
                        close()
                        moveTo(22f, 22f)
                        lineTo(18f, 18f)
                        horizontalLineTo(4f)
                        quadTo(3.18f, 18f, 2.59f, 17.41f)
                        reflectiveQuadTo(2f, 16f)
                        verticalLineTo(4f)
                        quadTo(2f, 3.17f, 2.59f, 2.59f)
                        reflectiveQuadTo(4f, 2f)
                        horizontalLineTo(20f)
                        quadToRelative(0.83f, 0f, 1.41f, 0.59f)
                        reflectiveQuadTo(22f, 4f)
                        verticalLineTo(22f)
                        close()
                        moveTo(4f, 16f)
                        horizontalLineTo(18.85f)
                        lineTo(20f, 17.13f)
                        verticalLineTo(4f)
                        horizontalLineTo(4f)
                        verticalLineTo(16f)
                        close()
                        moveToRelative(0f, 0f)
                        verticalLineTo(4f)
                        verticalLineTo(16f)
                        close()
                    }
                }
                .build()
        return _comment!!
    }

private var _comment: ImageVector? = null
