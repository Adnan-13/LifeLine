package com.example.lifeline

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_admin_home.*

class AdminHomeActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_admin_home)



        updateInventory_button_adminHomeActivity.setOnClickListener {

            val intent = Intent(this, AddMedsActivity::class.java)

            startActivity(intent)

        }

        addMeds_button_adminHomeActivity.setOnClickListener {

            val intent = Intent(this, AddMedsActivity::class.java)

            startActivity(intent)

        }

        updateHospitalList_button_adminHomeActivity.setOnClickListener {

        }

        checkOrders_button_adminHomeActivity.setOnClickListener {

            val intent = Intent(this, CheckOrdersActivity::class.java)
            startActivity(intent)

        }
        contactDeliveryMan_button_adminHomeActivity.setOnClickListener {

            val intent = Intent(this, DeliveryManInfoActivity::class.java)
            startActivity(intent)

        }
        checkMedicines_button_adminHomeActivity.setOnClickListener {

            val intent = Intent(this, MedicineListActivity::class.java)
            startActivity(intent)

        }
    }
}