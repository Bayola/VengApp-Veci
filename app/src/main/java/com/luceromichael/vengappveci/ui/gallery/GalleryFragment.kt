package com.luceromichael.vengappveci.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.luceromichael.vengappveci.PantallaRegistro
import com.luceromichael.vengappveci.R
import com.luceromichael.vengappveci.REGISTER_KEY
import kotlinx.android.synthetic.main.activity_pantalla_registro.*
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_gallery.buttonRegistrarse

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    )
            : View? {

        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it

            buttonRegistrarse.setOnClickListener {
                val intent = Intent(activity, PantallaRegistro::class.java)
                startActivity(intent)
            }

        })

        return root


    }

}