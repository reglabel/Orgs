package com.rbcl.orgs.ui.activity

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.rbcl.orgs.R
import com.rbcl.orgs.model.Produto
import com.rbcl.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import java.math.BigDecimal

class MainActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler)
        recyclerView.adapter = ListaProdutosAdapter(context = this@MainActivity, produtos = listOf(
            Produto(nome= "teste", descricao="teste desc", valor=BigDecimal("19.99")),
            Produto(nome= "teste", descricao="teste desc", valor=BigDecimal("19.99")),
            Produto(nome= "teste", descricao="teste desc", valor=BigDecimal("19.99"))
        ))
        //recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
    }
}