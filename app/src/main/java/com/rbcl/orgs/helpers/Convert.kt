package com.rbcl.orgs.helpers

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

object Convert {
    fun formatarValorParaMoedaBrasileira(valor: BigDecimal): String? {
        val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        return formatador.format(valor)
    }
}