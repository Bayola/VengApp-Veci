package com.luceromichael.vengappveci

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class ProductoHomeAdapter(
    private val context: Context,
    private val animals: Array<String>,
    private val nombres: Array<String>,
    private val precios: Array<String>,

    private val layout: Int
) : RecyclerView.Adapter<ProductoHomeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(context).inflate(layout, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        Glide.with(context).load(animals[position]).into(holder.image)
        holder.name.setText(nombres[position])
        holder.price.setText(precios[position])
    }

    override fun getItemCount(): Int {
        return animals.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var name: TextView
        var price: TextView

        init {

            name = itemView.findViewById(R.id.textViewNameItemProduct) as TextView
            price = itemView.findViewById(R.id.textViewPrecioItemProduct) as TextView
            image =
                itemView.findViewById<View>(R.id.imageViewItemProduct) as ImageView
        }
    }

}

