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
public val center_focus_strong: ImageVector
    get() {
        if (_center_focus_strong != null) {
            return _center_focus_strong!!
        }
        _center_focus_strong =
            ImageVector.Builder(
                name = "center_focus_strong",
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
                        moveTo(5f, 21f)
                        quadTo(4.18f, 21f, 3.59f, 20.41f)
                        reflectiveQuadTo(3f, 19f)
                        verticalLineTo(15f)
                        horizontalLineTo(5f)
                        verticalLineToRelative(4f)
                        horizontalLineTo(9f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(5f)
                        close()
                        moveToRelative(10f, 0f)
                        verticalLineTo(19f)
                        horizontalLineToRelative(4f)
                        verticalLineTo(15f)
                        horizontalLineToRelative(2f)
                        verticalLineToRelative(4f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(19f, 21f)
                        horizontalLineTo(15f)
                        close()
                        moveTo(3f, 9f)
                        verticalLineTo(5f)
                        quadTo(3f, 4.17f, 3.59f, 3.59f)
                        reflectiveQuadTo(5f, 3f)
                        horizontalLineTo(9f)
                        verticalLineTo(5f)
                        horizontalLineTo(5f)
                        verticalLineTo(9f)
                        horizontalLineTo(3f)
                        close()
                        moveTo(19f, 9f)
                        verticalLineTo(5f)
                        horizontalLineTo(15f)
                        verticalLineTo(3f)
                        horizontalLineToRelative(4f)
                        quadToRelative(0.83f, 0f, 1.41f, 0.59f)
                        reflectiveQuadTo(21f, 5f)
                        verticalLineTo(9f)
                        horizontalLineTo(19f)
                        close()
                        moveTo(8.46f, 15.54f)
                        quadTo(7f, 14.08f, 7f, 12f)
                        quadTo(7f, 9.92f, 8.46f, 8.46f)
                        reflectiveQuadTo(12f, 7f)
                        reflectiveQuadToRelative(3.54f, 1.46f)
                        reflectiveQuadTo(17f, 12f)
                        reflectiveQuadToRelative(-1.46f, 3.54f)
                        reflectiveQuadTo(12f, 17f)
                        quadTo(9.93f, 17f, 8.46f, 15.54f)
                        close()
                        moveToRelative(5.66f, -1.41f)
                        quadTo(15f, 13.25f, 15f, 12f)
                        reflectiveQuadTo(14.13f, 9.88f)
                        reflectiveQuadTo(12f, 9f)
                        reflectiveQuadTo(9.88f, 9.88f)
                        reflectiveQuadTo(9f, 12f)
                        reflectiveQuadToRelative(0.88f, 2.13f)
                        reflectiveQuadTo(12f, 15f)
                        reflectiveQuadToRelative(2.13f, -0.88f)
                        close()
                        moveTo(12f, 12f)
                        close()
                    }
                }
                .build()
        return _center_focus_strong!!
    }

private var _center_focus_strong: ImageVector? = null
