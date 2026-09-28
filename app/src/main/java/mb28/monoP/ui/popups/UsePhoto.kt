package mb28.monoP.ui.popups

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import mb28.monoP.R
import mb28.monoP.core.getUriImage
import mb28.monoP.ui.other.EasySegmentedListItem

@Composable
fun UsePhotoPopup(path: String, activity: Activity, onDismiss: () -> Unit) {
    AlertDialog(
        { onDismiss() },
        { },
        dismissButton = {
            OutlinedButton({ onDismiss() }) {
                Text(stringResource(R.string.camera_back))
            }
        },
        title = {
            Text("Use Photo")
        },
        text = {
            val count = remember { 4 }
            LazyColumn {
                item {
                    EasySegmentedListItem(
                        null,
                        "Set as wallpaper",
                        0, count
                    ) {
                        val intent = Intent(Intent.ACTION_ATTACH_DATA)
                            .setDataAndType(getUriImage(path, activity)!!, "image/*")
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        activity.startActivity(intent)
                        onDismiss()
                    }
                    EasySegmentedListItem(
                        null,
                        "Print",
                        1, count
                    ) {
//                        val intent = Intent(PrintManager)
//                            .setDataAndType(getUriImage(path, activity)!!, "image/*")
//                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                        activity.startActivity(intent)
                        onDismiss()
                    }
                    EasySegmentedListItem(
                        null,
                        "Copy (Coming soon)",
                        1, count
                    )  {
                        onDismiss()
                    }
                    EasySegmentedListItem(
                        null,
                        "Move (Coming soon)",
                        3, count
                    )  {
                        onDismiss()
                    }
                }
            }
        }
    )
}