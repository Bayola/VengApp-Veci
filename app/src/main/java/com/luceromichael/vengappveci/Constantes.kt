package com.luceromichael.vengappveci

import android.content.Context
import com.google.firebase.auth.FirebaseAuth

const val LOGIN_KEY = "LOGIN_KEY"
const val REGISTER_KEY = "REGISTER_KEY"
const val DETALLE_PED_KEY = "DETALLE_PED_KEY"
lateinit var contexto: Context
lateinit var mAuth: FirebaseAuth
var currentUSer: UserModelClass = UserModelClass("", "", "", null)
lateinit var activity: MainActivity
var state = 0