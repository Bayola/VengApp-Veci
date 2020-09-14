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
import com.google.firebase.firestore.Query
import com.luceromichael.vengappveci.*
import kotlinx.android.synthetic.main.fragment_pedidos.*


class PedidosFragment : Fragment() {
    var listaPedidos = arrayListOf<PedidoModelClass>()
    lateinit var v: View
    lateinit var rvpedidos: ListView
    private val TAG = "CarritoFragment"
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_pedidos, container, false)
        rvpedidos = v.findViewById(R.id.listViewPedidos)
        //llenarpedidos
        db.collection("pedidos")
            .whereEqualTo("userUID", currentUSer.user?.uid)
            .get()
            .addOnSuccessListener { result ->
                val rowHeaderView = inflater.inflate(R.layout.pedido, null, false)
                listViewPedidos.addHeaderView(rowHeaderView)
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    listaPedidos.add(PedidoModelClass(document.data.get("date").toString(),null,document.data.get("total").toString().toFloat()))
                    val inflater = this.layoutInflater
                    var pedidoAdaptador = ListPedidosAdapter(this,listaPedidos)
                    listViewPedidos.adapter = pedidoAdaptador
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        return v
    }


}
