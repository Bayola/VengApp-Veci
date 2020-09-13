package com.luceromichael.vengappveci

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class PedidoAdapter (private val context: Activity, private val pedidos: ArrayList<PedidoModelClass>)   : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.pedido, null, true)

        val textViewFechaPedido = rowView.findViewById<TextView>(R.id.textViewFechaPedido)
        val textViewTotalPedido = rowView.findViewById<TextView>(R.id.textViewTotalPedido)

        textViewFechaPedido.text = "${pedidos[position].fecha}"
        textViewTotalPedido.text = "${pedidos[position].total}"
        Log.d("TAG", "${pedidos[position].fecha} => ${pedidos[position].total}")

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