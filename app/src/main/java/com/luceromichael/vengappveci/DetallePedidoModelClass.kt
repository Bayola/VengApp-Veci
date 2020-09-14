package com.luceromichael.vengappveci

data class DetallePedidoModelClass(
    var producto: ProductoModelClass,
    var cant: Int,
    var subTotal: Float
){
//    fun getNombreProducto(): String? {
//        return this.producto.name
//    }
}