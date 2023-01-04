package com.shokal.roomdatabasewithmultipletable.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.shokal.roomdatabasewithmultipletable.models.Category
import com.shokal.roomdatabasewithmultipletable.models.Product

class Repository(context: Context) {
    private val db: DataAccessObject = DataBase.getDatabase(context).universalDao()

    fun getAllCategory(): LiveData<List<Category>> {
        return db.getAllCategory()
    }

    suspend fun insertCategory(category: Category) {
        return db.insertCategory(category)
    }

    suspend fun updateCategory(category: Category){
        return db.updateCategory(category)
    }

    suspend fun deleteCategory(category: Category){
        return db.deleteCategory(category)
    }

    //Products

    fun getAllProducts() : LiveData<List<Product>>{
        return db.getAllProducts()
    }

    suspend fun insertProduct(product: Product){
        return db.insertProduct(product)
    }

    suspend fun updateProduct(product: Product){
        return db.updateProduct(product)
    }

    suspend fun deleteProduct(product: Product){
        return db.deleteProduct(product)
    }
}