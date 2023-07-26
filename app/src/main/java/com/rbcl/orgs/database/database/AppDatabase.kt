package com.rbcl.orgs.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rbcl.orgs.database.converter.Converters
import com.rbcl.orgs.database.dao.ProdutoDao
import com.rbcl.orgs.model.Produto

@Database(
    entities = [Produto::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao
}