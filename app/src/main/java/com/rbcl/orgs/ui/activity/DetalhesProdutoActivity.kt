package com.rbcl.orgs.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rbcl.orgs.R
import com.rbcl.orgs.database.database.AppDatabase
import com.rbcl.orgs.databinding.ActivityDetalhesProdutoBinding
import com.rbcl.orgs.extensions.tentaCarregarImagem
import com.rbcl.orgs.helpers.CHAVE_PRODUTO
import com.rbcl.orgs.helpers.Convert
import com.rbcl.orgs.model.Produto

class DetalhesProdutoActivity : AppCompatActivity() {
    private lateinit var currentProduto: Produto
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (::currentProduto.isInitialized) {
            val db = AppDatabase.instancia(applicationContext)
            val produtoDao = db.produtoDao()
            when (item.itemId) {
                R.id.menu_detalhes_produto_excluir -> {
                    produtoDao.remove(currentProduto)
                    finish()
                }
                R.id.menu_detalhes_produto_editar -> {
                    Intent(this, FormularioProdutoActivity::class.java).apply {
                        putExtra(CHAVE_PRODUTO, currentProduto)
                        startActivity(this)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto() {
        try {
            val produto = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(CHAVE_PRODUTO, Produto::class.java)
            } else {
                intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)
            }
            if (produto != null) {
                currentProduto = produto
                preencheCampos(produto)
            } else {
                finish()
            }
        } catch (e: Exception) {
            Toast.makeText(
                this@DetalhesProdutoActivity,
                "Ocorreu um erro ao obter o produto: ${e}",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }

    private fun preencheCampos(produtoCarregado: Produto) {
        with(binding) {
            activityDetalhesDoProdutoImagem.tentaCarregarImagem(produtoCarregado.imagem)
            activityDetalhesDoProdutoTitulo.text = produtoCarregado.nome
            activityDetalhesDoProdutoTagPreco.text =
                Convert.formatarValorParaMoedaBrasileira(produtoCarregado.valor)
            activityDetalhesDoProdutoDescricao.text = produtoCarregado.descricao
        }
    }
}