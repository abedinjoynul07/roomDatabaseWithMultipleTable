package com.shokal.roomdatabasewithmultipletable.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.shokal.roomdatabasewithmultipletable.R
import com.shokal.roomdatabasewithmultipletable.models.Category
import com.shokal.roomdatabasewithmultipletable.models.Product
import com.shokal.roomdatabasewithmultipletable.viewModels.ViewModel
import kotlinx.android.synthetic.main.fragment_add_category.*
import kotlinx.android.synthetic.main.fragment_add_product.*

class AddCategoryFragment : Fragment() {
    private lateinit var viewModel: ViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        addCategoryButton.setOnClickListener {
            addData()
        }
    }
    private fun addData() {
        val name = categoryNameEditText.text.toString()

        if (!TextUtils.isEmpty(name)) {
            val category = Category(0, name)
            viewModel.addCategory(category)
        }
        categoryNameEditText.text?.clear()
        findNavController().navigate(R.id.action_addCategoryFragment_to_categoryListFragment)
    }
}