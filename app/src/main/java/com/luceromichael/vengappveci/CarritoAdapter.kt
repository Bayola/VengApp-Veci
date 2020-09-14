package com.luceromichael.vengappveci

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.luceromichael.vengappveci.ui.carrito.CarritoFragment

class CarritoAdapter(private val context: CarritoFragment, private val detalleCarrito: ArrayList<DetallePedidoModelClass>)   : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.lista_productos_in_pedido, null, true)

        val textViewName = rowView.findViewById<TextView>(R.id.textViewNombre)
        val textViewCantidad = rowView.findViewById<TextView>(R.id.textViewCantidad)
        val textViewPrecio = rowView.findViewById<TextView>(R.id.textViewPrecioP)
        val textViewSubtotal = rowView.findViewById<TextView>(R.id.textViewSubtotal)

        textViewName.text = "${detalleCarrito[position].producto.name}"
        textViewCantidad.text = "${detalleCarrito[position].cant.toString()}"
        textViewPrecio.text = "${detalleCarrito[position].producto.price}"
        textViewSubtotal.text = "${detalleCarrito[position].subTotal}"
        return rowView
    }

    override fun getItem(position: Int): Any? {
        return detalleCarrito.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return detalleCarrito.size
    }
}