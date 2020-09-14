package com.luceromichael.vengappveci

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.luceromichael.vengappveci.ui.pedidos.PedidosFragment

class ListPedidosAdapter(private val context: PedidosFragment, private val pedidos: ArrayList<PedidoModelClass>)   : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.pedido, null, true)

        val textViewFecha = rowView.findViewById<TextView>(R.id.textViewFechaPedido)
        val textViewTotal = rowView.findViewById<TextView>(R.id.textViewTotalPedido)
        textViewFecha.text = "${pedidos[position].fecha}"
        textViewTotal.text = "${pedidos[position].total}"
        return rowView
    }

    override fun getItem(position: Int): Any? {
        return pedidos.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return pedidos.size
    }
}

