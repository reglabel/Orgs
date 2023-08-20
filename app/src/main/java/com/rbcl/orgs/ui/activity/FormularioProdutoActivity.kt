package com.rbcl.orgs.ui.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rbcl.orgs.R
import com.rbcl.orgs.database.database.AppDatabase
import com.rbcl.orgs.databinding.ActivityFormularioProdutoBinding
import com.rbcl.orgs.extensions.tentaCarregarImagem
import com.rbcl.orgs.helpers.CHAVE_PRODUTO
import com.rbcl.orgs.model.Produto
import com.rbcl.orgs.ui.dialog.FormularioImagemDialog
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }
    private var url: String? = null
    private var idProduto = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoSalvar()
        title = getString(R.string.cadastrar_produto)
        binding.activityFormularioProdutoImagem.setOnClickListener {
            FormularioImagemDialog(this).mostra(url) { imagem ->
                url = imagem
                binding.activityFormularioProdutoImagem.tentaCarregarImagem(url)
            }
        }

        try {
            val produto = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(CHAVE_PRODUTO, Produto::class.java)
            } else {
                intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)
            }
            if (produto != null) {
                carregaProduto(produto)
            }
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Ocorreu um erro ao carregar o produto: ${e}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun carregaProduto(produto: Produto) {
        idProduto = produto.id
        title = "Editar produto"
        url = produto.imagem
        binding.activityFormularioProdutoImagem.tentaCarregarImagem(produto.imagem)
        binding.activityFormularioProdutoNomeInputEdittext.setText(produto.nome)
        binding.activityFormularioProdutoDescricaoInputEdittext.setText(produto.descricao)
        binding.activityFormularioProdutoValorInputEdittext.setText(produto.valor.toPlainString())
    }

    private fun configuraBotaoSalvar() {
        val botao = binding.activityFormularioProdutoBotaoSalvar
        val db = AppDatabase.instancia(applicationContext)
        val produtoDao = db.produtoDao()

        botao.setOnClickListener {
            val novoProduto = criaProduto()
            if (idProduto > 0L) {
                produtoDao.atualiza(novoProduto)
            } else {
                produtoDao.salva(novoProduto)
            }
            finish()
        }
    }

    private fun criaProduto(): Produto {
        val campoNome = binding.activityFormularioProdutoNomeInputEdittext
        val nome = campoNome.text.toString()

        val campoDescricao = binding.activityFormularioProdutoDescricaoInputEdittext
        val descricao = campoDescricao.text.toString()

        val campoValor = binding.activityFormularioProdutoValorInputEdittext
        val valor = if (campoValor.text.toString().isBlank()) {
            BigDecimal.ZERO
        } else BigDecimal(campoValor.text.toString())

        return Produto(
            id = idProduto,
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url
        )
    }
}