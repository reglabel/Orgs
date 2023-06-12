package com.rbcl.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.rbcl.orgs.R
import com.rbcl.orgs.dao.ProdutosDao
import com.rbcl.orgs.model.Produto
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity(R.layout.activity_formulario_produto) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("onCreate", "onCreate oooooooooo")

        val botao = findViewById<Button>(R.id.botao_salvar)

        botao.setOnClickListener {
            val campoNome = findViewById<EditText>(R.id.nome)
            val nome = campoNome.text.toString()

            val campoDescricao = findViewById<EditText>(R.id.descricao)
            val descricao = campoDescricao.text.toString()

            val campoValor = findViewById<EditText>(R.id.valor)
            val valor = if(campoValor.text.toString().isBlank()){
                BigDecimal.ZERO
            } else BigDecimal(campoValor.text.toString())

            val novoProduto = Produto(nome, descricao, valor)

            Log.i("FormularioProduto", "onCreate: $novoProduto")

            val dao = ProdutosDao()
            dao.adiciona(novoProduto)
            Log.i("FormularioProduto", "onCreate: ${dao.buscaTodos()}")

            finish()
        }
    }
}