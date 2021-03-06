package com.luceromichael.vengappveci

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detalle_pedido.*
import kotlinx.android.synthetic.main.activity_detalle_producto.*
import kotlinx.android.synthetic.main.fragment_home.*

class DetalleProducto : AppCompatActivity() {
    var cant = 0
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val TAG = "DetalleProducto"
    lateinit var producto:ProductoModelClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)
        var nombre = findViewById<TextView>(R.id.textViewNombreP)
        var precio = findViewById<TextView>(R.id.textViewPrecioP)
        var detalle = findViewById<TextView>(R.id.textViewDetalleP)
        var cantidad = findViewById<TextView>(R.id.textViewNumeroP)
        var image = findViewById<ImageView>(R.id.imageViewProductoDetalleProducto)
        db.collection("productos").document(intent.getStringExtra("id").toString())
            .get()
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
            .addOnCompleteListener {doc ->
                var result = doc.result
                if(result!!.exists()) {
                    producto = ProductoModelClass(
                        result.id,
                        result.get("nombre").toString(),
                        result.get("precio").toString().toFloat(),
                        result.get("imagen").toString(),
                        result.get("detalle").toString()
                    )
                    Log.d(TAG, "${result.id} => ${result.data}")
                    nombre.text = producto.name
                    precio.text = producto.price.toString()
                    detalle .text = producto.detail
                    Glide
                        .with(this)
                        .asBitmap()
                        .placeholder(R.drawable.loadproduct)
                        .load(producto.image)
                        .into(object : SimpleTarget<Bitmap?>(191, 172) {
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: Transition<in Bitmap?>?
                            )
                            {
                                image.setImageBitmap(resource)
                            }
                        })
                }
            }

        cantidad.text = cant.toString()
        var menos = findViewById<ImageView>(R.id.imageViewMenosCant)
        var mas = findViewById<ImageView>(R.id.imageViewMasCant)


        menos.setOnClickListener {
            if (cant>0){
                cant--
                cantidad.text = cant.toString()
            }
        }
        mas.setOnClickListener {
            cant++
            cantidad.text = cant.toString()
        }

        buttonAgregar.setOnClickListener {
            if(cant>0){
                carrito.add(DetallePedidoModelClass(producto, cant, (cant*producto.price)))
                totalCarrito += cant*producto.price
                Log.d("Producto: ", DetallePedidoModelClass(producto, cant, (cant*producto.price)).toString())
                super.onBackPressed()
            }
        }
    }
}