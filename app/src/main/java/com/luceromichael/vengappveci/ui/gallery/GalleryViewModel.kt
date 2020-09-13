package com.luceromichael.vengappveci.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luceromichael.vengappveci.PantallaRegistro
import com.luceromichael.vengappveci.REGISTER_KEY
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryViewModel : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text


}