package com.example.pago

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerCardType: Spinner
    private lateinit var editTextName: EditText
    private lateinit var editTextCardNumber: EditText
    private lateinit var editTextCvv: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextExpirationMonth: EditText
    private lateinit var editTextExpirationYear: EditText
    private lateinit var dividerLine: TextView
    private lateinit var textInputLayoutCardNumber: TextInputLayout
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var imageViewVisa: ImageView
    private lateinit var imageViewMastercard: ImageView
    private lateinit var imageViewAmericanExpress: ImageView
    private lateinit var imageViewCardNormal: ImageView

    // Expresión regular para validar el formato del número de tarjeta
    private val cardNumberRegex = Regex("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$")
    private val productPrices = listOf(
        3900.0, // Precio del producto 1
        2198.0, // Precio del producto 2
        439.0,  // Precio del producto 3
        5344.0, // Precio del producto 4
        747.0,  // Precio del producto 5
        1975.0, // Precio del producto 6
        299.0,  // Precio del producto 7
        239.0,  // Precio del producto 8
        80.0,   // Precio del producto 9
        496.0   // Precio del producto 10
    )
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val randomNumbers = List(3) { Random.nextInt(1, 11) }
        spinnerCardType = findViewById(R.id.spinnerCardType)
        editTextName = findViewById(R.id.editTextName)
        editTextCardNumber = findViewById(R.id.editTextCardNumber)
        editTextCvv = findViewById(R.id.editTextCvv)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextExpirationMonth = findViewById(R.id.editTextExpirationMonth)
        editTextExpirationYear = findViewById(R.id.editTextExpirationYear)
        dividerLine = findViewById(R.id.dividerLine)
        textInputLayoutCardNumber = findViewById(R.id.textInputLayoutCardNumber)
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail)
        imageViewVisa = findViewById(R.id.imageViewVisa)
        imageViewMastercard = findViewById(R.id.imageViewMastercard)
        imageViewAmericanExpress = findViewById(R.id.imageViewAmericanExpress)
        imageViewCardNormal = findViewById(R.id.imageViewCardNormal)
        //TextInputLayoutViewTotalPrice = findViewById(R.id.textInputLayoutEmail)

        val cardTypes = arrayOf("Selecciona un tipo de tarjeta", "Visa", "MasterCard", "American Express")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cardTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCardType.adapter = adapter

        val buttonPay: Button = findViewById(R.id.buttonPay)
        buttonPay.setOnClickListener {
            // Obtener los valores de los campos
            val cardTypePosition = spinnerCardType.selectedItemPosition
            val name = editTextName.text.toString()
            val cardNumber = editTextCardNumber.text.toString().replace("-", "")
            val cvv = editTextCvv.text.toString()
            val expirationMonth = editTextExpirationMonth.text.toString()
            val expirationYear = editTextExpirationYear.text.toString()
            val email = editTextEmail.text.toString()

            // Verificar las condiciones para ir a una pantalla
            if (cardTypePosition > 0 && name.isNotBlank() && cardNumber.length == 16 &&
                (cvv.length == 3 || cvv.length == 4) && isValidExpirationDate(expirationMonth, expirationYear) &&
                email.isNotBlank() && isValidEmail(email)) {
                // Pantalla random transaccion realizada o falta de fondos
                startActivity(Intent(this, MainActivity2::class.java).apply {
                    putExtra("RESULT", "CORRECTO")
                })
            } else {
                // Pantalla falta de datos
                startActivity(Intent(this, MainActivity2::class.java).apply {
                    putExtra("RESULT", "INCORRECTO")
                })
            }
        }

        val totalPriceTextView: TextView = findViewById(R.id.totalPriceTextView)
        val totalPrice = calculateTotalPrice(randomNumbers)
        totalPriceTextView.text = "Total: $$totalPrice"

        val buttonCart: Button = findViewById(R.id.buttonCart)
        buttonCart.setOnClickListener { openCartActivity(randomNumbers) }
        //Tipo de tarjeta y limpiar valores en caso de cambio
        spinnerCardType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position > 0) {
                    editTextName.visibility = View.VISIBLE
                    textInputLayoutCardNumber.visibility = View.VISIBLE
                    editTextCvv.visibility = View.VISIBLE
                    editTextExpirationMonth.visibility = View.VISIBLE
                    editTextExpirationYear.visibility = View.VISIBLE
                    dividerLine.visibility = View.VISIBLE
                    // Limpiar los campos de nombre, número de tarjeta, CVV y fecha de expiración
                    editTextName.text.clear()
                    //editTextCardNumber.text.clear()
                    editTextCvv.text.clear()
                    editTextExpirationMonth.text.clear()
                    editTextExpirationYear.text.clear()
                    // Mostrar la imagen correspondiente según el tipo de tarjeta seleccionado
                    when (position) {
                        1 -> { // Visa
                            imageViewVisa.visibility = View.VISIBLE
                            imageViewMastercard.visibility = View.INVISIBLE
                            imageViewAmericanExpress.visibility = View.INVISIBLE
                            imageViewCardNormal.visibility = View.INVISIBLE
                        }
                        2 -> { // MasterCard
                            imageViewVisa.visibility = View.INVISIBLE
                            imageViewMastercard.visibility = View.VISIBLE
                            imageViewAmericanExpress.visibility = View.INVISIBLE
                            imageViewCardNormal.visibility = View.INVISIBLE
                        }
                        3 -> { // American Express
                            imageViewVisa.visibility = View.INVISIBLE
                            imageViewMastercard.visibility = View.INVISIBLE
                            imageViewAmericanExpress.visibility = View.VISIBLE
                            imageViewCardNormal.visibility = View.INVISIBLE
                        }
                    }
                } else {
                    // Si no se selecciona ninguna opción en el Spinner, mostrar la imagen de tarjeta normal
                    imageViewVisa.visibility = View.INVISIBLE
                    imageViewMastercard.visibility = View.INVISIBLE
                    imageViewAmericanExpress.visibility = View.INVISIBLE
                    imageViewCardNormal.visibility = View.VISIBLE

                    editTextName.visibility = View.GONE
                    textInputLayoutCardNumber.visibility = View.GONE
                    editTextCvv.visibility = View.GONE
                    editTextExpirationMonth.visibility = View.GONE
                    editTextExpirationYear.visibility = View.GONE
                    dividerLine.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                editTextName.visibility = View.GONE
                textInputLayoutCardNumber.visibility = View.GONE
                editTextCvv.visibility = View.GONE
                editTextExpirationMonth.visibility = View.GONE
                editTextExpirationYear.visibility = View.GONE
            }
        }

        val editTextExpirationMonth: EditText = findViewById(R.id.editTextExpirationMonth)
        val editTextExpirationYear: EditText = findViewById(R.id.editTextExpirationYear)

//filtro para el campo de mes de vencimiento (1-12)
        editTextExpirationMonth.filters = arrayOf(InputFilter { source, _, _, dest, dstart, dend ->
            val input = dest.subSequence(0, dstart).toString() + source + dest.subSequence(dend, dest.length)
            if (input.toIntOrNull() in 0..12 || input == "") {
                null
            } else {
                ""
            }
        })

// filtro para el campo de año de vencimiento (0-50)
        editTextExpirationYear.filters = arrayOf(InputFilter { source, _, _, dest, dstart, dend ->
            val input = dest.subSequence(0, dstart).toString() + source + dest.subSequence(dend, dest.length)
            if (input.toIntOrNull() in 0..50 || input == "") {
                null
            } else {
                ""
            }
        })

        //Condicion para que el cvv tenga 3 o 4 digitos
        editTextCvv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val cvvLength = if (spinnerCardType.selectedItemPosition == 3) 4 else 3
                editTextCvv.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(cvvLength))
                if (s != null && s.length > cvvLength) {
                    s.delete(cvvLength, s.length)
                }
            }
        })

        //insertar de forma automatica guion al numero de tarjeta
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
    //Productos random cada que se abre la aplicacion
    fun openCartActivity(randomNumbers: List<Int>) {
        val intent = Intent(this, MainActivity3::class.java).apply {
            putIntegerArrayListExtra("RANDOM_NUMBERS", ArrayList(randomNumbers))
        }
        startActivity(intent)
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    //verificar si la fecha de expiración es válida
    private fun isValidExpirationDate(month: String, year: String): Boolean {
        // Obtener el año y el mes actual
        val currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1

        // Convertir los valores de mes y año a números enteros
        val expMonth = month.toIntOrNull() ?: return false
        val expYear = year.toIntOrNull() ?: return false

        // Verificar si la fecha de expiración está en el futuro
        return expYear > currentYear || (expYear == currentYear && expMonth >= currentMonth)
    }

    private fun calculateTotalPrice(randomNumbers: List<Int>): Double {
        return randomNumbers.sumOf { productPrices[it - 1] }
    }

}