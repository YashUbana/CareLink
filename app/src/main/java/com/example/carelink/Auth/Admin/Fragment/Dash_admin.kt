package com.example.carelink.Auth.Admin.Fragment

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.carelink.Auth.Patient.Database.DocAcc
import com.example.carelink.Auth.Patient.Database.UserName
import com.example.carelink.R
import com.example.carelink.databinding.FragmentDashAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Dash_admin : Fragment() {
    private var binding: FragmentDashAdminBinding? = null
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var email: EditText
    lateinit var pass: EditText
    lateinit var confirmPass: EditText
    lateinit var docname: EditText
    lateinit var progressDialog: ProgressDialog
    lateinit var firebaseDatabase: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseAuth = FirebaseAuth.getInstance()
        binding = FragmentDashAdminBinding.inflate(inflater, container, false)
        binding!!.textView22.paint?.isUnderlineText = true
        email = binding!!.editTextTextEmailAddress
        pass = binding!!.editTextTextPassword
        confirmPass = binding!!.editTextTextPassword2

        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Creating Account")
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)



        binding!!.button2.setOnClickListener {
            progressDialog.show()
            val email = email.text.toString()
            val pass = pass.text.toString()
            val confirmPass = confirmPass.text.toString()
            val name = binding!!.docName.text.toString()

            if (email.isNotEmpty() || pass.isNotEmpty() || confirmPass.isNotEmpty()) {
                if (pass.length < 6) {
                    progressDialog.hide()
                    binding!!.editTextTextPassword.error = "Password must be 6 characters long"
                    return@setOnClickListener
                }
                if (pass == confirmPass && pass.length >= 6) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            firebaseDatabase =
                                FirebaseDatabase.getInstance().getReference("DocDetail")
                            val uid = firebaseAuth.currentUser?.uid
                            val user = DocAcc(name, email, pass, uid.toString())
                            firebaseDatabase.child(uid!!).setValue(user).addOnSuccessListener {
                                binding!!.docName.text.clear()
                                binding!!.editTextTextEmailAddress.text.clear()
                                binding!!.editTextTextPassword.text.clear()
                                binding!!.editTextTextPassword2.text.clear()
                                progressDialog.hide()
                            }.addOnFailureListener {
                                Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT)
                                    .show()
                            }

                            Toast.makeText(requireContext(), "Account Created", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            progressDialog.hide()
                            Toast.makeText(
                                requireContext(),
                                it.exception.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    progressDialog.hide()
                    Toast.makeText(requireContext(), "Password not Matching", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                progressDialog.hide()
                Toast.makeText(requireContext(), "Enter every detail", Toast.LENGTH_SHORT).show()
            }
        }
        return binding!!.root
    }
}