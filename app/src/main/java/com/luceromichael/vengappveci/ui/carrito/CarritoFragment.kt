package com.luceromichael.vengappveci.ui.carrito

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.luceromichael.vengappveci.*


class CarritoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_carrito, container, false)
    }

    private val TAG = "LoginFragment"
    lateinit var loadingProgressBar:ProgressBar
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

        val usernameEditText = view.findViewById<EditText>(R.id.editTextCorreo)
        val passwordEditText = view.findViewById<EditText>(R.id.editTextContrasena)
        val loginButton = view.findViewById<Button>(R.id.buttonIniciarSesion)
        loadingProgressBar = view.findViewById<ProgressBar>(R.id.loading)

        usernameEditText.validate("Correo electronico invalido") { s -> s.isValidEmail() }
        passwordEditText.validate("La contraseña debe tener mas de 8 caracteres") { s -> s.length >= 8}

        loginButton.setOnClickListener {
            login(
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }
    }


    fun EditText.validate(message: String, validator: (String) -> Boolean) {
        this.afterTextChanged {
            this.error = if (validator(it)) null else message
        }
    }

    fun String.isValidEmail(): Boolean
            = this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                afterTextChanged.invoke(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })
    }

    fun login(username: String, password: String) {
        mAuth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = mAuth.currentUser
                    if(user != null){
                        //start MAIN activity
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                        state = 1
                        Toast.makeText(contexto, "Inicio de sesion exitoso.",Toast.LENGTH_LONG).show()
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(contexto, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    loadingProgressBar.visibility = View.INVISIBLE

                }
            }
    }

    fun updateUI(user: FirebaseUser?) {
        if(user != null){
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
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.exception)
                    }
                }
        }
    }
}