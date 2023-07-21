package com.rbcl.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rbcl.orgs.R
import com.rbcl.orgs.dao.ProdutosDao
import com.rbcl.orgs.databinding.ActivityFormularioProdutoBinding
import com.rbcl.orgs.extensions.tentaCarregarImagem
import com.rbcl.orgs.model.Produto
import com.rbcl.orgs.ui.dialog.FormularioImagemDialog
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoSalvar()
        title = getString(R.string.cadastrar_produto)
        binding.activityFormularioProdutoImagem.setOnClickListener{
            FormularioImagemDialog(this).mostra(url) { imagem ->
                url = imagem
                binding.activityFormularioProdutoImagem.tentaCarregarImagem(url)
            }
        }
    }

    private fun configuraBotaoSalvar() {
        val botao = binding.activityFormularioProdutoBotaoSalvar
        val dao = ProdutosDao()

        botao.setOnClickListener {
            val novoProduto = criaProduto()
            dao.adiciona(novoProduto)
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

        return Produto(nome, descricao, valor, url)
    }
}