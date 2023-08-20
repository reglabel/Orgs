package com.rbcl.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.rbcl.orgs.database.database.AppDatabase
import com.rbcl.orgs.databinding.ActivityListaProdutosBinding
import com.rbcl.orgs.helpers.CHAVE_PRODUTO
import com.rbcl.orgs.ui.recyclerview.adapter.ListaProdutosAdapter

class ListaProdutosActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    private val adapter = ListaProdutosAdapter(context = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.instancia(applicationContext)
        val produtoDao = db.produtoDao()
        adapter.atualiza(produtoDao.getAll())
    }

    private fun configuraFab() {
        val fab = binding.activityListaProdutosFab
        fab.setOnClickListener {
            vaiParaFormularioProduto()
        }
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this@ListaProdutosActivity, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaProdutosRecycler
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                DetalhesProdutoActivity::class.java
            ).apply {
                putExtra(CHAVE_PRODUTO, it)
            }
            startActivity(intent)
            adapter.quandoClicaEmEditar = {
                Log.i(ListaProdutosActivity::class.simpleName, "configuraRecyclerView: Editar $it")
            }
            adapter.quandoClicaEmRemover = {
                Log.i(ListaProdutosActivity::class.simpleName, "configuraRecyclerView: Remover $it")
            }
        }
    }
}