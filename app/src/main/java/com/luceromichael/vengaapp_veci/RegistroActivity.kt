package com.luceromichael.vengaapp_veci

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registro.*


class RegistroActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        actionBar?.title = getString(R.string.addNewUser)

        buttonRegistrar.setOnClickListener {
            if (!ValidarDatos())
                return@setOnClickListener
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val confirmPassword = editTextPasswordConfirm.text.toString()
            if(password != confirmPassword){
                Toast.makeText(this,getString(R.string.password_no_coincide),Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            SignUpNewUser(email, password)
        }

               buttonRegistrarALogin.setOnClickListener {

            var intent1 = Intent(this,LoginActivity::class.java)
            startActivity(intent1)
        }
    }

    fun ValidarDatos(): Boolean {
        fun CharSequence?.isValidEmail() =
            !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
        if (editTextEmail.text.isNullOrEmpty()) {
            editTextEmail.setError(getString(R.string.editTextTextEmailAddress_hint))
            editTextEmail.requestFocus()
            return false
        }
        if (!editTextEmail.text.isValidEmail()) {
            editTextEmail.setError(getString(R.string.email_NoValido))
            editTextEmail.requestFocus()
            return false
        }
        if (editTextPassword.text.isNullOrEmpty()) {
            editTextPassword.setError(getString(R.string.editTextPassword_hint))
            editTextPassword.requestFocus()
            return false
        }
        if (editTextPassword.text.length < MIN_PASSWORD_LENGTH) {
            editTextPassword.setError(getString(R.string.password_longitudNoValida))
            editTextPassword.requestFocus()
            return false
        }
        return true
    }

    private fun SignUpNewUser(email:String, password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "New user saved.",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext, task.exception!!.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

}
