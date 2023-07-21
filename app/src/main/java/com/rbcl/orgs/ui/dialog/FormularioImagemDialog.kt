package com.rbcl.orgs.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.rbcl.orgs.databinding.FormularioImagemBinding
import com.rbcl.orgs.extensions.tentaCarregarImagem

class FormularioImagemDialog(
    private val context: Context
    ) {

    fun mostra(urlPadrao: String? = null, quandoImagemCarregada: (imagem: String?) -> Unit) {
        FormularioImagemBinding.inflate(LayoutInflater.from(context)).apply {
            urlPadrao?.let {
                formularioImagemImagemview.tentaCarregarImagem(it)
                formularioImagemInputEdittextUrl.setText(it)
            }

            configureDialog(quandoImagemCarregada, this)
            formularioImagemBotaoCarregar.setOnClickListener {
                configuraBotaoCarregarImagem(this)
            }
        }
    }

    private fun configureDialog(
        quandoImagemCarregada: (imagem: String?) -> Unit,
        binding: FormularioImagemBinding
    ) {
        AlertDialog.Builder(context)
            .setPositiveButton("Confirmar") { _, _ ->
                confirmeImagem(quandoImagemCarregada, binding)
        }
            .setNegativeButton("Cancelar") { _, _ -> }
            .setView(binding.root)
                .show()
    }

    private fun configuraBotaoCarregarImagem(binding: FormularioImagemBinding) {
        binding.formularioImagemBotaoCarregar.setOnClickListener {
            val url = binding.formularioImagemInputEdittextUrl.text.toString()
            binding.formularioImagemImagemview.tentaCarregarImagem(url)
        }
    }

    private fun confirmeImagem(
        quandoImagemCarregada: (imagem: String?) -> Unit,
        binding: FormularioImagemBinding
    ) {
        val url = binding.formularioImagemInputEdittextUrl.text.toString()
        quandoImagemCarregada(url)
    }
}
