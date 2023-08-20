package com.rbcl.orgs.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rbcl.orgs.database.converter.Converters
import com.rbcl.orgs.database.dao.ProdutoDao
import com.rbcl.orgs.model.Produto

@Database(
    entities=[Produto::class],
    version=1,
    exportSchema=true
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao

    companion object{
        fun instancia(context: Context): AppDatabase{
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "orgs.db"
            ).allowMainThreadQueries()
                .build()
        }
    }
}