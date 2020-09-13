package com.luceromichael.vengappveci

import com.google.firebase.auth.FirebaseUser

data class UserModelClass(
    var name: String,
    var phoneNumber: String,
    var emailAddress: String,
    var user: FirebaseUser?
)