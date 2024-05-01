package com.example.carelink.Auth.Patient.Database

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.carelink.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MyAdapter(
    private val c: Context,
    private val userList: ArrayList<UserList>,
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.textView10)
        val dob: TextView = itemView.findViewById(R.id.textView17)
        val gender: TextView = itemView.findViewById(R.id.textView16)
        val phone: TextView = itemView.findViewById(R.id.textView18)
        val reason: TextView = itemView.findViewById(R.id.textView19)
        val doctor: TextView = itemView.findViewById(R.id.textView20)
        val menu: ImageView = itemView.findViewById(R.id.imageView3)
        private val uid = firebaseAuth.currentUser!!.uid

        init {
            menu.setOnClickListener { view ->
                popupmenu(view)
            }
        }

        private fun popupmenu(view: View?) {
            val popupMenu = PopupMenu(itemView.context, view)
            popupMenu.inflate(R.menu.patient_history_menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.delete -> {
                        val position = adapterPosition
                        if (position != RecyclerView.NO_POSITION) {
                            deleteItem(position)
                        }
                        true
                    }

                    else -> false
                }
            }
            popupMenu.show()
        }

        private fun deleteItem(position: Int) {
            val id = userList[position].id
            firestore.collection("Users").document(uid).collection(uid).document(id!!).delete()
                .addOnSuccessListener {
                    userList.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, itemCount)
                    Toast.makeText(itemView.context, "Deleted", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(itemView.context, "Failed to Delete", Toast.LENGTH_SHORT).show()
                }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.patient_history_list, parent, false)
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {
        val user: UserList = userList[position]
        holder.name.text = user.name!!.uppercase()
        holder.dob.text = user.dob
        holder.gender.text = user.gender
        holder.phone.text = user.phone
        holder.reason.text = user.reason
        holder.doctor.text = user.doctor
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}