package com.example.carelink.Auth.Admin.Fragment

import android.adservices.adid.AdId
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carelink.Auth.Admin.Database.AdminAdapter
import com.example.carelink.Auth.Patient.Database.DocAcc
import com.example.carelink.R
import com.example.carelink.databinding.FragmentDoctorListBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.firestore

class Doctor_list : Fragment() {
    private var binding: FragmentDoctorListBinding? = null
    private lateinit var recycleView: RecyclerView
    private lateinit var docAccList: ArrayList<DocAcc>
    private lateinit var adminAdapter: AdminAdapter
    private lateinit var db : DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var progressDialog: ProgressDialog
    val uid = FirebaseAuth.getInstance().currentUser!!.uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        progressDialog.show()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDoctorListBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Showing Details")
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)

        recycleView = binding!!.adminRecycle
        recycleView.layoutManager = LinearLayoutManager(requireContext())
        recycleView.setHasFixedSize(true)

        docAccList = arrayListOf()

        getData()

        println(context.toString())
        adminAdapter = AdminAdapter( docAccList, db,firebaseAuth)
        recycleView.adapter = adminAdapter

        progressDialog.hide()
        return binding!!.root
    }

    fun restartFragment(){
        if (isAdded){
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, Doctor_list())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    private fun getData() {
        db = FirebaseDatabase.getInstance().getReference("DocDetail")
        db.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(DocAcc::class.java)
                        docAccList.add(user!!)
                    }
                    recycleView.adapter = AdminAdapter( docAccList, db,firebaseAuth)
                    progressDialog.hide()

                }else {
                    progressDialog.hide()
                    binding!!.textView24.visibility = View.VISIBLE
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
            }

        })

    }
}