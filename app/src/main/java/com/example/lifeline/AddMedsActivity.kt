package com.example.lifeline

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_meds.*
import java.lang.Exception

class AddMedsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_meds)

        add_button_addMedsActivity.setOnClickListener {

            addMedicine()
        }
    }

    private fun addMedicine()
    {
        val name = name_editText_addMedsActivity.text.toString()
        val genericName = genericName_editText_addMedsActivity.text.toString()

        if(name.isEmpty() || genericName.isEmpty())
        {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_LONG).show()
            return
        }

        val price : String = price_editText_addMedsActivity.text.toString()
        val quantity : String = quantity_editText_addMedsActivity.text.toString()
        try {
            price_editText_addMedsActivity.text.toString().toFloat()
            quantity_editText_addMedsActivity.text.toString().toInt()
        } catch (e : Exception)
        {
            if(!(price_editText_addMedsActivity.text.toString().isEmpty() && quantity_editText_addMedsActivity.text.toString().isEmpty())) Toast.makeText(this, "Please enter numeric values in price and quantity fields", Toast.LENGTH_LONG).show()

            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("/Medicine/${name}")

        val medicine = Medicine(name, genericName, price, quantity)

        ref.setValue(medicine)
            .addOnCompleteListener {
                Toast.makeText(this, "Medicine added successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to add medicine : ${it.message}", Toast.LENGTH_LONG).show()
            }

    }
}

class Medicine(val name : String, val genericName : String, val price : String, val quantity : String)
{
    constructor() : this("", "", "", "")
}