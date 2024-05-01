package com.example.carelink.Auth.Doctor

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.carelink.databinding.ActivityLoginDocBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginDoc : AppCompatActivity() {
    private lateinit var binding: ActivityLoginDocBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: DatabaseReference
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginDocBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Getting database")
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)

        binding.button.setOnClickListener {
            val email = binding.patientEmail.text.toString()
            val pass = binding.patientPass.text.toString()


            if (email.isEmpty()) {
                binding.patientEmail.error = "Enter Email"
                return@setOnClickListener
            }
            if (pass.isEmpty()) {
                binding.patientPass.error = "Enter Password"
                return@setOnClickListener
            }


            if (email.isNotEmpty() && pass.isNotEmpty()) {
                if(pass.length < 6){
                    binding.patientPass.error = "Password must be 6 characters long"
                    return@setOnClickListener
                }

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    progressDialog.show()
                    if (it.isSuccessful) {
                        val uid = firebaseAuth.currentUser?.uid
                        db = FirebaseDatabase.getInstance().reference
                        db.child("DocProfile").child(uid!!).child("name").get().addOnSuccessListener {
                            progressDialog.show()
                            if(it.exists()){
                                progressDialog.hide()
                                val intent =
                                    Intent(this,DashboardDoc::class.java)
                                startActivity(intent)
                                finish()
                            }else{
                                progressDialog.hide()
                                val intent =
                                    Intent(this,EnterProfileDetail::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }



                        val intent =
                            Intent(this,DashboardDoc::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        progressDialog.hide()
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                if(email.isEmpty()){
                    binding.patientEmail.error = "Enter Email"
                    return@setOnClickListener
                }
                if(pass.isEmpty()){
                    binding.patientPass.error = "Enter Password"
                    return@setOnClickListener
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser != null) {
            progressDialog.show()
            val uid = firebaseAuth.currentUser?.uid
            db = FirebaseDatabase.getInstance().reference
            db.child("DocProfile").child(uid!!).child("name").get().addOnSuccessListener {
                progressDialog.show()
                if(it.exists()){
                    progressDialog.hide()
                    val intent =
                        Intent(this,DashboardDoc::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    progressDialog.hide()
                    val intent =
                        Intent(this,EnterProfileDetail::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
