package com.example.lifeline

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_user_payment.*

class UserPaymentActivity :AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_payment)

        totalAmount_textView_userPaymentActivity.text = intent.getStringExtra("total_price") + " Taka Only"

        cash_button_user_payment_activity.setOnClickListener {

            val ref = FirebaseDatabase.getInstance().getReference("/Order/${LoginActivity.currentUser?.uid}")

            val order = Order(UserCartActivity.cartID.toString(), intent.getStringExtra("Prescription_ID"),
                LoginActivity.currentUser?.uid.toString(), totalAmount_textView_userPaymentActivity.text.toString()
            )


            ref.setValue(order)
                .addOnSuccessListener {
                    Toast.makeText(this, "Order Successfully Placed. We will contact you as soon as possible", Toast.LENGTH_LONG).show()
                }
        }

    }
}

class Order(val cartID:String, val prescriptionID:String, val uid:String, val totalAmount:String)
{
    constructor() : this("", "","", "")
}