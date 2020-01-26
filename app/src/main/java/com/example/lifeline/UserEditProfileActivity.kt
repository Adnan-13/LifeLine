package com.example.lifeline

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_user_edit_profile.*

class UserEditProfileActivity:AppCompatActivity()
{
    private var newUser : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_user_edit_profile)

        showCurrentInfo()

        changePassword_button_userEditProfileActivity.setOnClickListener {

        }

        confirm_button_userEditProfileActivity.setOnClickListener {

            if(userName_editText_userEditProfileActivity.text.isEmpty() || fullName_editText_userEditProfileActivity.text.isEmpty() || phoneNumber_editText_userEditProfileActivity.text.isEmpty())
            {
                Toast.makeText(this,"Please fill in all the fields.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val username = userName_editText_userEditProfileActivity.text.toString()
            val fullName = fullName_editText_userEditProfileActivity.text.toString()
            val phoneNumber = phoneNumber_editText_userEditProfileActivity.text.toString()


            val ref = FirebaseDatabase.getInstance().getReference("/users")

            ref.child("${LoginActivity.currentUser?.uid}").child("username").setValue("$username")
            ref.child("${LoginActivity.currentUser?.uid}").child("fullName").setValue("$fullName")
            ref.child("${LoginActivity.currentUser?.uid}").child("phoneNumber").setValue("$phoneNumber")

        }

    }

    private fun showCurrentInfo() {

        val ref = FirebaseDatabase.getInstance().getReference("/users/${LoginActivity.currentUser?.uid}")

        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                val user = p0.getValue(User::class.java)

                if (user != null) {
                    userName_editText_userEditProfileActivity.setText(user.username)
                    email_textView_userEditProfileActivity.text = user.email
                    fullName_editText_userEditProfileActivity.setText(user.fullName)
                    phoneNumber_editText_userEditProfileActivity.setText(user.phoneNumber)


                }

            }


        })

    }
}