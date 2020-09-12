package com.luceromichael.vengaapp_veci

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    //private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        LeerDatosDeArchivoPreferenciasEncriptado()

        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        TextEmail.setText(sharedPref.getString(LOGIN_KEY, ""))
        TextPassword.setText(sharedPref.getString(PASSWORD_KEY, ""))
        EscribirDatosEnArchivoPreferenciasEncriptado()

        //Validar password y email
        buttonLogin.setOnClickListener {
            val email = TextEmail.text.toString()
            val password = TextPassword.text.toString()
            //AutenticarUsuario2(TextEmail.text.toString(),TextPassword.text.toString())

            if (checkBox.isChecked) {
                val editor = sharedPref.edit()
                editor.putString(LOGIN_KEY, email)
                editor.putString(PASSWORD_KEY, password)
                editor.commit()
            } else {
                val editor = sharedPref.edit()
                editor.putString(LOGIN_KEY, "")
                editor.putString(PASSWORD_KEY, "")
                editor.commit()
            }
            if(email.equals("gabriela.talavera@epn.edu.ec") and password.equals("12345678")){
                var intent = Intent(this, PantallaPrincipalActivity::class.java)
                intent.putExtra(LOGIN_KEY,email)
                startActivity(intent)
                Toast.makeText(this, "Validacion exitosa",Toast.LENGTH_LONG).show()
                finish()
            }
        }

        TextEmail.validate("Ingrese un correo electronico valido") { s -> s.isValidEmail() }
        TextPassword.validate("La contraseña debe tener minimo 8 caracteres") { s -> s.length >= 8 }

        buttonRegistrar.setOnClickListener {
            var intent1 = Intent(this,RegistroActivity::class.java)
            startActivity(intent1)
        }
    }

    //Validacion de password y contrasena
    fun EditText.validate(message: String, validator: (String) -> Boolean) {
        this.afterTextChanged {
            this.error = if (validator(it)) null else message
        }
    }
    fun String.isValidEmail(): Boolean =
        this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                afterTextChanged.invoke(s.toString())
            }
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    fun EscribirDatosEnArchivoPreferenciasEncriptado() {
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val sharedPreferences = EncryptedSharedPreferences.create(
            SECRET_FILENAME,//filename
            masterKeyAlias,
            this,//context
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        if (checkBox.isChecked) {
            val editor = sharedPreferences.edit()
            editor.putString(LOGIN_KEY, TextEmail.text.toString())
            editor.putString(PASSWORD_KEY, TextPassword.text.toString())
            editor.apply()
        } else {
            val editor = sharedPreferences.edit()
            editor.putString(LOGIN_KEY, "")
            editor.putString(PASSWORD_KEY, "")
            editor.commit()
        }
    }

    fun LeerDatosDeArchivoPreferenciasEncriptado() {
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val sharedPreferences = EncryptedSharedPreferences.create(
            SECRET_FILENAME,//filename
            masterKeyAlias,
            this,//context
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        TextEmail.setText(sharedPreferences.getString(LOGIN_KEY, ""))
        TextPassword.setText(sharedPreferences.getString(PASSWORD_KEY, ""))
    }

    fun InicializarArchivoPref(){
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        sharedPreferences = EncryptedSharedPreferences.create(
            SECRET_FILENAME,//filename
            masterKeyAlias,
            this,//context
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

        }