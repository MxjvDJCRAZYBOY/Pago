package com.example.pago

import android.annotation.SuppressLint
import android.os.Bundle
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

    private val products = listOf(
        Product("Controlador Launchpad X Novation 64 Pads Y Msi", R.drawable.product1, 3900.0, 1),
        Product("Galaxy Watch 4 Sm-r860", R.drawable.product2, 2198.0, 2),
        Product("Lentes De Sol Hawkers", R.drawable.product3, 439.0, 3),
        Product("Ventilador Industrial De Pared", R.drawable.product4, 5344.0, 4),
        Product("Funlab Firefly Control Pro", R.drawable.product5, 747.0, 5),
        Product("Xiaomi Poco C65", R.drawable.product6, 1975.0, 6),
        Product("Cargador Xiaomi 33w", R.drawable.product7, 299.0, 7),
        Product("Lego Ferrari", R.drawable.product8, 239.0, 8),
        Product("Rechargeable Ultra-thin Wireless Mouse", R.drawable.product9, 80.0, 9),
        Product("Juego De Mesa Basta", R.drawable.product10, 496.0, 10)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

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

    data class Product(val name: String, val imageResource: Int, val price: Double, val number: Int)
}
