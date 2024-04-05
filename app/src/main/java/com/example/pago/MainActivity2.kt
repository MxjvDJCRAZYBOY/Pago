package com.example.pago

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        val constraintLayout = findViewById<ConstraintLayout>(R.id.main)
        val resultado = intent.getStringExtra("RESULT")

        // Verificar el resultado y mostrar la imagen correspondiente
        if (resultado == "CORRECTO") {
            // Generar un número aleatorio entre 0 y 1
            val random = (0..1).random()

            // Si el número aleatorio es 0, mostrar la imagen "correcto", de lo contrario, mostrar la imagen "fondo"
            if (random == 0) {
                constraintLayout.setBackgroundResource(R.drawable.correcto)
            } else {
                constraintLayout.setBackgroundResource(R.drawable.incorrecto)
            }
        } else if (resultado == "INCORRECTO") {
            constraintLayout.setBackgroundResource(R.drawable.dato)
        }
    }

    fun onBackPressed(view: View) {
        // Define el comportamiento al presionar el botón de "Atrás"
        // Por ejemplo, puedes cerrar la actividad actual y volver a la actividad anterior
        super.onBackPressed() // Esta línea llama al comportamiento predeterminado (cerrar la actividad)
    }
}