package com.shokal.roomdatabasewithmultipletable.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.shokal.roomdatabasewithmultipletable.models.Category
import com.shokal.roomdatabasewithmultipletable.models.Product
import com.shokal.roomdatabasewithmultipletable.repositories.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ViewModel(application: Application): AndroidViewModel(application) {
    var categoryList: LiveData<List<Category>>
    var productList : LiveData<List<Product>>
    private var repository : Repository
    init {
        repository = Repository(application)
        categoryList = repository.getAllCategory()
        productList = repository.getAllProducts()
    }

    fun addCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCategory(category)
        }
    }

    fun updateCategory(category: Category){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCategory(category)
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCategory(category)
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertProduct(product)
        }
    }

    fun updateProduct(product: Product){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProduct(product)
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProduct(product)
        }
    }
}