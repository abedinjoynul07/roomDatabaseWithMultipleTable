package com.shokal.roomdatabasewithmultipletable.repositories

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shokal.roomdatabasewithmultipletable.models.Category
import com.shokal.roomdatabasewithmultipletable.models.Product

@Dao
interface DataAccessObject {
    @Query("SELECT * FROM categories")
    fun getAllCategory(): LiveData<List<Category>>

    @Query("SELECT name FROM categories")
    suspend fun getAllCAtegoryName() : List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<Product>>

    @Insert
    suspend fun insertProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

}