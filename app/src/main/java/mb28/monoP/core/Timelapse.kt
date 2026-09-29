package mb28.monoP.core

import android.widget.Toast
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalZeroShutterLag
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.exifinterface.media.ExifInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File
import java.time.LocalDateTime
import java.util.Calendar
import kotlin.time.Duration.Companion.milliseconds

class Timelapse(cam: LifecycleCameraController) {
    val camera = cam
    var isStarted by mutableStateOf(false)
    var time by mutableFloatStateOf(0f)
    var takeNum by mutableIntStateOf(0)

    @OptIn(ExperimentalZeroShutterLag::class)
    suspend fun start(onStart: suspend () -> Unit = {}) = withContext(Dispatchers.Main) {
        val t = LocalDateTime.now()
        val folderName = "Timelapse ${t.year}-${t.monthValue.toString().padStart(2, '0')}" +
            "-${t.dayOfMonth.toString().padStart(2, '0')} " +
            "${t.hour.toString().padStart(2, '0')}-${t.minute.toString().padStart(2, '0')}" +
            "-${t.second.toString().padStart(2, '0')}"

        val folder = "${Settings.appFolder}/$folderName"
        isStarted = true
        time = 16f
        takeNum = 0
//        camera.imageCaptureMode = ImageCapture.CAPTURE_MODE_ZERO_SHUTTER_LAG
        onStart()
        while (isStarted) {
            if (time > Settings.timelapseInterval) {
                takeNum++

                val outputOptions = ImageCapture.OutputFileOptions.Builder(
                    File(folder, "Take ${takeNum.toString().padStart(3,'0')}.jpg")
                ).setMetadata(ImageCapture.Metadata()).build()

                camera.takePicture(
                    outputOptions,
                    Dispatchers.IO.asExecutor(),
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) { }
                        override fun onError(e: ImageCaptureException) { }
                    }
                )
                time = 0f
            }

            time += 0.25f
            delay(250.milliseconds)
        }
        cancel()
    }

    fun stop() {
        isStarted = false
        time = 0f
        takeNum = 0
//        camera.imageCaptureMode = Settings.imageCaptureMode
    }
}