package com.rbcl.orgs.ui.recyclerview.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rbcl.orgs.databinding.ProdutoItemBinding
import com.rbcl.orgs.extensions.tentaCarregarImagem
import com.rbcl.orgs.helpers.Convert
import com.rbcl.orgs.model.Produto

class ListaProdutosAdapter(
    produtos: List<Produto>,
    var quandoClicaNoItem: (produto: Produto) -> Unit = {},
    private val context: Context
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    private val produtos = produtos.toMutableList()

    inner class ViewHolder(private val binding: ProdutoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var produto: Produto

        init {
            itemView.setOnClickListener {
                if (::produto.isInitialized) {
                    quandoClicaNoItem(produto)
                }
            }
        }

        fun vincular(produto: Produto) {
            this.produto = produto
            val nome = binding.produtoItemNome
            nome.text = produto.nome

            val descricao = binding.produtoItemDescricao
            descricao.text = produto.descricao

            val valor = binding.produtoItemValor
            val valorEmMoeda = Convert.formatarValorParaMoedaBrasileira(produto.valor)
            valor.text = valorEmMoeda

            if (produto.imagem == null) {
                binding.imageView.visibility = View.GONE
            } else {
                binding.imageView.tentaCarregarImagem(produto.imagem)
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
