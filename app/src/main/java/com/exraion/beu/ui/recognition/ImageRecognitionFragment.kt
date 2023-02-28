package com.exraion.beu.ui.recognition

import android.Manifest
import android.R.attr.bitmap
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentImageRecognitionBinding
import com.exraion.beu.tflite.TFLiteHelper
import com.exraion.beu.util.ScreenOrientation
import java.io.IOException
import java.util.concurrent.Executors


class ImageRecognitionFragment : BaseFragment<FragmentImageRecognitionBinding>() {
    private val cameraExecutor = Executors.newSingleThreadExecutor()
    private var imagePreview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var cameraControl: CameraControl? = null
    private var cameraInfo: CameraInfo? = null
    
    private lateinit var tfLiteHelper: TFLiteHelper
    
    companion object {
        private const val PHOTO_EXTENSION = ".jpg"
        
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        
        private const val REQUEST_CODE = 1
    }
    
    override fun inflateViewBinding(container: ViewGroup?): FragmentImageRecognitionBinding {
        return FragmentImageRecognitionBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation = ScreenOrientation.PORTRAIT
    
    override fun FragmentImageRecognitionBinding.binder() {
        tfLiteHelper = TFLiteHelper(requireContext())
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissions(
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        
        ivCameraCapture.setOnClickListener {
            takePhoto()
        }
        
        ivPickGallery.setOnClickListener {
            takeFromGallery()
        }
    }
    
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
        cameraProviderFuture.addListener({
            imagePreview = Preview.Builder().apply {
                setTargetAspectRatio(AspectRatio.RATIO_16_9)
                setTargetRotation(binding!!.previewView.display.rotation)
            }.build()
            
            imageCapture = ImageCapture.Builder().apply {
                setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                setFlashMode(ImageCapture.FLASH_MODE_AUTO)
            }.build()
            
            val cameraProvider = cameraProviderFuture.get()
            val camera = cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                imagePreview,
                // imageAnalysis,
                imageCapture,
            )
            binding!!.previewView.implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            imagePreview?.setSurfaceProvider(binding!!.previewView.surfaceProvider)
            cameraControl = camera.cameraControl
            cameraInfo = camera.cameraInfo
        }, ContextCompat.getMainExecutor(requireContext()))
    }
    
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }
    
    private fun takePhoto() {
        imageCapture!!.takePicture(cameraExecutor, object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                val buffer = image.planes[0].buffer
                val bytes = ByteArray(buffer.capacity())
                buffer.get(bytes)
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                tfLiteHelper.classifyImage(bitmap)
                activity?.runOnUiThread {
                    val label = tfLiteHelper.showResult()
                    Toast.makeText(requireContext(), label, Toast.LENGTH_SHORT).show()
                }
                image.close()
            }
        
            override fun onError(exception: ImageCaptureException) {
                Toast.makeText(requireContext(), "Error capturing photo: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    
    private fun takeFromGallery() {
        val selectType = "image/*"
        val selectPicture = "Select Picture"
    
        val intent = Intent()
        intent.type = selectType
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, selectPicture), REQUEST_CODE)
    }
    
    override fun onBackPressedBehaviour() {
        findNavController().navigate(
            ImageRecognitionFragmentDirections.actionNavigationImageRecognitionToNavigationHome()
        )
    }
    
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            return
        }
    }
    
    @Deprecated("Deprecated in Java", ReplaceWith(
        "super.onActivityResult(requestCode, resultCode, data)",
        "com.exraion.beu.base.BaseFragment"
    )
    )
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === REQUEST_CODE && resultCode === RESULT_OK && data != null) {
            val imageUri = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, imageUri)
                tfLiteHelper.classifyImage(bitmap)
                activity?.runOnUiThread {
                    val label = tfLiteHelper.showResult()
                    Toast.makeText(requireContext(), label, Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        
    }
    
    
    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}