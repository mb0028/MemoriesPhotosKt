package mb28.monoP.ui.components

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mb28.monoP.icons.arrow_back
import mb28.monoP.core.getComment
import mb28.monoP.icons.more_vert

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewerTopAppBar(path: String, activity: Activity, modifier: Modifier) {
    HorizontalFloatingToolbar(
        expanded = true,
        modifier = modifier,
        colors = FloatingToolbarDefaults.standardFloatingToolbarColors(
            MaterialTheme.colorScheme.surfaceContainer.copy(0.8f)
        ),
        leadingContent = {
            IconButton(
                { activity.finish() },
                colors = IconButtonDefaults.iconButtonColors().copy(
                    MaterialTheme.colorScheme.surfaceContainerHigh
                ),
                modifier = Modifier.padding(end = 15.dp)
            ) {
                Icon(
                    arrow_back,
                    contentDescription = null
                )
            }
        },
        trailingContent = {
            IconButton({

            }) {
                Icon(more_vert, null)
            }
        }
    ) {
        Text(
            getComment(path),
            maxLines = 3,
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth(0.8f)
        )
    }
}
