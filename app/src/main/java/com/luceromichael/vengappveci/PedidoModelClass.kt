package com.luceromichael.vengappveci

data class PedidoModelClass(
    var fecha: String,
    var productList: ArrayList<DetallePedidoModelClass>,
    var total: Float
)