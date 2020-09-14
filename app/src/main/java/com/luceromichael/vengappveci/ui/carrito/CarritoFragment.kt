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
import kotlinx.android.synthetic.main.activity_detalle_pedido.*
import kotlinx.android.synthetic.main.fragment_carrito.*
import kotlinx.android.synthetic.main.fragment_pedidos.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class CarritoFragment : Fragment() {

    private val TAG = "CarritoFragment"
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var carritoAdaptador:CarritoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_carrito, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val date = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        var pedido = PedidoModelClass(null, date, carrito, totalCarrito)

        //adaptador
        //lista productos = carrito
        //sumacarrito = totalcarrito

        val inflater = this.layoutInflater
        val rowHeaderView = inflater.inflate(R.layout.lista_productos_in_pedido, null, false)
        listViewCarrito.addHeaderView(rowHeaderView)
        carritoAdaptador = CarritoAdapter(this,carrito)
        listViewCarrito.adapter = carritoAdaptador


        buttonHacerPed.setOnClickListener {
            savePedido(PedidoModelClass(currentUSer.user?.uid,date,carrito, totalCarrito))
            carrito = arrayListOf<DetallePedidoModelClass>()
            totalCarrito = "0".toFloat()
        }

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

    fun onClickRegresar() {
        this.requireActivity().onBackPressed()
    }

}