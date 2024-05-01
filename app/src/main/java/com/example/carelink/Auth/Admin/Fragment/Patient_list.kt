package com.example.carelink.Auth.Admin.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carelink.Auth.Admin.Database.AdminUserAdapter
import com.example.carelink.Auth.Patient.Database.AdminUser
import com.example.carelink.Auth.Patient.Database.MyAdapter
import com.example.carelink.Auth.Patient.Database.UserList
import com.example.carelink.R
import com.example.carelink.databinding.FragmentHistoryBinding
import com.example.carelink.databinding.FragmentPatientListBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.firestore

class Patient_list : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private var binding: FragmentPatientListBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<AdminUser>
    private lateinit var myAdapter: AdminUserAdapter
    private var db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatientListBinding.inflate(inflater, container, false)

        recyclerView = binding!!.recyclerViewAdminUser
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()

        myAdapter = AdminUserAdapter(userArrayList,db)
        recyclerView.adapter = myAdapter

        EventChangeListener()

        return binding!!.root
    }


    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("Admin")
        .addSnapshotListener(object : EventListener<QuerySnapshot> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        userArrayList.add(dc.document.toObject(AdminUser::class.java))
                    }
                }
                myAdapter.notifyDataSetChanged()

            }
        })

    }
}
