package com.luceromichael.vengappveci.ui.carrito

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.luceromichael.vengappveci.*
import kotlinx.android.synthetic.main.fragment_carrito.*


class CarritoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_carrito, container, false)
    }

    private val TAG = "CarritoFragment"
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        buttonHacerPed.setOnClickListener({
            savePedido(PedidoModelClass("","",null, 0f))
        })
    }

    fun savePedido(carrito:PedidoModelClass){
        val pedido = hashMapOf(
            "date" to carrito.fecha,
            "total" to carrito.total,
            "userUID" to currentUSer.user!!.uid
        )
        db.collection("/pedidos")
            .add(pedido)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                saveDetalle(documentReference.id, carrito.productList)
                Toast.makeText(context, "Pedido completado.", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                Toast.makeText(context, "Error al hacer pedido.", Toast.LENGTH_LONG).show()
            }
    }

    fun saveDetalle(pedidoID:String, productList: ArrayList<DetallePedidoModelClass>?){
        if(productList!=null){
            for (detPed in productList){
                val detallePedido = hashMapOf(
                    "productoId" to detPed.producto.id,
                    "total" to detPed.subTotal,
                    "cat" to detPed.cant
                )
                db.collection("/pedidos/"+pedidoID+"/detallePedidos")
                    .add(detallePedido)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                        Toast.makeText(context, "Error al hacer pedido.", Toast.LENGTH_LONG).show()
                    }
            }
        }
    }
}