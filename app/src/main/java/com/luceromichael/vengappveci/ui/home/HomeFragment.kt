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
import androidx.recyclerview.widget.RecyclerView
import com.luceromichael.vengappveci.PantallaCarrito
import com.luceromichael.vengappveci.ProductoHomeAdapter
import com.luceromichael.vengappveci.R
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

//    private lateinit var gridView: GridView
    private lateinit var productos: Array<String>
    private lateinit var homeViewModel: HomeViewModel
    private val layoutManager: RecyclerView.LayoutManager? = null

//    var nombresProductos = arrayOf("nombre1")
//    var imagenesProductos = intArrayOf(R.drawable.logovengapp)
   private var productoHomeAdapter : ProductoHomeAdapter? = null

//    var EXTRA_SPACE_PHOTO = "HomeFragment.SPACE_PHOTO"
//    private var mImageView: ImageView? = null

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

//            gridView = view?.findViewById(R.id.gridViewHome)!!
//
//            productoHomeAdapter = ProductoHomeAdapter(nombresProductos, imagenesProductos, this.context)
//            gridView.setAdapter(productoHomeAdapter);

//            imageView8.setOnClickListener {
//                val intent = Intent(activity, DetalleProducto::class.java)
//                startActivity(intent)
//            }
            productos = getProductsLinks()!!
            productoHomeAdapter = ProductoHomeAdapter(this.requireContext(), productos, R.layout.item_producto)
            recicleViewHome.setAdapter(productoHomeAdapter)
            //Glide.with(this).load(getProductsLinks()).placeholder(R.drawable.loadproduct).into(imageViewItemProduct)


            imageViewCarrito.setOnClickListener {
                val intent = Intent(activity, PantallaCarrito::class.java)
                startActivity(intent)
            }

        })

        return root
    }

    fun getProductsLinks(): Array<String>? {
        return arrayOf(
            "https://static.pexels.com/photos/86462/red-kite-bird-of-prey-milan-raptor-86462.jpeg",
            "https://static.pexels.com/photos/67508/pexels-photo-67508.jpeg",
            "https://static.pexels.com/photos/55814/leo-animal-savannah-lioness-55814.jpeg",
            "https://static.pexels.com/photos/40745/red-squirrel-rodent-nature-wildlife-40745.jpeg",
            "https://static.pexels.com/photos/33392/portrait-bird-nature-wild.jpg",
            "https://static.pexels.com/photos/62640/pexels-photo-62640.jpeg",
            "https://static.pexels.com/photos/38438/rattlesnake-toxic-snake-dangerous-38438.jpeg",
            "https://static.pexels.com/photos/33149/lemur-ring-tailed-lemur-primate-mammal.jpg",
            "https://static.pexels.com/photos/1137/wood-animal-dog-pet.jpg",
            "https://static.pexels.com/photos/40731/ladybug-drop-of-water-rain-leaf-40731.jpeg",
            "https://static.pexels.com/photos/40860/spider-macro-insect-arachnid-40860.jpeg",
            "https://static.pexels.com/photos/63282/crab-yellow-ocypode-quadrata-atlantic-ghost-crab-63282.jpeg",
            "https://static.pexels.com/photos/45246/green-tree-python-python-tree-python-green-45246.jpeg",
            "https://static.pexels.com/photos/39245/zebra-stripes-black-and-white-zoo-39245.jpeg",
            "https://static.pexels.com/photos/92000/pexels-photo-92000.jpeg",
            "https://static.pexels.com/photos/121445/pexels-photo-121445.jpeg",
            "https://static.pexels.com/photos/112603/pexels-photo-112603.jpeg",
            "https://static.pexels.com/photos/52961/antelope-nature-flowers-meadow-52961.jpeg",
            "https://static.pexels.com/photos/36450/flamingo-bird-pink-nature.jpg",
            "https://static.pexels.com/photos/20861/pexels-photo.jpg",
            "https://static.pexels.com/photos/54108/peacock-bird-spring-animal-54108.jpeg",
            "https://static.pexels.com/photos/24208/pexels-photo-24208.jpg"
        )
    }


}