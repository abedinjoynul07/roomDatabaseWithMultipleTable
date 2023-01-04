package com.shokal.roomdatabasewithmultipletable.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shokal.roomdatabasewithmultipletable.R
import com.shokal.roomdatabasewithmultipletable.models.Product
import com.shokal.roomdatabasewithmultipletable.viewModels.ViewModel
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.android.synthetic.main.fragment_product_update.*

class ProductUpdateFragment : Fragment() {
    val args: ProductUpdateFragmentArgs by navArgs()
    private lateinit var viewModel: ViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productUpdateName.setText(args.product.name)
        productUpdatePrice.setText(args.product.price.toString())
        productUpdateQuantity.setText(args.product.quantity.toString())
        productUpdateImage.setText(args.product.productImage)

        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        updateProductButton.setOnClickListener {
            val id = args.product.id
            val name = productUpdateName.text.toString()
            val price = productUpdatePrice.text.toString()
            val quantity = productUpdateQuantity.text.toString()
            val image = productUpdateImage.text.toString()
            val categoryId = args.product.categoryName

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(quantity) && !TextUtils.isEmpty(image)) {
                val product = Product(id, name, price.toFloat(), quantity.toInt(), categoryId, image)
                viewModel.updateProduct(product)

            }
            productUpdateName.text?.clear()
            productUpdatePrice.text?.clear()
            productUpdateQuantity.text?.clear()
            productUpdateImage.text?.clear()

            findNavController().navigate(R.id.action_productUpdateFragment_to_productListFragment)
        }
    }
}