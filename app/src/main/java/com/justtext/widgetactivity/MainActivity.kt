package com.justtext.widgetactivity

import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    // Declarar vistas
    private lateinit var tilNombre: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var tilConfirmPassword: TextInputLayout
    private lateinit var etNombre: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText
    private lateinit var btnRegistrar: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar vistas
        initViews()

        // Configurar listeners
        setupListeners()
    }

    private fun initViews() {
        tilNombre = findViewById(R.id.tilNombre)
        tilEmail = findViewById(R.id.tilEmail)
        tilPassword = findViewById(R.id.tilPassword)
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword)
        etNombre = findViewById(R.id.etNombre)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegistrar = findViewById(R.id.btnRegistrar)
    }

    private fun setupListeners() {
        // Listener para el botón de registro
        btnRegistrar.setOnClickListener {
            if (validarFormulario()) {
                registrarUsuario()
            }
        }

        // Limpiar errores al escribir
        etNombre.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) tilNombre.error = null
        }

        etEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) tilEmail.error = null
        }

        etPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) tilPassword.error = null
        }

        etConfirmPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) tilConfirmPassword.error = null
        }
    }

    private fun validarFormulario(): Boolean {
        var isValid = true

        // Validar nombre
        val nombre = etNombre.text.toString().trim()
        when {
            nombre.isEmpty() -> {
                tilNombre.error = getString(R.string.error_nombre_vacio)
                isValid = false
            }
            nombre.length < 3 -> {
                tilNombre.error = getString(R.string.error_nombre_corto)
                isValid = false
            }
            else -> {
                tilNombre.error = null
            }
        }

        // Validar email
        val email = etEmail.text.toString().trim()
        when {
            email.isEmpty() -> {
                tilEmail.error = getString(R.string.error_email_vacio)
                isValid = false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                tilEmail.error = getString(R.string.error_email_invalido)
                isValid = false
            }
            else -> {
                tilEmail.error = null
            }
        }

        // Validar contraseña
        val password = etPassword.text.toString()
        when {
            password.isEmpty() -> {
                tilPassword.error = getString(R.string.error_password_vacia)
                isValid = false
            }
            password.length < 6 -> {
                tilPassword.error = getString(R.string.error_password_corta)
                isValid = false
            }
            else -> {
                tilPassword.error = null
            }
        }

        // Validar confirmación de contraseña
        val confirmPassword = etConfirmPassword.text.toString()
        if (password != confirmPassword) {
            tilConfirmPassword.error = getString(R.string.error_password_no_coincide)
            isValid = false
        } else {
            tilConfirmPassword.error = null
        }

        return isValid
    }

    private fun registrarUsuario() {
        val nombre = etNombre.text.toString().trim()

        // Mostrar Snackbar de éxito
        Snackbar.make(
            findViewById(R.id.main),
            getString(R.string.registro_exitoso, nombre),
            Snackbar.LENGTH_LONG
        ).setAction("OK") {
            // Limpiar formulario después de registro exitoso
            limpiarFormulario()
        }.show()
    }

    private fun limpiarFormulario() {
        etNombre.text?.clear()
        etEmail.text?.clear()
        etPassword.text?.clear()
        etConfirmPassword.text?.clear()

        tilNombre.error = null
        tilEmail.error = null
        tilPassword.error = null
        tilConfirmPassword.error = null

        etNombre.requestFocus()
    }
}