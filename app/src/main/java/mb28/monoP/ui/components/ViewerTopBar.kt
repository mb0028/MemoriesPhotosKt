package mb28.monoP.ui.components

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mb28.crystalHomeKt.ui.icons.arrow_back
import mb28.monoP.core.getComment
import mb28.monoP.icons.add_2
import mb28.monoP.icons.more_vert

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewerTopAppBar(path: String, activity: Activity) {
    TopAppBar(
        {
            Text(
                getComment(path),
                maxLines = 3,
                fontSize = 18.sp
            )
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.surface.copy(0.85f),
        ),
        navigationIcon = {
            IconButton(
                { activity.finish() },
                colors = IconButtonDefaults.iconButtonColors().copy(
                    MaterialTheme.colorScheme.surfaceContainerHigh
                ),
                modifier = Modifier.padding(horizontal = 15.dp)
            ) {
                Icon(
                    arrow_back,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton({

            }) {
                Icon(more_vert, null)
            }
        }
    )
}
