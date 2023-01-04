package com.shokal.roomdatabasewithmultipletable.fragments

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.shokal.roomdatabasewithmultipletable.R
import com.shokal.roomdatabasewithmultipletable.models.Product
import com.shokal.roomdatabasewithmultipletable.viewModels.ViewModel
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.android.synthetic.main.fragment_add_product.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddProductFragment : Fragment() {
    private lateinit var viewModel: ViewModel
    lateinit var categoryName: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        val t=inflater.inflate(R.layout.fragment_add_product, container, false)
//        viewModel = ViewModelProvider(this)[ViewModel::class.java]
//        categoryName = "Phone"
//        val spinner = t.categorySpinner
//        val labels: MutableList<String> = mutableListOf()
//        viewModel.categoryList.observe(viewLifecycleOwner) {
//            for(item in it){
//                labels.add(item.name)
//            }
//        }
//        spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, labels)
//        spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                val type = parent?.getItemAtPosition(position).toString()
//                Toast.makeText(activity,type, Toast.LENGTH_LONG).show()
//                categoryName = parent?.getItemAtPosition(position).toString()
//            }
//        }
        return inflater.inflate(R.layout.fragment_add_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ViewModel::class.java]

        categoryName = "Phone"
        val spinner = categorySpinner as Spinner
        val labels: MutableList<String> = mutableListOf()
        viewModel.categoryList.observe(viewLifecycleOwner) {
            for(item in it){
                labels.add(item.name)
            }
        }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, labels)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                spinner.setSelection(position)
                if (parentView != null) {
                    categoryName = parentView.getItemAtPosition(position).toString()
                }
            }
            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,view: View, position: Int, id: Long) {
                categoryName = parent.getItemAtPosition(position).toString()
                Toast.makeText(requireContext(), labels[position], Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

//        spinner.onItemSelectedListener =
//            object : AdapterView.OnItemSelectedListener{
//                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                    categoryName = p0?.getItemAtPosition(p2).toString()
//                }
//                override fun onNothingSelected(p0: AdapterView<*>?) {
//                }
//            }

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
}