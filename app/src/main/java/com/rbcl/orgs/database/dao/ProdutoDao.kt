package com.rbcl.orgs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rbcl.orgs.model.Produto

@Dao
interface ProdutoDao {
    @Query("SELECT * FROM Produto")
    fun getAll(): List<Produto>

    @Insert
    fun salva(vararg produto: Produto)
}