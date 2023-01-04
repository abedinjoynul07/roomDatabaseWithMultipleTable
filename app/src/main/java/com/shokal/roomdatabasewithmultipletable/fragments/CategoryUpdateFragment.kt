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
import com.shokal.roomdatabasewithmultipletable.models.Category
import com.shokal.roomdatabasewithmultipletable.models.Product
import com.shokal.roomdatabasewithmultipletable.viewModels.ViewModel
import kotlinx.android.synthetic.main.fragment_category_update.*
import kotlinx.android.synthetic.main.fragment_product_update.*

class CategoryUpdateFragment : Fragment() {
    val args: CategoryUpdateFragmentArgs by navArgs()
    private lateinit var viewModel: ViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryUpdateNameEditText.setText(args.category.name)


        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        updateCategoryButton.setOnClickListener {
            val id = args.category.id
            val name = categoryUpdateNameEditText.text.toString()

            if (!TextUtils.isEmpty(name)) {
                val category = Category(id, name)
                viewModel.updateCategory(category)
            }
            categoryUpdateNameEditText.text?.clear()

            findNavController().navigate(R.id.action_categoryUpdateFragment_to_categoryListFragment)
        }
    }

}