package com.rbcl.orgs.ui.recyclerview.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.rbcl.orgs.databinding.ProdutoItemBinding
import com.rbcl.orgs.extensions.tentaCarregarImagem
import com.rbcl.orgs.helpers.Convert
import com.rbcl.orgs.model.Produto
import com.rbcl.orgs.ui.activity.DetalhesProdutoActivity

class ListaProdutosAdapter(
    produtos: List<Produto>,
    private val context: Context
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    private val produtos = produtos.toMutableList()

    class ViewHolder(private val binding: ProdutoItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun vincular(produto: Produto) {
            val nome = binding.produtoItemNome
            nome.text = produto.nome

            val descricao = binding.produtoItemDescricao
            descricao.text = produto.descricao

            val valor = binding.produtoItemValor
            val valorEmMoeda = Convert.formatarValorParaMoedaBrasileira(produto.valor)
            valor.text = valorEmMoeda

            if(produto.imagem == null){
                binding.imageView.visibility = View.GONE
            } else {
                binding.imageView.tentaCarregarImagem(produto.imagem)
            }

            binding.root.setOnClickListener {
                val thisContext = binding.root.context
                val intent = Intent(
                    thisContext,
                    DetalhesProdutoActivity::class.java)
                    .putExtra(
                        "produto",
                        Gson().toJson(produto)
                    )
                thisContext.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ProdutoItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = produtos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = produtos[position]
        holder.vincular(produto)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun atualiza(produtos: List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)
        notifyDataSetChanged()
    }

}
