package mb28.monoP.ui.components

import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList

@Composable
fun ShutterButton(onClick: () -> Unit) {
    val held = remember { mutableStateOf(false) }
    val animatedScale: Float by animateFloatAsState(
        if (held.value) 0.85f else 1f,
        animationSpec = SpringSpec()
    )
    Box(
        Modifier
            .clip(CircleShape)
            .scale(animatedScale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        held.value = true
                    }
                )
            }
    ) {
        Card(
            shape = CircleShape,
            colors = CardDefaults.cardColors().copy(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier
                .width(85.dp)
                .height(85.dp)
        ) {
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .padding(10.dp)
                    .width(100.dp)
                    .height(100.dp)
            ) { }
        }
    }
}