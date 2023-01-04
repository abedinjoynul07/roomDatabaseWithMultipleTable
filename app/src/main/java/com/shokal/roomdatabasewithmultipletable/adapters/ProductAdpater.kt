package com.shokal.roomdatabasewithmultipletable.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shokal.roomdatabasewithmultipletable.R
import com.shokal.roomdatabasewithmultipletable.fragments.ProductListFragment
import com.shokal.roomdatabasewithmultipletable.fragments.ProductListFragmentDirections
import com.shokal.roomdatabasewithmultipletable.models.Product
import com.shokal.roomdatabasewithmultipletable.viewModels.ViewModel
import kotlinx.android.synthetic.main.product_list.view.*

class ProductAdpater (
    private val context: Context,
    private val viewModel: ViewModel,
    private val arrayList: ArrayList<Product>
) : RecyclerView.Adapter<ProductAdpater.UserViewHolder>() {

    class UserViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.product_list, parent, false)
        return UserViewHolder(root)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val product = arrayList[position]
        holder.itemView.productNameTV.text = product.name
        holder.itemView.productPriceTV.text = product.price.toString()
        holder.itemView.productQuantityTV.text = product.quantity.toString()
        holder.itemView.productImageView.load(product.productImage)
        
        holder.itemView.updateProductTV.setOnClickListener {
            val action = ProductListFragmentDirections.actionProductListFragmentToProductUpdateFragment(product)
            Navigation.findNavController(holder.itemView).navigate(action)
        }
        
        holder.itemView.deleteProduct.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Are you sure you want to Delete?").setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    // Delete selected note from database
                    viewModel.deleteProduct(product)
                    notifyItemRemoved(arrayList.indexOf(product))

                }.setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
    }

    override fun getItemCount(): Int {
        if (arrayList.size == 0) {
            Toast.makeText(context, "Product list is empty", Toast.LENGTH_SHORT).show()
        }
        return arrayList.size
    }
}