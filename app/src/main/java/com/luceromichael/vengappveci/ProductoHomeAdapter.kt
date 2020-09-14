package com.luceromichael.vengappveci

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition



class ProductoHomeAdapter(
    private val context: Context,

    private val productos: Array<ProductoModelClass>,

    private val layout: Int
) : RecyclerView.Adapter<ProductoHomeAdapter.ViewHolder>() {
    var listaId = arrayListOf<String>()
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

        holder.name.text =  "${productos[position].name}"
        holder.price.text =  "${productos[position].price}"
        var imageUrl = productos[position].image as String
        Glide
            .with(context)
            .asBitmap()
            .load(imageUrl)
            .into(object : SimpleTarget<Bitmap?>(100, 100) {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                )
                {
                    holder.image.setImageBitmap(resource)
                }
            })
        Log.d("producto id no image: ",productos[position].id)
        if(currentUSer.user !=null){
            holder.image.setOnClickListener {
                Log.d("producto id: ",productos[position].id)
                val intent = Intent(context, DetalleProducto::class.java)
                intent.putExtra("id", productos[position].id)
                context.startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int {
        return productos.size
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

