package com.example.carelink.Auth.Doctor.Fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.carelink.MainActivity
import com.example.carelink.R
import com.example.carelink.databinding.FragmentProfileDocBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Profile_doc : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db : DatabaseReference
    private var binding:FragmentProfileDocBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileDocBinding.inflate(inflater,container,false)
        firebaseAuth = FirebaseAuth.getInstance()
        val uid = firebaseAuth.currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference


        db.child("DocProfile").child(uid).get().addOnSuccessListener {
            if (it.exists()){
                val name = it.child("name").value
                val spec = it.child("spec").value
                val hos = it.child("hosname").value
                val location = it.child("city").value
                val yoe = it.child("yoe").value
                val phone = it.child("contact").value
                val availability = it.child("availability").value
                val fee = it.child("fee").value


                binding!!.textView34.text = name.toString().uppercase()
                binding!!.textView35.text = spec.toString()
                binding!!.textView36.text = hos.toString()
                binding!!.textView37.text = location.toString().uppercase()
                binding!!.textView38.text = yoe.toString()
                binding!!.textView39.text = phone.toString()
                binding!!.textView40.text = availability.toString().uppercase()
                binding!!.textView33.text = fee.toString()

                binding!!.textView40.paint?.isUnderlineText = true
                binding!!.textView33.paint?.isUnderlineText = true
            }
        }


        binding?.button5?.setOnClickListener {
            val editP = EditProfileDetail()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.frame_layout,editP)
            transaction.commit()
        }

        binding!!.button6.setBackgroundColor(Color.RED)
        binding!!.button6.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)

        }

        return binding!!.root

    }



}