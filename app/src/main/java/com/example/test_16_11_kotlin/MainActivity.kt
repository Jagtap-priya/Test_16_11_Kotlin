package com.example.test_16_11_kotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var btnConvert: Button
    private var amount: Double = 0.0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        btnConvert = findViewById(R.id.btnConvert)

        btnConvert.setOnClickListener {
            val amountText = editText.text.toString()
            if (amountText.isNotEmpty()) {
                amount = amountText.toDouble()
                showCustomDialog()
            } else {
                Toast.makeText(this, "Enter amount", Toast.LENGTH_LONG).show()
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showCustomDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.activity_customdialog, null)
        builder.setView(dialogView)

        val rgCaseOption: RadioGroup = dialogView.findViewById(R.id.rgCaseOption)
        val txtCustomDialog: TextView = dialogView.findViewById(R.id.txtCustomDialog)

        builder.setPositiveButton("Convert") { _, _ ->
            val selectedCurrency: String = when (rgCaseOption.checkedRadioButtonId) {
                R.id.rbCan -> "CAN"
                R.id.rbUsa -> "USA"
                R.id.rbUk -> "UK"
                R.id.rbChi -> "CHL"  // Corrected to match the conversion code
                else -> ""
            }
            if (selectedCurrency.isNotEmpty()) {
                val convertedAmount = convertCurrency(amount, selectedCurrency)
                txtCustomDialog.text = "Converted Amount: $convertedAmount"
            }
        }
        builder.setNegativeButton("Cancel", null)

        val dialog = builder.create()
        dialog.show()
    }

    private fun convertCurrency(amount: Double, currency: String): Double {
        val conversionRate = when (currency) {
            "CAN" -> 1.12
            "USA" -> 0.3
            "UK" -> 7.0
            "CHL" -> 0.75 // Corrected the currency code here
            else -> 1.0
        }
        return amount * conversionRate
    }
}
