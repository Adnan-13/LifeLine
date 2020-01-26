package com.example.lifeline

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_user_home.*

class UserHomeActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home)

        orderMedicine_button_userHomeActivity.setOnClickListener {

//            start a new activity for medicine order
            val intent = Intent(this, SearchMedicineActivity::class.java)
            startActivity(intent)


        }

        showMedicineList_button_userHomeActivity.setOnClickListener {

//            view use home screen activity

            val intent = Intent(this, MedicineListActivity::class.java)
            startActivity(intent)

        }

        searchMeds_button_userHomeActivity.setOnClickListener {

//            search a new activity for searching medicine

            val intent = Intent(this, SearchMedicineActivity::class.java)
            startActivity(intent)
        }

        callAmbulance_button_userHomeActivity.setOnClickListener {

//            start a new activity for calling ambulance

        }

        editProfile_button_userHomeActivity.setOnClickListener {

//            start a new activity for changing user info

            val intent = Intent(this, UserEditProfileActivity::class.java)
            startActivity(intent)

        }

        showCart_button_userHomeActivity.setOnClickListener {
//            start show cart activity

            val intent = Intent(this, UserCartActivity::class.java)
            startActivity(intent)
        }

    }
}