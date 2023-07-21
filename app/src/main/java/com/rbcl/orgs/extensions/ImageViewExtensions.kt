package com.rbcl.orgs.extensions

import android.os.Build
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import com.rbcl.orgs.R

fun ImageView.tentaCarregarImagem(url: String? = null) {
    load(url, ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()){
        fallback(R.drawable.imagem_padrao)
        placeholder(R.drawable.drawable_background_loading_image)
        error(R.drawable.erro)
    }
}