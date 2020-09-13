package com.luceromichael.vengappveci.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.luceromichael.vengappveci.DetallePedido
import com.luceromichael.vengappveci.DetalleProducto
import com.luceromichael.vengappveci.PantallaCarrito
import com.luceromichael.vengappveci.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_logout.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it

            imageView8.setOnClickListener {
                val intent = Intent(activity, DetalleProducto::class.java)
                startActivity(intent)
            }

            imageViewCarrito.setOnClickListener {
                val intent = Intent(activity, PantallaCarrito::class.java)
                startActivity(intent)
            }

        })
        return root
    }
}