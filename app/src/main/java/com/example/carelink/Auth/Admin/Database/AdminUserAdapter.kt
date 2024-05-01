package com.example.carelink.Auth.Admin.Database

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.carelink.Auth.Patient.Database.AdminUser
import com.example.carelink.Auth.Patient.Database.UserList
import com.example.carelink.R
import com.google.firebase.firestore.FirebaseFirestore

class AdminUserAdapter(
    private val userList: ArrayList<AdminUser>,
    private val firestore:FirebaseFirestore,
): RecyclerView.Adapter<AdminUserAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.textView10)
        val dob: TextView = itemView.findViewById(R.id.textView17)
        val gender: TextView = itemView.findViewById(R.id.textView16)
        val phone: TextView = itemView.findViewById(R.id.textView18)
        val reason: TextView = itemView.findViewById(R.id.textView19)
        val doctor: TextView = itemView.findViewById(R.id.textView20)
        val menu: ImageView = itemView.findViewById(R.id.imageView3)

        init {
            menu.setOnClickListener{ view->
                popupmenu(view)
            }
        }

        private fun popupmenu(view: View?) {
            val popupMenu = PopupMenu(itemView.context, view)
            popupMenu.inflate(R.menu.patient_admin_menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.deleteP -> {
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
            val uid = userList[position].uid
            firestore.collection("Admin").document(id!!).delete().addOnSuccessListener {
                Toast.makeText(itemView.context, "Deleted", Toast.LENGTH_SHORT).show()
            }
            firestore.collection("Users").document(uid!!).collection(uid!!).document(id!!).delete()
                .addOnSuccessListener {
                    userList.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, itemCount)
                }.addOnFailureListener{
                    Toast.makeText(itemView.context, "Failed to Delete", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdminUserAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.patient_history_list,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdminUserAdapter.MyViewHolder, position: Int) {
        val user: AdminUser = userList[position]
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