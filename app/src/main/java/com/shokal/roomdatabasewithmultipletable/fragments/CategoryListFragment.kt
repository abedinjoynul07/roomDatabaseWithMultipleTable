package com.shokal.roomdatabasewithmultipletable.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shokal.roomdatabasewithmultipletable.R
import com.shokal.roomdatabasewithmultipletable.adapters.CategoryAdapter
import com.shokal.roomdatabasewithmultipletable.models.Category
import com.shokal.roomdatabasewithmultipletable.viewModels.ViewModel
import kotlinx.android.synthetic.main.fragment_category_list.*
import kotlinx.android.synthetic.main.fragment_product_list.*

class CategoryListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = categoryRecyclerView
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        initializeAdapter()
        addCategoryButton.setOnClickListener {
            findNavController().navigate(R.id.action_categoryListFragment_to_addCategoryFragment)
        }
    }



    fun initializeAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        observeData()
    }

    private fun observeData() {
        viewModel.categoryList.observe(viewLifecycleOwner) {
            recyclerView.adapter = CategoryAdapter(
                requireContext(), viewModel, it as ArrayList<Category>
            )
        }
    }
}