package com.example.lifeline

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_medicine_list.*
import kotlinx.android.synthetic.main.medicine_row_medicine_list.view.*

class MedicineListActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_medicine_list)

        fetchMeds()
    }

    private fun fetchMeds()
    {
        val ref = FirebaseDatabase.getInstance().getReference("/Medicine")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()

                p0.children.forEach{
                    //Log.d("MedicineList", it.toString())

                    val medicine = it.getValue(Medicine::class.java)


                    if(medicine != null)
                    {
                        adapter.add(Meds(medicine))
                    }
                }

                recyclerView_medicineListActivity.adapter = adapter
            }
            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}

class Meds(val medicine:Medicine):Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.name_textView_medicine_row.text = medicine.name
        viewHolder.itemView.genericName_textView_medicine_row.text = medicine.genericName
        viewHolder.itemView.price_textView_medicine_row.text = medicine.price
        viewHolder.itemView.quantity_textView_medicine_row.text = medicine.quantity


    }

    override fun getLayout(): Int {
        return R.layout.medicine_row_medicine_list
    }

}