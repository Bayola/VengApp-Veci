package com.luceromichael.vengappveci.ui.pedidos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.luceromichael.vengappveci.*


class PedidosFragment : Fragment() {
    var pedidos = arrayListOf<PedidoModelClass>()
    var selectedPedidoPosition = 0
    lateinit var v: View
    lateinit var rvpedidos: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_pedidos, container, false)
        rvpedidos = v.findViewById(R.id.rv_pedidos)

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

        val pedidoAdapter:PedidoAdapter = PedidoAdapter(requireContext(), pedidos)
        rvpedidos.layoutManager = LinearLayoutManager(activity)
        rvpedidos.adapter = pedidoAdapter
        return v
    }

    private val TAG = "CarritoFragment"
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*var listView = requireActivity().findViewById<View>(R.id.listViewPedidos) as ListView
        rvpedidos.setOnItemClickListener { parent, view, position, id ->
            selectedPedidoPosition = position
            Toast.makeText(contexto, "pedido selected "+selectedPedidoPosition, Toast.LENGTH_LONG).show()
        }*/



    }

}
