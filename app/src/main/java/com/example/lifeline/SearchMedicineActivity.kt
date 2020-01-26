package com.example.lifeline

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_search_medicine.*

class SearchMedicineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_medicine)


        search_button_searchMedicineActivity.setOnClickListener {

            //start a new activity. find medicine

            val intent = Intent(this, FindMedicineActivity::class.java)

            intent.putExtra("medicine_search_name", name_editText_searchMedicineActivity.text.toString())

            Log.d("Search Med", "$intent")

            startActivity(intent)


        }

    }
}