package com.luceromichael.vengappveci.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.luceromichael.vengappveci.ProductoHomeAdapter
import com.luceromichael.vengappveci.ProductoModelClass
import com.luceromichael.vengappveci.R
import com.luceromichael.vengappveci.mAuth
import com.luceromichael.vengappveci.ui.carrito.CarritoFragment
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {


    private lateinit var productos: Array<ProductoModelClass>
    var listproductos = arrayListOf<ProductoModelClass>()

    private lateinit var homeViewModel: HomeViewModel
    private val layoutManager: RecyclerView.LayoutManager? = null
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val TAG = "HomeFragement"


   private var productoHomeAdapter : ProductoHomeAdapter? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listproductos = arrayListOf<ProductoModelClass>()

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it


            db.collection("productos")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        listproductos.add(
                            ProductoModelClass(
                                document.id,
                                document.data.get("nombre").toString(),
                                document.data.get("precio").toString().toFloat(),
                                document.data.get("imagen").toString(),
                                document.data.get("detalle").toString()
                        ))
                    }
                    productos = listproductos.toTypedArray()
                    productoHomeAdapter = ProductoHomeAdapter(
                        this.requireContext(),
                        productos,
                        R.layout.item_producto
                    )
                    recicleViewHome.layoutManager = GridLayoutManager(this.requireContext(), 3)
                    recicleViewHome.setAdapter(productoHomeAdapter)
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }

            imageViewCarrito.setOnClickListener{
                //val intent = Intent(activity, com.luceromichael.vengappveci.ui.login.CarritoFragment::class.java)
                //startActivity(intent)
            }

        })

        return root
    }


}

