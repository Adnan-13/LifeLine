package com.example.lifeline

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private var toasterCheck: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        alreadyHaveAnAccount_textView_registerActivity.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)

            startActivity(intent)
        }

        register_button_registerActivity.setOnClickListener {

            performRegister()
        }
    }


    private fun performRegister()
    {
        val email = email_editText_registerActivity.text.toString()
        val password = password_editText_registerActivity.text.toString()

        if(email.isEmpty() || password.isEmpty())
        {
            toasterCheck = true

            Toast.makeText(this, "Please fill in the fields", Toast.LENGTH_LONG).show()
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {

                if(it.isSuccessful)
                {
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    saveUserToDatabase()
                }

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

            }

            .addOnFailureListener {

                Toast.makeText(this, "Registration failed : ${it.message}", Toast.LENGTH_LONG).show()

            }
    }

    private fun saveUserToDatabase()
    {
        val username = username_editText_registerActivity.text.toString()
        val email = email_editText_registerActivity.text.toString()
        val fullName = fullname_editText_registerActivity.text.toString()
        val phoneNumber = phoneNumber_editText_registerActivity.text.toString()
        val isAdmin = admin_checkBox_registerActivity.isChecked
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        if(username.isEmpty() || email.isEmpty() || fullName.isEmpty() || phoneNumber.isEmpty())
        {
            if(!toasterCheck) Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_LONG).show()
            return
        }

        val user = User(uid, username, email, fullName, phoneNumber, isAdmin.toString())

        ref.setValue(user)
            .addOnSuccessListener {

                Toast.makeText(this, "Saved in database", Toast.LENGTH_SHORT).show()

            }


    }
}

class User(val uid:String, var username: String, val email: String, var fullName: String, var phoneNumber: String, val admin:String)
{
    constructor() : this("", "","","","", "")
}

