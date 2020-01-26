package com.example.lifeline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity()
{

    companion object {
        var currentUser : User? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dontHaveAnAccount_textView_loginActivity.setOnClickListener {

            val intent = Intent(this, RegisterActivity::class.java)

            startActivity(intent)

        }

        login_button_loginActivity.setOnClickListener {

            performLogin()
        }
    }

    private fun performLogin()
    {
        val email = email_editText_loginActivity.text.toString()
        val password = password_editText_loginActivity.text.toString()

        if(email.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_LONG).show()
            return
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {

                if(it.isSuccessful)
                {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                    val uid = FirebaseAuth.getInstance().uid.toString()

                    val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

                    ref.addListenerForSingleValueEvent(object : ValueEventListener{

                        override fun onDataChange(p0: DataSnapshot) {
                            currentUser = p0.getValue(User::class.java)

                            UserCartActivity.incrementCartID()

                            performSeperateLogin(currentUser)

                            Log.d("LoginCart", "${UserCartActivity.cartID}")

                        }

                        override fun onCancelled(p0: DatabaseError) {

                        }
                    })


                }
                else
                {
                    //Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                    return@addOnCompleteListener
                }

            }

            .addOnFailureListener {
                Toast.makeText(this, "Login Failed: ${it.message}", Toast.LENGTH_LONG).show()


            }


    }

    private fun performSeperateLogin(currentUser: User?) {
        if (currentUser != null) {
            Log.d("Login", "Logged in user details:\n" +
                    "Email = ${currentUser.email}\n" +
                    "Username = ${currentUser.username}\n" +
                    "Phone Number : ${currentUser.phoneNumber}\n" +
                    "Full Name = ${currentUser.fullName}\n" +
                    "Admin = ${currentUser.admin}")

            if(currentUser.admin.toBoolean())
            {
                val intent = Intent(this, AdminHomeActivity::class.java)
                startActivity(intent)
            }
            else
            {
                val intent = Intent(this, UserHomeActivity::class.java)
                startActivity(intent)
            }
        }

    }


}
