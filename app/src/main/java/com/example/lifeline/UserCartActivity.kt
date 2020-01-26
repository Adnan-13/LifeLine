package com.example.lifeline

import android.content.Intent
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_medicine_list.*
import kotlinx.android.synthetic.main.activity_user_cart.*
import kotlinx.android.synthetic.main.cart_list_row.view.*

class UserCartActivity:AppCompatActivity()
{
    companion object
    {
        var cartID : Long = 0

        public fun incrementCartID()
        {
            val ref = FirebaseDatabase.getInstance().getReference("/CartID")

            var id : Long = 0

            ref.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {

                    val currentCardRef = p0.getValue(CartID::class.java)

                    if (currentCardRef != null) {
                        id = currentCardRef.value.toLong()

                        //Log.d("LoginCart", "$id")

                        val newID = id + 1

                        val incrementedRef = CartID(newID.toString())

                        cartID = newID
                        ref.setValue(incrementedRef)


                    }

                }

            })

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_cart)

        val ref = FirebaseDatabase.getInstance().getReference("/CartID")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val cartIDObj = p0.getValue(CartID::class.java)

                if (cartIDObj != null) {
                    cartID = cartIDObj.value.toLong()
                }

            }


        })

        fetchCartMedicines()

        confirm_button_userCartActivity.setOnClickListener {


            var totalPrice : Float = 0f

            for(i in 1 until cartList_recyclerView_userCartActivity.childCount)
            {
                val medName = cartList_recyclerView_userCartActivity[i].name_textView_cartListRow.text.toString()
                val price = cartList_recyclerView_userCartActivity[i].price_textView_cartListRow.text.toString().toFloat()
                val quantity = cartList_recyclerView_userCartActivity[i].quantity_editText_cartListRow.text.toString().toInt()

                totalPrice += price * quantity

                val ref = FirebaseDatabase.getInstance().getReference("/Medicine/$medName")

                ref.addListenerForSingleValueEvent(object :ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {

                        val medicine = p0.getValue(Medicine::class.java)

                        val newQuantity = medicine!!.quantity.toInt() - quantity

                        if(newQuantity < 0)
                        {
                            showMessage("Requested amount of medicine is not available", Toast.LENGTH_LONG)
                            return
                        }

                        val ref2 = FirebaseDatabase.getInstance().getReference("/Medicine")

                        ref2.child(medName).child("quantity").setValue(newQuantity.toString())
                    }


                })

            }



            val prescriptionID = prescriptionID_editText_userCartActivity.text.toString()

            if(prescriptionID.isEmpty())
            {

//                Toast.makeText(this, "Please enter prescription ID.", Toast.LENGTH_LONG).show()
                prescriptionID_editText_userCartActivity.error = "Please enter prescription ID"
                prescriptionID_editText_userCartActivity.requestFocus()

                return@setOnClickListener

            }

            val intent = Intent(this, UserPaymentActivity::class.java)

            intent.putExtra("Prescription_ID", prescriptionID)
            intent.putExtra("total_price", totalPrice.toString())
            startActivity(intent)



        }
    }

    private fun fetchCartMedicines() {

        val currentUserID = LoginActivity.currentUser?.uid
        val currentCartID = cartID
        val ref = FirebaseDatabase.getInstance().getReference("/Cart/$currentUserID/$currentCartID")

        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                val adapter = GroupAdapter<GroupieViewHolder>()

                val titleCartMedicine = CartMedicine("Name", "Price", "Quantity")
                adapter.add(CartMed(titleCartMedicine))

                p0.children.forEach {

                    val cartMedicine = it.getValue(CartMedicine::class.java)


                    if (cartMedicine != null) {
                        Log.d("CartView", "${cartMedicine.name}\n${cartMedicine.price}\n${cartMedicine.quantity}")

                        adapter.add(CartMed(cartMedicine))

                    }

                }

                cartList_recyclerView_userCartActivity.adapter = adapter



            }


        })


    }

    private fun showMessage(msg: String, length: Int)
    {
        Toast.makeText(this, msg, length).show()
    }



}

class CartID(val value: String)
{
    constructor() : this("")
}

class CartMed(val cartMedicine: CartMedicine) : Item<GroupieViewHolder>(){

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.name_textView_cartListRow.text = cartMedicine.name
        viewHolder.itemView.price_textView_cartListRow.text = cartMedicine.price
        viewHolder.itemView.quantity_editText_cartListRow.setText(cartMedicine.quantity)

    }

    override fun getLayout(): Int {
        return R.layout.cart_list_row
    }
}