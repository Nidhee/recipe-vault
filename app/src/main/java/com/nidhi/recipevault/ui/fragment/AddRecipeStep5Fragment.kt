package com.nidhi.recipevault.com.nidhi.recipevault.ui.fragment
import android.Manifest
import androidx.appcompat.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.nidhi.recipevault.databinding.AddRecipeStep5Binding
import com.nidhi.recipevault.ui.viewmodel.AddRecipeViewModel
private const val CAMERA_RESULT_KEY = "camera_result"
private const val CAMERA_RESULT_URI = "uri"
class AddRecipeStep5Fragment : Fragment(), CameraCaptureDialogFragment.Callback {
    private var _binding : AddRecipeStep5Binding? = null
    private val binding get() = _binding!!

    private val addRecipeViewModel: AddRecipeViewModel by activityViewModels()

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) openCameraDialog()
    }

    private val pickFromGalleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            addRecipeViewModel.setImageUri(it.toString())
            showPreview(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddRecipeStep5Binding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Listen on the SAME manager the dialog will post to (childFragmentManager)
        childFragmentManager.setFragmentResultListener(CAMERA_RESULT_KEY, viewLifecycleOwner) { _, bundle ->
            bundle.getString(CAMERA_RESULT_URI)?.let { uriString ->
                onPhotoCaptured(uriString)
            }
        }
        binding.addRecipeStep5UploadImageBtn.setOnClickListener {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
        // Restore from ViewModel if present
        addRecipeViewModel.step5Fields.value.imageUri?.let { saved ->
            showPreview(Uri.parse(saved))
        }
        binding.addRecipeStep5UploadImageBtn.setOnClickListener {
            openPhotoSourceChooser()
        }
    }

    private fun openCameraDialog() {
        val dialog = CameraCaptureDialogFragment()
        dialog.show(childFragmentManager, "CameraCaptureDialogFragment")
    }

    private fun openPhotoSourceChooser() {
        AlertDialog.Builder(requireContext())
            .setTitle("Add photo")
            .setItems(arrayOf("Camera", "Pick from gallery")) { _, which ->
                when (which) {
                    0 -> cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    1 -> pickFromGalleryLauncher.launch("image/*")
                }
            }
            .show()
    }
    override fun onPhotoCaptured(uriString: String) {
        addRecipeViewModel.setImageUri(uriString)
        val uri = Uri.parse(uriString)
        showPreview(uri)
    }

    private fun showPreview(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .into(binding.addRecipeStep5ImagePreview)
    }
}