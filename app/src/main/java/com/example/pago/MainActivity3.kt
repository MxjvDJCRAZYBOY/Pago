package com.example.pago

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity3 : AppCompatActivity() {
    private lateinit var imageView1: ImageView
    private lateinit var imageView2: ImageView
    private lateinit var imageView3: ImageView
    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private lateinit var textView3: TextView
    private lateinit var products: List<Product>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        products = listOf(
            Product(getString(R.string.product_name_1), R.drawable.product1, 900.0, 1),
            Product(getString(R.string.product_name_2), R.drawable.product2, 2198.0, 2),
            Product(getString(R.string.product_name_3), R.drawable.product3, 439.0, 3),
            Product(getString(R.string.product_name_4), R.drawable.product4, 534.0, 4),
            Product(getString(R.string.product_name_5), R.drawable.product5, 747.0, 5),
            Product(getString(R.string.product_name_6), R.drawable.product6, 1900.0, 6),
            Product(getString(R.string.product_name_7), R.drawable.product7, 299.0, 7),
            Product(getString(R.string.product_name_8), R.drawable.product8, 239.0, 8),
            Product(getString(R.string.product_name_9), R.drawable.product9, 80.0, 9),
            Product(getString(R.string.product_name_10), R.drawable.product10, 496.0, 10)

        )
        // Obtener los números aleatorios del Intent
        val randomNumbers = intent.getIntegerArrayListExtra("RANDOM_NUMBERS")

        // Filtrar los productos según los números aleatorios
        val filteredProducts = products.filter { randomNumbers?.contains(it.number) == true }

        // Mostrar los productos filtrados en las vistas correspondientes
        showProducts(filteredProducts)
    }

    @SuppressLint("SetTextI18n")
    private fun showProducts(products: List<Product>) {
        imageView1 = findViewById(R.id.imageView1)
        imageView2 = findViewById(R.id.imageView2)
        imageView3 = findViewById(R.id.imageView3)
        textView1 = findViewById(R.id.textView1)
        textView2 = findViewById(R.id.textView2)
        textView3 = findViewById(R.id.textView3)

        val imageViews = listOf(imageView1, imageView2, imageView3)
        val textViews = listOf(textView1, textView2, textView3)

        products.forEachIndexed { index, product ->
            imageViews[index].setImageResource(product.imageResource)
            textViews[index].text = "${product.name}\n$${product.price}"
        }
    }

    fun onBackPressed(view: View) {
        // Define el comportamiento al presionar el botón de "Atrás"
        // Por ejemplo, puedes cerrar la actividad actual y volver a la actividad anterior
        super.onBackPressed() // Esta línea llama al comportamiento predeterminado (cerrar la actividad)
    }
    data class Product(val name: String, val imageResource: Int, val price: Double, val number: Int)
}

