package com.shokal.roomdatabasewithmultipletable.repositories

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.shokal.roomdatabasewithmultipletable.adapters.Converter
import com.shokal.roomdatabasewithmultipletable.models.Category
import com.shokal.roomdatabasewithmultipletable.models.Product
import de.hdodenhof.circleimageview.BuildConfig

@Database(entities = [Category::class, Product::class], version = 3)
@TypeConverters(Converter::class)
abstract class DataBase : RoomDatabase() {
    abstract fun universalDao() : DataAccessObject

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null
        fun getDatabase(context: Context): DataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "pms"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}