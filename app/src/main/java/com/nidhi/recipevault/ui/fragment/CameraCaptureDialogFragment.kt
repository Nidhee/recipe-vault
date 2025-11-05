package com.nidhi.recipevault.com.nidhi.recipevault.ui.fragment

import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.nidhi.recipevault.R
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
private const val CAMERA_RESULT_KEY = "camera_result"
private const val CAMERA_RESULT_URI = "uri"
class CameraCaptureDialogFragment : DialogFragment() {

    interface Callback {
        fun onPhotoCaptured(uriString: String)
    }

    private lateinit var previewView: PreviewView
    private lateinit var captureBtn: MaterialButton
    private lateinit var closeBtn: MaterialButton

    private var imageCapture: ImageCapture? = null
    private val cameraExecutor: Executor by lazy {
        ContextCompat.getMainExecutor(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.dialog_camera_capture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previewView = view.findViewById(R.id.previewView)
        captureBtn = view.findViewById(R.id.captureBtn)
        closeBtn = view.findViewById(R.id.closeBtn)

        startCamera()

        captureBtn.setOnClickListener { takePhoto() }
        closeBtn.setOnClickListener { dismissAllowingStateLoss() }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = androidx.camera.core.Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()

            val cameraSelector = androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                dismissAllowingStateLoss()
            }
        }, cameraExecutor)
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val name = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
            .format(System.currentTimeMillis())

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "RV_$name.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/RecipeVault")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            requireContext().contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ).build()

        imageCapture.takePicture(
            outputOptions,
            cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val uri = outputFileResults.savedUri
                    if (uri != null) {
                        // Post result on the SAME manager Step5 listens to (childFragmentManager)
                        parentFragmentManager.setFragmentResult(
                            CAMERA_RESULT_KEY,
                            Bundle().apply { putString(CAMERA_RESULT_URI, uri.toString()) }
                        )
                    }
                    dismissAllowingStateLoss()
                }

                override fun onError(exception: ImageCaptureException) {
                    // swallow for now, just close
                    dismissAllowingStateLoss()
                }
            }
        )
    }
}