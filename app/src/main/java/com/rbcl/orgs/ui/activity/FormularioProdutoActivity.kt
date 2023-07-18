package com.rbcl.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import coil.load
import com.rbcl.orgs.dao.ProdutosDao
import com.rbcl.orgs.databinding.ActivityFormularioProdutoBinding
import com.rbcl.orgs.databinding.FormularioImagemBinding
import com.rbcl.orgs.model.Produto
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoSalvar()
        configuraDialogImagem()
    }

    private fun configuraDialogImagem() {
        binding.activityFormularioProdutoImagem.setOnClickListener {
            val bindingFormularioImagem = FormularioImagemBinding.inflate(layoutInflater)
            configuraBotaoCarregarImagem(bindingFormularioImagem)
            configureDialog(bindingFormularioImagem)
        }
    }

    private fun configuraBotaoCarregarImagem(bindingFormularioImagem: FormularioImagemBinding) {
        bindingFormularioImagem.formularioImagemBotaoCarregar.setOnClickListener {
            val url = bindingFormularioImagem.formularioImagemInputEdittextUrl.text.toString()
            bindingFormularioImagem.formularioImagemImagemview.load(url)
        }
    }

    private fun configureDialog(bindingFormularioImagem: FormularioImagemBinding) {
        AlertDialog.Builder(this@FormularioProdutoActivity)
            .setPositiveButton("Positivo") { _, _ ->
                confirmeImagem(bindingFormularioImagem)
            }
            .setNegativeButton("Negativo") { _, _ -> }
            .setView(bindingFormularioImagem.root)
            .show()
    }

    private fun confirmeImagem(bindingFormularioImagem: FormularioImagemBinding) {
        val url = bindingFormularioImagem.formularioImagemInputEdittextUrl.text.toString()
        binding.activityFormularioProdutoImagem.load(url)
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

        val novoProduto = Produto(nome, descricao, valor)
        return novoProduto
    }
}