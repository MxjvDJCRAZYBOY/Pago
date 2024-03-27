package com.example.pago

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import android.util.Patterns

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerCardType: Spinner
    private lateinit var editTextName: EditText
    private lateinit var editTextCardNumber: EditText
    private lateinit var editTextCvv: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var textInputLayoutCardNumber: TextInputLayout
    private lateinit var textInputLayoutEmail: TextInputLayout
    // Expresión regular para validar el formato del número de tarjeta
    private val cardNumberRegex = Regex("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerCardType = findViewById(R.id.spinnerCardType)
        editTextName = findViewById(R.id.editTextName)
        editTextCardNumber = findViewById(R.id.editTextCardNumber)
        editTextCvv = findViewById(R.id.editTextCvv)
        editTextEmail = findViewById(R.id.editTextEmail)
        textInputLayoutCardNumber = findViewById(R.id.textInputLayoutCardNumber)
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail)

        val cardTypes = arrayOf("Selecciona un tipo de tarjeta", "Visa", "MasterCard", "American Express")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cardTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCardType.adapter = adapter

        spinnerCardType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position > 0) {
                    editTextName.visibility = View.VISIBLE
                    textInputLayoutCardNumber.visibility = View.VISIBLE
                    editTextCvv.visibility = View.VISIBLE
                    val cvvLength = if (parent?.getItemAtPosition(position).toString() == "American Express") 4 else 3
                    editTextCvv.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(cvvLength))
                } else {
                    editTextName.visibility = View.GONE
                    textInputLayoutCardNumber.visibility = View.GONE
                    editTextCvv.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                editTextName.visibility = View.GONE
                textInputLayoutCardNumber.visibility = View.GONE
                editTextCvv.visibility = View.GONE
            }
        }

        editTextCardNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.length % 5 == 0 && s.length <= 19 && s.last() != '-') {
                    s.insert(s.length - 1, "-")
                }
                textInputLayoutCardNumber.error = if (!cardNumberRegex.matches(s.toString())) "Formato de tarjeta incorrecto" else null
            }
        })


        // Validación para el formato del correo electrónico
        editTextEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!isValidEmail(s.toString())) {
                    textInputLayoutEmail.error = "Formato de correo electrónico incorrecto"
                } else {
                    textInputLayoutEmail.error = null
                }
            }
        })
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
