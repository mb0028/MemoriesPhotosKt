package mb28.monoP

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import mb28.monoP.core.Settings.allowRotationGesture
import mb28.monoP.ui.components.ViewerBottomDrawer
import mb28.monoP.ui.components.ViewerTopAppBar
import mb28.monoP.ui.theme.MemoriesPhotosTheme

const val EXTRA_PATH = "EXTRA_PATH"

private var canMoveImage by mutableStateOf(false)
private var activityOffset by mutableIntStateOf(0)

class PhotoViewerActivity : ComponentActivity() {
    override fun finish() {
        super.finish()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, 0,
                R.anim.slide_out)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        canMoveImage = false
        activityOffset = 0
        enableEdgeToEdge()
        with(window) {
            isNavigationBarContrastEnforced = false
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            allowEnterTransitionOverlap = true
            allowReturnTransitionOverlap = true
            enterTransition = Slide()
            exitTransition = Slide()
        }

        if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_MEDIA_IMAGES), 0)
        }

        val p = intent.getStringExtra(EXTRA_PATH)
        if (p == null) {
            finish()
        }
        val photo = BitmapFactory.decodeFile(p!!).asImageBitmap()

        super.onCreate(savedInstanceState)
        setContent {
            MemoriesPhotosTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset { IntOffset(0, activityOffset) },
                    containerColor = MaterialTheme.colorScheme.surfaceBright
                        .copy(1f - (activityOffset / 400f)),
                    topBar = {
                        ViewerTopAppBar(p, this,
                            Modifier.padding(
                                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 5.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                            .alpha(if (canMoveImage) 0f else 1f - (activityOffset / 200f))
                        )
                    },
                    bottomBar = {
                        ViewerBottomDrawer(p,
                            Modifier.alpha(if (canMoveImage) 0f else 1f)
                        )
                    }
                ) { i -> i
                    PinchToZoomView(path = photo, this)
                }
            }
        }
    }
}


@Composable
private fun PinchToZoomView(path: ImageBitmap, activity: PhotoViewerActivity) {
    var scale by remember { mutableFloatStateOf(1f) }
    var rotation by remember { mutableFloatStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, offsetChange, zoomChange, rotationChange ->
                    scale *= zoomChange

                    if (allowRotationGesture) {
                        rotation += rotationChange
                    }
                    if (scale < 1.05f) {
                        canMoveImage = false
                    } else {
                        canMoveImage = true
                        offset += offsetChange
                    }
                }
            }
            .pointerInput(Unit) {
                fun onRelease() {
                    if (activityOffset > 400) {
                        activity.finish()
                    } else {
                        activityOffset = 0
                    }
                }
                detectVerticalDragGestures(
                    onDragEnd = { onRelease() },
                    onDragCancel = { onRelease() }
                ) { _, dragAmount ->
                    if (!canMoveImage) {
                        activityOffset = (activityOffset + dragAmount.toInt())
                            .coerceAtLeast(0)
                    }

                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        scale = if (scale == 1f) 2f else 1f
                        if (allowRotationGesture) {
                            rotation = 0f
                        }
                        offset = Offset.Zero
                        canMoveImage = !(scale < 1.05f)
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
            contentScale = ContentScale.FillWidth
        )
    }
}