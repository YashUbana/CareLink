package com.example.carelink.Auth.Doctor.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carelink.Auth.Doctor.Database.DocPatientAdapter
import com.example.carelink.Auth.Patient.Database.DocPatient
import com.example.carelink.R
import com.example.carelink.databinding.FragmentPatientListDocBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.toObject
import kotlin.math.ceil


class Patient_list_doc : Fragment() {
    private var binding: FragmentPatientListDocBinding?= null
    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList : ArrayList<DocPatient>
    private lateinit var docPatientAdapter: DocPatientAdapter
    private lateinit var db : FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatientListDocBinding.inflate(inflater,container, false)
        recyclerView = binding!!.docPatientRecycle
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        auth = FirebaseAuth.getInstance()


        userArrayList = arrayListOf()

        docPatientAdapter = DocPatientAdapter(userArrayList)
        recyclerView.adapter = docPatientAdapter

        EventChangeListerner()


        return binding!!.root
    }
    private fun EventChangeListerner(){
        uid = auth.currentUser!!.uid
        db = FirebaseFirestore.getInstance()
        db.collection("Doctor_Patient").document(uid).collection("Patients").
                addSnapshotListener(object : EventListener<QuerySnapshot>{
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {
                        if(error != null){
                            Log.d("Firestore error", error.message.toString())
                            return
                        }
                        for (dc : DocumentChange in value?.documentChanges!!){
                            if(dc.type == DocumentChange.Type.ADDED){
                                userArrayList.add(dc.document.toObject(DocPatient::class.java))
                            }
                        }
                        docPatientAdapter.notifyDataSetChanged()
                    }

                })
    }


}