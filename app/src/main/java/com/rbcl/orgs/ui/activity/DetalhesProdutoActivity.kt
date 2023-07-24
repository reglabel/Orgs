package com.rbcl.orgs.ui.activity

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rbcl.orgs.databinding.ActivityDetalhesProdutoBinding
import com.rbcl.orgs.extensions.tentaCarregarImagem
import com.rbcl.orgs.helpers.CHAVE_PRODUTO
import com.rbcl.orgs.helpers.Convert
import com.rbcl.orgs.model.Produto

class DetalhesProdutoActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    private fun tentaCarregarProduto() {
        try {
            val produto = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(CHAVE_PRODUTO, Produto::class.java)
            } else {
                intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)
            }
            if (produto != null) {
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