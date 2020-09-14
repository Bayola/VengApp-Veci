package com.luceromichael.vengappveci.ui.registro

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


class RegistroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registro, container, false)
    }

    private val TAG = "LoginFragment"
    lateinit var loadingProgressBar:ProgressBar
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

        val nameEditText = view.findViewById<EditText>(R.id.editTextNombre)
        val emailEditText = view.findViewById<EditText>(R.id.editTextCorreo)
        val phoneEditText = view.findViewById<EditText>(R.id.editTextTelefono)
        val pass1EditText = view.findViewById<EditText>(R.id.editTextpass1)
        val pass2EditText = view.findViewById<EditText>(R.id.editTextPass2)
        val registrarButton = view.findViewById<Button>(R.id.buttonRegistrarse)
        loadingProgressBar = view.findViewById<ProgressBar>(R.id.loading2)

        nameEditText.validate("El nombre no puede quedar en blanco") { s -> s.length > 0 }
        emailEditText.validate("Correo electronico invalido") { s -> s.isValidEmail() }
        pass1EditText.validate("La contraseña debe tener mas de 8 caracteres") { s -> s.length >= 8 }
        pass2EditText.validate("La contraseña debe tener mas de 8 caracteres") { s -> s.length >= 8 }
        pass2EditText.validate("Las contraseñas no son iguales") { s -> s.isEqualPass(pass1EditText.text.toString()) }
        phoneEditText.validate("El numero debe tener 8 caracteres o mas") { s -> s.length >= 8 }

        registrarButton.setOnClickListener(
            View.OnClickListener {
                loadingProgressBar.visibility = View.VISIBLE
                activity?.let { it1 ->
                    mAuth.createUserWithEmailAndPassword(
                        emailEditText.text.toString(),
                        pass1EditText.text.toString()
                    )
                        .addOnCompleteListener(it1) { task ->
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = mAuth.currentUser
                            updateUI(user, nameEditText.text.toString(), emailEditText.text.toString(), phoneEditText.text.toString())
                        }
                }
            })

        val buttonCancelar = view.findViewById<Button>(R.id.buttonCancelar)
        buttonCancelar.setOnClickListener{
            this.activity?.onBackPressed()
        }
    }

    fun updateUI(user: FirebaseUser?, name:String, email:String, phone:String) {
        currentUSer = UserModelClass(name, phone, email, user)
        if(user != null){

            val user = hashMapOf(
                "name" to currentUSer.name,
                "emailAddress" to currentUSer.emailAddress,
                "phoneNumber" to currentUSer.phoneNumber,
                "userUID" to currentUSer.user!!.uid
            )

            db.collection("/users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                    Toast.makeText(context, "Registro exitoso. Bienvenido "+name,Toast.LENGTH_LONG).show()
                    state = 1
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                    loadingProgressBar.visibility = View.INVISIBLE
                    currentUSer = UserModelClass("", "", "", null)
                    Toast.makeText(context, "Error al registrarse.",Toast.LENGTH_LONG).show()
                }
        }
    }

    fun EditText.validate(message: String, validator: (String) -> Boolean) {
        this.afterTextChanged {
            this.error = if (validator(it)) null else message
        }
    }

    fun String.isValidEmail(): Boolean
            = this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun String.isEqualPass(passVal:String): Boolean
            = this.isNotEmpty() && this.equals(passVal)

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                afterTextChanged.invoke(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })
    }
}