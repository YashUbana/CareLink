package com.example.carelink.Auth.Admin.Database

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.carelink.Auth.Admin.Fragment.Doctor_list
import com.google.firebase.auth.FirebaseUser
import com.example.carelink.Auth.Patient.Database.DocAcc
import com.example.carelink.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.PrimitiveIterator


class AdminAdapter(
//    private val c: Context,
    private val docAcc: ArrayList<DocAcc>,
    private val db: DatabaseReference,
    private val firebaseAuth: FirebaseAuth
) : RecyclerView.Adapter<AdminAdapter.AdminViewHolder>() {

    inner class AdminViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.doc_name)
        val email: TextView = itemView.findViewById(R.id.doc_email)
        val pass: TextView = itemView.findViewById(R.id.doc_pass)
        val menu: ImageView = itemView.findViewById(R.id.imageView4)

        init {
            menu.setOnClickListener { view ->
                popupmenu(view)
            }
        }

        private fun popupmenu(view: View?) {
            val popupMenu = PopupMenu(itemView.context, view)
            popupMenu.inflate(R.menu.doc_acc_menu)
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
            val id = docAcc[position].id
            val dbref = FirebaseDatabase.getInstance().getReference("DocDetail").child(id!!)

            val delTask = dbref.removeValue()

            delTask.addOnSuccessListener {
                docAcc.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemCount)
                Doctor_list().restartFragment()
                Toast.makeText(itemView.context, "Doctor Account Deleted", Toast.LENGTH_SHORT)
                    .show()
            }.addOnFailureListener { error ->
                Toast.makeText(itemView.context, error.toString(), Toast.LENGTH_SHORT).show()
            }
        }


    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdminAdapter.AdminViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.doc_acc_list, parent, false)
        return AdminViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdminAdapter.AdminViewHolder, position: Int) {
        val doc: DocAcc = docAcc[position]
        holder.name.text = "Dr. " + doc.name!!.uppercase()
        holder.email.text = doc.email
        holder.pass.text = doc.pass
    }

    override fun getItemCount(): Int {
        return docAcc.size
    }
}

