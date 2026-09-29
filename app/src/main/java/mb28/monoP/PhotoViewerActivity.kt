package mb28.monoP

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import mb28.monoP.core.Settings
import mb28.monoP.core.Settings.allowRotationGesture
import mb28.monoP.core.applyExifRotation
import mb28.monoP.ui.other.ViewerBottomDrawer
import mb28.monoP.ui.other.ViewerTopAppBar
import mb28.monoP.ui.theme.MemoriesPhotosTheme

class PhotoViewerActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        window.isNavigationBarContrastEnforced = false

        if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_MEDIA_IMAGES), 0)
        }

        val p = intent.data?.path
        if (p == null) finish()

        val photo = BitmapFactory.decodeFile(p!!).applyExifRotation(p).asImageBitmap()

        super.onCreate(savedInstanceState)
        setContent {
            MemoriesPhotosTheme {
                val hideUI = remember { mutableStateOf(false) }
                val hideUIAlpha by animateFloatAsState(if (hideUI.value) 0f else 1f)
                val hideUIBg by animateColorAsState(if (hideUI.value) MaterialTheme.colorScheme.inverseSurface
                    else MaterialTheme.colorScheme.surfaceBright)
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = hideUIBg,
                    topBar = {
                        ViewerTopAppBar(p, this,
                            Modifier.statusBarsPadding().padding(top = 5.dp)
                                .padding(horizontal = 15.dp)
                                .fillMaxWidth()
                                .alpha(hideUIAlpha)
                        )
                    },
                    bottomBar = {
                        ViewerBottomDrawer(p, this, Modifier.alpha(hideUIAlpha))
                    }
                ) {
                    PinchToZoomView(photo, hideUI)
                }
            }
        }
    }
}


@Composable
private fun PinchToZoomView(path: ImageBitmap, hideUI: MutableState<Boolean>) {
    var scale by remember { mutableFloatStateOf(1f) }
    var rotation by remember { mutableFloatStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, offsetChange, zoomChange, rotationChange ->
                    scale *= zoomChange
                    offset += offsetChange
                    if (allowRotationGesture) rotation += rotationChange
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        hideUI.value = !hideUI.value
                    },
                    onDoubleTap = {
                        scale = if (scale == 1f) 2f else 1f
                        offset = Offset.Zero
                        rotation = 0f
                    }
                )
            }
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                rotationZ = rotation,
                translationX = offset.x,
                translationY = offset.y,
            )
    ) {
        Image(
            path,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth,
            filterQuality = if (Settings.useBilinear) FilterQuality.High else FilterQuality.None
        )
    }
}