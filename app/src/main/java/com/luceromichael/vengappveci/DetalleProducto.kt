package com.luceromichael.vengappveci

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

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


    }
}