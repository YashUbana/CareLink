package com.example.carelink.Auth.Doctor.Database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carelink.Auth.Patient.Database.DocPatient
import com.example.carelink.R

class DocPatientAdapter(private val list : ArrayList<DocPatient>) : RecyclerView.Adapter<DocPatientAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DocPatientAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_doc_patient, parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: DocPatientAdapter.MyViewHolder, position: Int) {
        val user : DocPatient = list[position]
        holder.name.text = user.name
        holder.dob.text = user.dob
        holder.gender.text = user.gender
        holder.phone.text = user.phone
        holder.reason.text = user.reason
    }

    override fun getItemCount(): Int {
        return list.size
    }
    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.textView100)
        val dob: TextView = itemView.findViewById(R.id.textView170)
        val gender: TextView = itemView.findViewById(R.id.textView160)
        val phone: TextView = itemView.findViewById(R.id.textView180)
        val reason: TextView = itemView.findViewById(R.id.textView190)
    }
}