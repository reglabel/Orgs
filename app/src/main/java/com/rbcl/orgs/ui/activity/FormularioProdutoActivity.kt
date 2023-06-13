package com.rbcl.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.rbcl.orgs.R
import com.rbcl.orgs.dao.ProdutosDao
import com.rbcl.orgs.model.Produto
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity(R.layout.activity_formulario_produto) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuraBotaoSalvar()
    }

    private fun configuraBotaoSalvar() {
        val botao = findViewById<Button>(R.id.activity_formulario_produto_botao_salvar)
        val dao = ProdutosDao()

        botao.setOnClickListener {
            val novoProduto = criaProduto()
            dao.adiciona(novoProduto)
            finish()
        }
    }

    private fun criaProduto(): Produto {
        val campoNome = findViewById<EditText>(R.id.activity_formulario_produto_nome)
        val nome = campoNome.text.toString()

        val campoDescricao = findViewById<EditText>(R.id.activity_formulario_produto_descricao)
        val descricao = campoDescricao.text.toString()

        val campoValor = findViewById<EditText>(R.id.activity_formulario_produto_valor)
        val valor = if (campoValor.text.toString().isBlank()) {
            BigDecimal.ZERO
        } else BigDecimal(campoValor.text.toString())

        val novoProduto = Produto(nome, descricao, valor)
        return novoProduto
    }
}