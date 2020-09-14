package com.luceromichael.vengappveci

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_pedidos.*


class DetallePedido : AppCompatActivity() {
    var listaDetPedido = arrayListOf<DetallePedidoModelClass>()
    lateinit var idPed : String
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val TAG = "DetallePedido"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_pedido)
        idPed = intent.getStringExtra("pedido").toString()
        db.collection("pedidos/"+idPed+"/detallePedidos")
            .get()
            .addOnSuccessListener { result ->
                val inflater = this.layoutInflater
                val rowHeaderView = inflater.inflate(R.layout.lista_productos_in_pedido, null, false)
                listViewDetallesPedido.addHeaderView(rowHeaderView)
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    saveData(
                        document.data.get("productoId").toString(),
                        document.data.get("cat").toString().toInt(),
                        document.data.get("total").toString().toFloat()
                    )
                }

                Log.d(TAG, listaDetPedido.toString())

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }



        buttonRegresar.setOnClickListener{
            super.onBackPressed()
        }
    }

    fun saveData(id:String, cant:Int, total :Float){
        var producto: ProductoModelClass = ProductoModelClass("", "0".toFloat(), "", "")
        var task = db.collection("productos").document(id)
            .get()
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
        return producto
    }
}

