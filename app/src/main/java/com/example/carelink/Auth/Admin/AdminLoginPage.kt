package com.example.carelink.Auth.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.carelink.Auth.Patient.Dashboard
import com.example.carelink.R
import com.example.carelink.databinding.ActivityAdminLoginPageBinding
import com.google.firebase.auth.FirebaseAuth
/*  login credential:
email : admin@gmail.com
pass : 123456  */
class AdminLoginPage : AppCompatActivity() {
    lateinit var binding: ActivityAdminLoginPageBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAdminLoginPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.button.setOnClickListener {
            val email = binding.patientEmail.text.toString()
            val pass = binding.patientPass.text.toString()
            if(email.isEmpty()){
                binding.patientEmail.error = "Enter Email"
                return@setOnClickListener
            }
            if(pass.isEmpty()){
                binding.patientPass.error = "Enter Password"
                return@setOnClickListener
            }

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, com.example.carelink.Auth.Admin.Dashboard::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}