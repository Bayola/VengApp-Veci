package com.luceromichael.vengappveci

data class PedidoModelClass(
    var id:String,
    var fecha: String,
    var productList: ArrayList<DetallePedidoModelClass>?,
    var total: Float
)