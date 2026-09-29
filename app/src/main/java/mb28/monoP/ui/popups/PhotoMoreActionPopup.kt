package mb28.monoP.ui.popups

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mb28.monoP.core.Settings

@Composable
fun PhotoMoreActionPopup(path: String, onDismiss: () -> Unit) {
    AlertDialog(
        { onDismiss() },
        { },
        dismissButton = {
            OutlinedButton({ onDismiss() }) {
                Text("Close")
            }
        },
        title = {
            Text("More Actions")
        },
        text = {
            val count = remember { 1 }
            LazyColumn {
                item {
                    SegmentedListItem(
                        shapes = ListItemDefaults.segmentedShapes(0, count),
                        modifier = Modifier.padding(bottom = 2.dp),
                        trailingContent = {
                            Switch(
                                Settings.useBilinear,
                                { v ->
                                    Settings.useBilinear = v
                                    Settings.save()
                                },
                                modifier = Modifier.padding(vertical = 10.dp),
                            )
                        },
                        onClick = { Settings.useBilinear = !Settings.useBilinear }
                    ) {
                        Text("Use Bilinear")
                    }
                }
            }
        }
    )
}