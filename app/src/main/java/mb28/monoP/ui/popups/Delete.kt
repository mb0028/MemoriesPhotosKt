package mb28.monoP.ui.popups

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import mb28.monoP.R
import mb28.monoP.core.Settings
import mb28.monoP.core.deleteOrTrash
import mb28.monoP.ui.photosInTrash
import java.io.File

@Composable
fun DeletePopup(path: String, onDismiss: (Boolean) -> Unit) {
    val todText = if (Settings.trashInstead) stringResource(R.string.move_to_trash) else stringResource(R.string.delete)
    AlertDialog(
        { onDismiss(false) },
        {
            Button({
                deleteOrTrash(path)
                onDismiss(true)
            }) {
                Text(todText)
            }
        },
        dismissButton = {
            OutlinedButton({ onDismiss(false) }) {
                Text(stringResource(R.string.cancel))
            }
        },
        title = {
            Text("$todText?")
        },
        text = {
            Text(path)
        }
    )
}

@Composable
fun ClearTrashPopup(onDismiss: (Boolean) -> Unit) {
    AlertDialog(
        { onDismiss(false) },
        {
            Button({
                val ready = photosInTrash.toList()
                photosInTrash.clear()

                ready.forEach {
                     File(it).delete()
                }
                onDismiss(true)
            }) {
                Text("Clear (${photosInTrash.count()})")
            }
        },
        dismissButton = {
            OutlinedButton({ onDismiss(false) }) {
                Text(stringResource(R.string.cancel))
            }
        },
        title = {
            Text("Clear Trash?")
        },
        text = {
            Text("All items are deleted forever")
        }
    )
}
