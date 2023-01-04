package com.shokal.roomdatabasewithmultipletable.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shokal.roomdatabasewithmultipletable.R
import com.shokal.roomdatabasewithmultipletable.adapters.ProductAdpater
import com.shokal.roomdatabasewithmultipletable.models.Product
import com.shokal.roomdatabasewithmultipletable.viewModels.ViewModel
import kotlinx.android.synthetic.main.fragment_product_list.*

class ProductListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = productRecycleView
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        initializeAdapter()
        addProductViewButton.setOnClickListener {
            findNavController().navigate(R.id.action_productListFragment_to_addProductFragment)
        }

        categoryViewButton.setOnClickListener {
            findNavController().navigate(R.id.action_productListFragment_to_categoryListFragment)
        }
    }


    private fun initializeAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        observeData()
    }

    private fun observeData() {
        viewModel.productList.observe(viewLifecycleOwner) {
            recyclerView.adapter = ProductAdpater(
                requireContext(), viewModel, it as ArrayList<Product>
            )
        }
    }



}