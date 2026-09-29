package mb28.monoP.ui.popups

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import mb28.monoP.R
import mb28.monoP.core.Settings
import mb28.monoP.core.editComment
import mb28.monoP.core.getComment

@Composable
fun ChangeAppFolderPopup(onDismiss: () -> Unit) {
    val oldAppFolder = Settings.appFolder
    var newAppFolder by remember { mutableStateOf(oldAppFolder) }
    AlertDialog(
        { onDismiss() },
        {
            Button({
                onDismiss()
            }) {
                Text("Restart")
            }
        },
        dismissButton = {
            OutlinedButton({ onDismiss() }) {
                Text(stringResource(R.string.cancel))
            }
        },
        title = {
            Text("Change App Folder")
        },
        text = {
            OutlinedTextField(
                newAppFolder,
                {newAppFolder = it},
                minLines = 3,
                maxLines = 3,
                shape = OutlinedTextFieldDefaults.roundedShape,
                textStyle = TextStyle(
                    fontSize = 18.sp
                ),
                label = {
                    Text("App Folder")
                }
            )
        }
    )
}
