package mb28.monoP.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.star

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ShutterButton(interactionSource: MutableInteractionSource, onClick: () -> Unit) {
    val isPressed by interactionSource.collectIsPressedAsState()
    val animatedScale: Float by animateFloatAsState(
        if (isPressed) 0.75f else 1f,
        animationSpec = SpringSpec(
            dampingRatio = 0.2f,
            stiffness = Spring.StiffnessMediumLow
        )
    )
    Card(
        shape = CircleShape,
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .width(85.dp)
            .height(85.dp)
            .scale(animatedScale)
            .clip(CircleShape)
            .clickable(interactionSource = interactionSource) {
                onClick()
            }
    ) {
        Card(
            shape = RoundedPolygon.star(
                6,
                radius = 1f,
                innerRadius = 0.5f,
                rounding = CornerRounding(5f)
            ).toShape(),
            modifier = Modifier
                .fillMaxSize()
                .scale(animatedScale)
                .padding(18.dp)
        ) { }
    }
}