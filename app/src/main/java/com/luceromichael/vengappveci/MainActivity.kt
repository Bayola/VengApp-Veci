package com.luceromichael.vengappveci

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.nav_header_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val TAG = "MainActivity"
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        contexto = this.applicationContext
        activity = this

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_login, R.id.nav_registro, R.id.nav_carrito, R.id.nav_hpedidos, R.id.nav_logout), drawerLayout)
        login(false)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun login(boolean: Boolean){
        if(boolean){
            showItem(navView, R.id.nav_carrito)
            showItem(navView, R.id.nav_hpedidos)
            showItem(navView, R.id.nav_logout)
            hideItem(navView, R.id.nav_login)
            hideItem(navView, R.id.nav_registro)
        } else {
            showItem(navView, R.id.nav_login)
            showItem(navView, R.id.nav_registro)
            hideItem(navView, R.id.nav_carrito)
            hideItem(navView, R.id.nav_hpedidos)
            hideItem(navView, R.id.nav_logout)
        }
    }

    private fun hideItem(navView:NavigationView, item:Int) {
        val nav_Menu: Menu = navView.getMenu()
        nav_Menu.findItem(item).setVisible(false)
    }

    private fun showItem(navView:NavigationView, item:Int) {
        val nav_Menu: Menu = navView.getMenu()
        nav_Menu.findItem(item).setVisible(true)
    }

    override fun onStart() {
        super.onStart()
        mAuth = FirebaseAuth.getInstance()
        var usr: FirebaseUser? = mAuth.currentUser
        updateUI(usr)
    }

    fun updateUI(user: FirebaseUser?) {
        if(user != null){
            state = 1
            Toast.makeText(this, "Inicio de sesion exitoso.", Toast.LENGTH_LONG).show()
            db.collection("/users")
                .whereEqualTo("userUID", user.uid)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            Log.d(
                                TAG,
                                document.id + " => " + document.data
                            )
                            currentUSer = UserModelClass(
                                document.data.get("name").toString(),
                                document.data.get("phoneNumber").toString(),
                                document.data.get("emailAddress").toString(),
                                user)

                            login(true)
                            val myAwesomeTextView =
                                findViewById<View>(R.id.textViewNavBar) as TextView
                            myAwesomeTextView.text = currentUSer.name
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.exception)
                    }
                }
        }
    }
}