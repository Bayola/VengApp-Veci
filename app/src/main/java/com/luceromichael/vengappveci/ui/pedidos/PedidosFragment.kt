package com.luceromichael.vengappveci.ui.pedidos

import android.content.Intent
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
    private val TAG = "PedidosFragment"
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var pedidoAdaptador:ListPedidosAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_pedidos, container, false)
        //llenarpedidos
        db.collection("/pedidos")
            .whereEqualTo("userUID", currentUSer.user?.uid)
            .get()
            .addOnSuccessListener { result ->
                val rowHeaderView = inflater.inflate(R.layout.pedido, null, false)
                listViewPedidos.addHeaderView(rowHeaderView)
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    listaPedidos.add(PedidoModelClass(document.id,
                        document.data.get("date").toString(),
                        null,
                        document.data.get("total").toString().toFloat())
                    )
                }
                val inflater = this.layoutInflater
                pedidoAdaptador = ListPedidosAdapter(this,listaPedidos)
                listViewPedidos.adapter = pedidoAdaptador
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listViewPedidos.setOnItemClickListener { parent, view, position, id ->
            val pedido:PedidoModelClass = listaPedidos[position-1]
            val intent = Intent(requireContext(), DetallePedido::class.java)
            intent.putExtra("pedido", pedido.id)
            startActivity(intent)
        }
    }
}
