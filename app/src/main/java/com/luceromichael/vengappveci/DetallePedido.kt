package com.luceromichael.vengappveci

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detalle_pedido.*
import kotlinx.android.synthetic.main.fragment_pedidos.*
import kotlinx.android.synthetic.main.fragment_pedidos.buttonRegresar

class DetallePedido : AppCompatActivity() {
    var listaDetPedido = arrayListOf<DetallePedidoModelClass>()
    lateinit var idPed : String
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val TAG = "DetallePedido"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_pedido)
        idPed = intent.getStringExtra("pedido").toString()
        Toast.makeText(this, idPed, Toast.LENGTH_LONG).show()
        db.collection("pedidos/"+idPed+"/detallePedidos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    listaDetPedido.add(
                        DetallePedidoModelClass(
                            getProduct(document.data.get("productoId").toString()) as ProductoModelClass,
                            document.data.get("cat").toString().toInt(),
                            document.data.get("total").toString().toFloat()
                        ))
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }



        buttonRegresar.setOnClickListener{
            super.onBackPressed()
        }
    }

    fun getProduct(id:String):ProductoModelClass?{
        var producto = ProductoModelClass("Not found","0".toFloat(),"","Not found")
        db.collection("productos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if(document.id.equals(id)){
                        producto = ProductoModelClass(
                            document.data.get("nombre").toString(),
                            document.data.get("precio").toString().toFloat(),
                            document.data.get("image").toString(),
                            document.data.get("detalle").toString()
                        )
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
        return producto
    }
}

