package com.luceromichael.vengappveci.ui.home


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.luceromichael.vengappveci.ProductoHomeAdapter
import com.luceromichael.vengappveci.ProductoModelClass
import com.luceromichael.vengappveci.R
import com.luceromichael.vengappveci.ui.carrito.CarritoFragment
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {


    private lateinit var productos: Array<ProductoModelClass>
    var listproductos = arrayListOf<ProductoModelClass>()
    private lateinit var buscar : EditText
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
                            )
                        )
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

            imageViewCarrito.setOnClickListener {
                val nextFrag = CarritoFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit()
            }

            buscar = view?.findViewById(R.id.editTextBuscar)!!
            buscar.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    filtrar(s.toString())
                }
            })



        })

        return root
    }

    fun filtrar(texto: String) {
        val filtrarLista: ArrayList<ProductoModelClass> = ArrayList()
        for (product in productos) {
            Log.d("mensaje de coprovacion",product.name)


            if (product.name.toLowerCase().contains(texto.toLowerCase().trim())) {

                filtrarLista.add(product)
            }

        }
        productoHomeAdapter?.filtrar(filtrarLista)
    }
}

