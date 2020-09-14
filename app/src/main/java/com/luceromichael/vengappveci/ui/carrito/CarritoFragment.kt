package com.luceromichael.vengappveci.ui.carrito

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.luceromichael.vengappveci.*


class CarritoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_carrito, container, false)
    }

    private val TAG = "CarritoFragment"
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}