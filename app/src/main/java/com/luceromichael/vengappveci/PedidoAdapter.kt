package com.luceromichael.vengappveci

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class PedidoAdapter : RecyclerView.Adapter<PedidoAdapter.MyViewHolder> {

    lateinit var mContext: Context
    lateinit var mData: List<PedidoModelClass>

    constructor(mContext: Context, mData: List<PedidoModelClass>) : super() {
        this.mContext = mContext
        this.mData = mData
    }


    class MyViewHolder: RecyclerView.ViewHolder {
        lateinit var fechaPed: TextView
        lateinit var precioPed: TextView

        constructor(itemView: View) : super(itemView) {
            fechaPed = itemView.findViewById(R.id.textViewFechaPedido) as TextView
            precioPed = itemView.findViewById(R.id.textViewTotalPedido) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var v: View = LayoutInflater.from(mContext).inflate(R.layout.pedido, parent, false)
        var vHolder = MyViewHolder(v)
        return vHolder
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.fechaPed.text = mData.get(position).fecha
        holder.precioPed.text = mData.get(position).total.toString()

    }
}