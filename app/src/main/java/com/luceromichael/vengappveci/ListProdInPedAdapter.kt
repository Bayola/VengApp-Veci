package com.luceromichael.vengappveci

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListProdInPedAdapter (private val context: Activity, private val detallesPedido: ArrayList<DetallePedidoModelClass>)   : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.lista_productos_in_pedido, null, true)

        val textViewName = rowView.findViewById<TextView>(R.id.textViewNombre)
        val textViewCantidad = rowView.findViewById<TextView>(R.id.textViewCantidad)
        val textViewPrecio = rowView.findViewById<TextView>(R.id.textViewPrecioP)
        val textViewSubtotal = rowView.findViewById<TextView>(R.id.textViewSubtotal)
        textViewName.text = "${detallesPedido[position].producto.name}"
        textViewCantidad.text = "${detallesPedido[position].cant}"
        textViewPrecio.text = "$${detallesPedido[position].producto.price}"
        textViewSubtotal.text = "$${detallesPedido[position].subTotal}"
        return rowView
    }

    override fun getItem(position: Int): Any? {
        return detallesPedido.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return detallesPedido.size
    }
}
