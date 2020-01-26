package com.example.lifeline

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_check_orders.*
import kotlinx.android.synthetic.main.order_list.view.*

class CheckOrdersActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_check_orders)


        fetchOrders()

    }

    private fun fetchOrders() {

        val ref = FirebaseDatabase.getInstance().getReference("/Order")

        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                val adapter = GroupAdapter<GroupieViewHolder>()

                p0.children.forEach {

                    val order = it.getValue(Order::class.java)


                    if(order != null)
                    {
                        adapter.add(OrderListEliment(order))
                    }



                }

                orders_recyclerView_checkOrdersActivity.adapter = adapter

            }



        })

    }
}

class OrderListEliment(val order: Order) : Item<GroupieViewHolder>()
{
    override fun getLayout(): Int {

        return R.layout.order_list
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.cartID_textView_orderList.text = order.cartID
        viewHolder.itemView.prescriptionID_textView_orderList.text = order.prescriptionID
        viewHolder.itemView.totalPrice_textView_orderList.text = order.totalAmount
        val ref = FirebaseDatabase.getInstance().getReference("/users/${order.uid}")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                val user = p0.getValue(User::class.java)

                if (user != null) {
                    viewHolder.itemView.customerID_textView_orderList.text = user.username
                }

            }


        })


    }

}