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
import kotlinx.android.synthetic.main.activity_delivery_man_info.*
import kotlinx.android.synthetic.main.delivery_man_list.view.*

class DeliveryManInfoActivity:AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_man_info)

        fetchDeliveryMan()
    }

    private fun fetchDeliveryMan() {

        val ref = FirebaseDatabase.getInstance().getReference("/DeliveryMan")

        ref.addListenerForSingleValueEvent(object : ValueEventListener{

            override fun onDataChange(p0: DataSnapshot) {

                val adapter = GroupAdapter<GroupieViewHolder>()

                p0.children.forEach {

                    val deliveryMan = it.getValue(DeliveryMan::class.java)

                    if(deliveryMan != null)
                    {
                        adapter.add(PerDeliveryMan(deliveryMan))
                    }

                }


                deliverymanList_recyclerView_deliveryManInfoActivity.adapter = adapter
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

    }
}

class DeliveryMan(val username:String, val phoneNumber:String)
{
    constructor() : this("", "")
}

class PerDeliveryMan(val deliveryMan: DeliveryMan) : Item<GroupieViewHolder>(){
    override fun getLayout(): Int {

        return R.layout.delivery_man_list

    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {


        viewHolder.itemView.username_textView_deliveryManList.text = deliveryMan.username
        viewHolder.itemView.phoneNumber_textView_deliveryManList.text = deliveryMan.phoneNumber

    }


}