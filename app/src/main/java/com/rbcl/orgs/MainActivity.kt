package com.rbcl.orgs

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class MainActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val nome = findViewById<TextView>(R.id.nome)
        nome.text = "cesta de frutas"
        val descricao = findViewById<TextView>(R.id.descricao)
        descricao.text = "abacaxi e morango"
        val valor = findViewById<TextView>(R.id.valor)
        valor.text = "19,99"
    }
}