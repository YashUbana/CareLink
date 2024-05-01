package com.example.carelink.Auth.Patient.Fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carelink.Auth.Patient.Database.MyAdapter
import com.example.carelink.Auth.Patient.Database.UserList
import com.example.carelink.databinding.FragmentHistoryBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

class History : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<UserList>
    private lateinit var myAdapter: MyAdapter
    private var binding: FragmentHistoryBinding? = null
    private var db = Firebase.firestore
    private lateinit var firebaseAuth: FirebaseAuth
    val uid = FirebaseAuth.getInstance().currentUser!!.uid
    lateinit var progressDialog: ProgressDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Getting database")
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)

        recyclerView = binding!!.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()

        myAdapter = MyAdapter(requireContext(), userArrayList, db, firebaseAuth)
        recyclerView.adapter = myAdapter

        EventChangeListener()

        return binding!!.root
    }

    private fun EventChangeListener() {

        db = FirebaseFirestore.getInstance()
        db.collection("Users").document(uid).collection(uid)
            .orderBy("name", Query.Direction.ASCENDING)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    progressDialog.show()
                    if (error != null) {
                        progressDialog.hide()
                        Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT)
                            .show()
                        return
                    }

                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            progressDialog.hide()
                            binding!!.textView.text = ""
                            userArrayList.add(dc.document.toObject(UserList::class.java))
                        }
                    }
                    myAdapter.notifyDataSetChanged()
                    progressDialog.hide()
                }

            })
        progressDialog.hide()
    }

}



