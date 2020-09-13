package com.luceromichael.vengappveci.ui.pedidos

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.luceromichael.vengappveci.*
import kotlinx.android.synthetic.main.fragment_pedidos.*


class PedidosFragment : Fragment() {
    var pedidos = arrayListOf<PedidoModelClass>()
    var selectedPedidoPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pedidos, container, false)
    }

    private val TAG = "CarritoFragment"
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listViewPedidos.setOnItemClickListener { parent, view, position, id ->
            selectedPedidoPosition = position
            Toast.makeText(contexto, "pedido selected "+selectedPedidoPosition, Toast.LENGTH_LONG).show()
        }

        //llenarpedidos
        db.collection("/pedidos")
            .whereEqualTo("userUID", currentUSer.user?.uid)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    pedidos.add(PedidoModelClass(document.data.get("date").toString(),null,document.data.get("total").toString().toFloat()))
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        listViewPedidos.adapter = PedidoAdapter(requireContext() as Activity, pedidos)
    }
}