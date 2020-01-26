package com.example.lifeline

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_find_medicine.*

class FindMedicineActivity:AppCompatActivity()
{
    private var isFound: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_medicine)

        val searchName = intent.getStringExtra("medicine_search_name")

        val ref = FirebaseDatabase.getInstance().getReference("/Medicine/$searchName")



        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                val medicine = p0.getValue(Medicine::class.java)

                if (medicine != null) {

                    name_textView_findMedicineActivity.text = medicine.name
                    genericName_textView_findMedicineActivity.text = medicine.genericName
                    price_textView_findMedicineActivity.text = medicine.price.toString()
                    quantity_textView_findMedicineActivity.text = medicine.quantity.toString()

                }
                else
                {
                    addToCart_button_findMedicineActivity.isVisible = false

                    name_textView_findMedicineActivity.text = "Not Found"
                    genericName_textView_findMedicineActivity.text = "Not Found"
                    price_textView_findMedicineActivity.text = "Not Found"
                    quantity_textView_findMedicineActivity.text = "Not Found"

                    isFound = false


                    showMessage("Medicine not found please check you spelling.", Toast.LENGTH_LONG)

                }

            }


        })



        addToCart_button_findMedicineActivity.setOnClickListener {

//            add medicine to cart

            if(isFound) {

                quantity_editText_findMedicineActivity.isVisible = true
                confirm_button_findMedicineActivity.isVisible = true
            }

        }

        confirm_button_findMedicineActivity.setOnClickListener {

            val currentCartId = UserCartActivity.cartID

            val currentUserID = LoginActivity.currentUser?.uid
            val refDatabase = FirebaseDatabase.getInstance().getReference("/Cart/$currentUserID/$currentCartId/${name_textView_findMedicineActivity.text}")



            val medicineToAdd = CartMedicine(name_textView_findMedicineActivity.text.toString(), price_textView_findMedicineActivity.text.toString(), quantity_editText_findMedicineActivity.text.toString())

            refDatabase.setValue(medicineToAdd)
                .addOnSuccessListener {

                    showMessage("Added to Cart", Toast.LENGTH_SHORT)

                }

                .addOnFailureListener {

                    showMessage("Failed to add: ${it.message}", Toast.LENGTH_LONG)
                }

        }

    }



    private fun showMessage(msg: String, length: Int)
    {
        Toast.makeText(this, msg, length).show()
    }


}

class CartMedicine(val name: String, val price: String, val quantity: String)
{

    constructor() : this("", "", "")
}
