package com.example.carelink.Auth.Patient

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.carelink.Auth.Patient.Fragments.BookAppointment
import com.example.carelink.MainActivity
import com.example.carelink.R
import com.example.carelink.databinding.ActivityLoginPageBinding
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Getting database")
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)

        binding.textView3.setOnClickListener {
            val intent = Intent(this, SignupPage::class.java)
            startActivity(intent)
            finish()
        }
        binding.button.setOnClickListener {
            progressDialog.show()
            val email = binding.patientEmail.text.toString()
            val pass = binding.patientPass.text.toString()


            if (email.isNotEmpty() && pass.isNotEmpty()) {
                if (pass.length < 6) {
                    binding.patientPass.error = "Password must be at least 6 characters"
                    return@setOnClickListener
                } else {
                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                        progressDialog.hide()
                        if (it.isSuccessful) {
                            val intent = Intent(this, Dashboard::class.java)
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

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {

            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}