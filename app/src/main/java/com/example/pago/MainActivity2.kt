package com.example.pago

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity2 : AppCompatActivity() {

    private lateinit var textViewCorrecto: TextView
    private lateinit var textViewIncorrecto: TextView
    private lateinit var textViewIncorrecto2: TextView
    private lateinit var textViewDato: TextView
    private lateinit var textViewDato2: TextView
    private lateinit var textViewInicio: TextView
    private lateinit var textViewPerfil: TextView
    private lateinit var textViewBienvenido: TextView
    private lateinit var textViewInicio2: TextView
    private lateinit var textViewPerfil2: TextView
    private lateinit var textViewBienvenido2: TextView

    @SuppressLint("MissingInflatedId", "StringFormatMatches")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        textViewCorrecto = findViewById(R.id.Correcto)
        textViewIncorrecto = findViewById(R.id.Incorrecto)
        textViewIncorrecto2 = findViewById(R.id.Incorrecto2)
        textViewDato = findViewById(R.id.Dato)
        textViewDato2 = findViewById(R.id.Dato2)
        textViewBienvenido = findViewById(R.id.Bienvenido)
        textViewInicio = findViewById(R.id.Inicio)
        textViewPerfil = findViewById(R.id.Perfil)
        textViewBienvenido2 = findViewById(R.id.Bienvenido2)
        textViewInicio2 = findViewById(R.id.Inicio2)
        textViewPerfil2 = findViewById(R.id.Perfil2)

        val totalTextView = findViewById<TextView>(R.id.totalTextView)

        val total = intent.getDoubleExtra("total", 0.0)
        val numeroTarjeta = intent.getStringExtra("numeroTarjeta")
        val nombreTarjetaHabiente = intent.getStringExtra("nombreTarjetaHabiente")

        val constraintLayout = findViewById<ConstraintLayout>(R.id.main)
        val resultado = intent.getStringExtra("RESULT")

        val mensaje = getString(R.string.transaction_message, numeroTarjeta, total, nombreTarjetaHabiente)

        // Verificar el resultado y mostrar la imagen correspondiente
        if (resultado == "CORRECTO") {
            // Generar un número aleatorio entre 0 y 99 para tener un 75% de que se cumpla o no
            val random = (0..99).random()

            // Si el número aleatorio es 0, mostrar la imagen "correcto", de lo contrario, mostrar la imagen "fondo"
            if (random in 0 .. 74) {
                constraintLayout.setBackgroundResource(R.drawable.correcto)
                textViewCorrecto.visibility = View.VISIBLE
                textViewIncorrecto.visibility = View.GONE
                textViewIncorrecto2.visibility = View.GONE
                textViewDato.visibility = View.GONE
                textViewDato2.visibility = View.GONE
                textViewPerfil.visibility = View.VISIBLE
                textViewInicio.visibility = View.VISIBLE
                textViewBienvenido.visibility = View.VISIBLE
                textViewPerfil2.visibility = View.GONE
                textViewInicio2.visibility = View.GONE
                textViewBienvenido2.visibility = View.GONE
                totalTextView.text = mensaje
            } else {
                constraintLayout.setBackgroundResource(R.drawable.incorrecto)
                textViewCorrecto.visibility = View.GONE
                textViewIncorrecto.visibility = View.VISIBLE
                textViewIncorrecto2.visibility = View.VISIBLE
                textViewDato.visibility = View.GONE
                textViewDato2.visibility = View.GONE
                textViewPerfil.visibility = View.VISIBLE
                textViewInicio.visibility = View.VISIBLE
                textViewBienvenido.visibility = View.VISIBLE
                textViewPerfil2.visibility = View.GONE
                textViewInicio2.visibility = View.GONE
                textViewBienvenido2.visibility = View.GONE
            }
        } else if (resultado == "INCORRECTO") {
            constraintLayout.setBackgroundResource(R.drawable.dato)
            textViewCorrecto.visibility = View.GONE
            textViewIncorrecto.visibility = View.GONE
            textViewIncorrecto2.visibility = View.GONE
            textViewDato.visibility = View.VISIBLE
            textViewDato2.visibility = View.VISIBLE
            textViewPerfil2.visibility = View.VISIBLE
            textViewInicio2.visibility = View.VISIBLE
            textViewBienvenido2.visibility = View.VISIBLE
            textViewPerfil.visibility = View.GONE
            textViewInicio.visibility = View.GONE
            textViewBienvenido.visibility = View.GONE
        }
    }

    fun onBackPressed(view: View) {
        super.onBackPressed() // Esta línea llama al comportamiento predeterminado (cerrar la actividad)
    }
}