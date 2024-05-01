package com.example.carelink.Auth.Doctor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.carelink.Auth.Patient.Database.DocProfile
import com.example.carelink.R
import com.example.carelink.databinding.ActivityEnterProfileDetailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EnterProfileDetail : AppCompatActivity() {
    lateinit var binding: ActivityEnterProfileDetailBinding
    private lateinit var db:DatabaseReference
    private lateinit var auth:FirebaseAuth
    var items = mutableListOf("Mumbai", "Delhi", "Bangalore", "Hyderabad", "Kolkata", "Chennai", "Pune", "Ahmedabad", "Jaipur", "Surat", "Lucknow", "Kanpur", "Nagpur", "Visakhapatnam", "Indore", "Thane", "Bhopal", "Patna", "Vadodara", "Ghaziabad")
    private lateinit var city : String

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEnterProfileDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        val autoCompleter : AutoCompleteTextView = binding!!.autoComplete
        val adapter = ArrayAdapter(this, R.layout.doctor_list_patient,items)

        autoCompleter.setAdapter(adapter)
        autoCompleter.onItemClickListener = AdapterView.OnItemClickListener{ adapter, _, i, l ->
            city = adapter.getItemAtPosition(i).toString()
            Toast.makeText(this, city, Toast.LENGTH_SHORT).show()
        }

        binding.button4.setOnClickListener {
            val name = "Dr. " + binding.editTextText.text.toString()
            val spec = binding.editTextText2.text.toString()
            val yoe = binding.editTextNumber.text.toString() + " Years"
            val hosname = binding.editTextText4.text.toString()
            val availability = binding.editTextText5.text.toString()
            val contect ="+91"+binding.editTextText6.text.toString()
            val fee = binding!!.editTextNumber2.text.toString()+" Rs/Hour"
            val city = city
            val uid = uid


            if(name.isEmpty()){
                binding.editTextText.error = "Please enter your name"
                return@setOnClickListener
            }
            if(spec.isEmpty()){
                binding.editTextText2.error = "Please enter your specialization"
                return@setOnClickListener
            }
            if(yoe.isEmpty()){
                binding.editTextNumber.error = "Please enter your years of experience"
                return@setOnClickListener
            }
            if(hosname.isEmpty()){
                binding.editTextText4.error = "Please enter your hospital name"
                return@setOnClickListener
            }
            if(availability.isEmpty()){
                binding.editTextText5.error = "Please enter your availability"
                return@setOnClickListener
            }
            if(contect.isEmpty()){
                binding.editTextText6.error = "Please enter your contact number"
                return@setOnClickListener
            }
            if(fee.isEmpty()){
                binding.editTextNumber2.error = "Please enter your fee"
                return@setOnClickListener
            }



            if(name.isNotEmpty() && spec.isNotEmpty() && yoe.isNotEmpty() && hosname.isNotEmpty() && availability.isNotEmpty() && contect.isNotEmpty() && fee.isNotEmpty()){
                db = FirebaseDatabase.getInstance().getReference("DocProfile")

                val docpro = DocProfile(name, spec, yoe, hosname, availability,contect,fee,city,uid.toString())

                db.child(uid!!).setValue(docpro).addOnSuccessListener {
//                    binding.editTextText.text.clear()
//                    binding.editTextText2.text.clear()
//                    binding.editTextNumber.text.clear()
//                    binding.editTextText4.text.clear()
//                    binding.editTextText5.text.clear()
//                    binding.editTextText6.text.clear()
                    Toast.makeText(this, "Profile Saved", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
                val intent = Intent(this, DashboardDoc::class.java)
                startActivity(intent)
                finish()

            }else{
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show()
            }
        }

    }
}