package com.example.carelink.Auth.Patient.Database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carelink.R

class DoclistAdapter(private val doclist: ArrayList<DocProfile>): RecyclerView.Adapter<DoclistAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.patient_dash_doc_profile,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = doclist[position]
        holder.name.text = currentItem.name
        holder.spec.text = currentItem.spec
        holder.yoe.text = currentItem.yoe
        holder.hosname.text = currentItem.hosname
        holder.availability.text = currentItem.availability
        holder.contect.text = currentItem.contact
        holder.fee.text = currentItem.fee
        holder.city.text = currentItem.city
    }

    override fun getItemCount(): Int {
        return doclist.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.textView34)
        val spec : TextView = itemView.findViewById(R.id.textView35)
        val yoe : TextView = itemView.findViewById(R.id.textView38)
        val hosname : TextView = itemView.findViewById(R.id.textView36)
        val availability : TextView = itemView.findViewById(R.id.textView40)
        val contect : TextView = itemView.findViewById(R.id.textView39)
        val fee : TextView = itemView.findViewById(R.id.textView33)
        val city : TextView = itemView.findViewById(R.id.textView37)
    }
}
