package com.luceromichael.vengappveci

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DetalleProducto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)
        var producto = ProductoModelClass(
            intent.getStringExtra("id").toString(),
            intent.getStringExtra("name").toString(),
            intent.getStringExtra("price").toString().toFloat(),
            intent.getStringExtra("image").toString(),
            intent.getStringExtra("detail").toString()
        )

        var nombre = findViewById<TextView>(R.id.textViewNombreP)
        var precio = findViewById<TextView>(R.id.textViewPrecioP)
        var detalle = findViewById<TextView>(R.id.textViewNombreP)
        var cantidad = findViewById<TextView>(R.id.textViewNombreP)
        var image = findViewById<ImageView>(R.id.imageViewProductoDetalleProducto)

        nombre.text = producto.name
        precio.text = producto.price.toString()
        detalle .text = producto.detail

    }
}