package com.shokal.roomdatabasewithmultipletable.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.shokal.roomdatabasewithmultipletable.R
import com.shokal.roomdatabasewithmultipletable.fragments.CategoryListFragmentDirections
import com.shokal.roomdatabasewithmultipletable.fragments.ProductListFragmentDirections
import com.shokal.roomdatabasewithmultipletable.models.Category
import kotlinx.android.synthetic.main.category_list.view.*
import kotlinx.android.synthetic.main.product_list.view.*

class CategoryAdapter(
    private val context: Context,
    private val viewModel: com.shokal.roomdatabasewithmultipletable.viewModels.ViewModel,
    private val arrayList: ArrayList<Category>
) : RecyclerView.Adapter<CategoryAdapter.UserViewHolder>() {

    class UserViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.category_list, parent, false)
        return UserViewHolder(root)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val category = arrayList[position]
        holder.itemView.categoryName.text = category.name

        holder.itemView.updateCategory.setOnClickListener {
            val action = CategoryListFragmentDirections.actionCategoryListFragmentToCategoryUpdateFragment(category)
            Navigation.findNavController(holder.itemView).navigate(action)
        }

        holder.itemView.deleteCategory.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Are you sure you want to Delete?").setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    // Delete selected note from database
                    viewModel.deleteCategory(category)
                    notifyItemRemoved(arrayList.indexOf(category))

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
            Toast.makeText(context, "Category list is empty", Toast.LENGTH_SHORT).show()
        }
        return arrayList.size
    }
}