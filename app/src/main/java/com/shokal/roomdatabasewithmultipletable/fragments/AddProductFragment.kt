package com.shokal.roomdatabasewithmultipletable.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.shokal.roomdatabasewithmultipletable.R
import com.shokal.roomdatabasewithmultipletable.models.Category
import com.shokal.roomdatabasewithmultipletable.models.Product
import com.shokal.roomdatabasewithmultipletable.viewModels.ViewModel
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddProductFragment : Fragment() {
    private lateinit var viewModel: ViewModel
    private var categoryName: String = "Default"
    private lateinit var spinner : Spinner
    private var labels: MutableList<CharSequence> = mutableListOf()
    private val PICK_PHOTO_FOR_AVATAR = 1
    private lateinit var pickedImage: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        labels = mutableListOf("Default")

        viewModel.categoryList.observe(viewLifecycleOwner) {
            for(item in it){
                labels.add(item.name)
            }
        }
        Log.d("lab", labels.toString())
        val view: View = inflater.inflate(R.layout.fragment_add_product, container, false)
        spinner = view.findViewById<View>(R.id.categorySpinner) as Spinner
        val mSortAdapter: ArrayAdapter<CharSequence> = ArrayAdapter<CharSequence>(
            requireContext(), android.R.layout.simple_spinner_item, labels
        )
        mSortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = mSortAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    categoryName = p0?.getItemAtPosition(p2).toString()
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

        addProductButton.setOnClickListener {
            val name = productName.text.toString()
            val price = productPrice.text.toString()
            val quantity = productQuantity.text.toString()
            val image = productImage.text.toString()

            if (!TextUtils.isEmpty(name) &&
                !TextUtils.isEmpty(price) &&
                !TextUtils.isEmpty(quantity) &&
                !TextUtils.isEmpty(image)
            ) {
                lifecycleScope.launch(Dispatchers.IO){
                    val product =
                        Product(0, name, price.toFloat(), quantity.toInt(), categoryName, getBitmap(image))
                    viewModel.addProduct(product)
                }
            }
            productName.text?.clear()
            productPrice.text?.clear()
            productQuantity.text?.clear()
            productImage.text?.clear()
            findNavController().navigate(R.id.action_addProductFragment_to_productListFragment)
        }
    }

    private suspend fun getBitmap(imageLink: String): Bitmap {
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data(imageLink)
            .build()
        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }


    fun pickImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            val picturePath: String = getPath(requireActivity().applicationContext, selectedImageUri)

            Log.d("Picture Path", picturePath)
        }
    }

    fun getPath(context: Context, uri: Uri?): String {
        var result: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            uri?.let { context.contentResolver.query(it, proj, null, null, null) }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val index: Int = cursor.getColumnIndexOrThrow(proj[0])
                result = cursor.getString(index)
            }
            cursor.close()
        }
        if (result == null) {
            result = "Not found"
        }
        return result
    }
}