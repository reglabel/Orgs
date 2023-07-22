package com.rbcl.orgs.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.rbcl.orgs.databinding.ActivityDetalhesProdutoBinding
import com.rbcl.orgs.extensions.tentaCarregarImagem
import com.rbcl.orgs.helpers.Convert
import com.rbcl.orgs.model.Produto

class DetalhesProdutoActivity: AppCompatActivity() {
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    private lateinit var produto: Produto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        try{
            produto = Gson().fromJson(intent.extras?.getString("produto"), Produto::class.java)
        } catch (e: Exception) {
            Toast.makeText(this@DetalhesProdutoActivity, "Ocorreu um erro ao obter o produto: ${e}", Toast.LENGTH_SHORT).show()
        }

        if(::produto.isInitialized){
            binding.activityDetalhesDoProdutoImagem.tentaCarregarImagem(produto.imagem)
            binding.activityDetalhesDoProdutoTitulo.text = produto.nome
            binding.activityDetalhesDoProdutoTagPreco.text = Convert.formatarValorParaMoedaBrasileira(produto.valor)
            binding.activityDetalhesDoProdutoDescricao.text = produto.descricao
        }

    }
}