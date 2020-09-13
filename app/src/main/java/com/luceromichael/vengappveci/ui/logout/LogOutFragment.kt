package com.luceromichael.vengappveci.ui.logout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.luceromichael.vengappveci.*

class LogOutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_logout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loadingProgressBar = view.findViewById<ProgressBar>(R.id.loading3)
        loadingProgressBar.visibility = View.VISIBLE

        FirebaseAuth.getInstance().signOut()
        currentUSer= UserModelClass("","","", null)
        state = 0
        Toast.makeText(contexto, "Cerrando sesion "+ currentUSer.name, Toast.LENGTH_LONG).show()
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)

    }





}