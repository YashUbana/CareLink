package com.example.carelink.Auth.Doctor.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import com.example.carelink.Auth.Doctor.DashboardDoc
import com.example.carelink.R
import com.example.carelink.databinding.FragmentEditProfileDetailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class EditProfileDetail : Fragment() {
    private var binding: FragmentEditProfileDetailBinding? = null
    var items = mutableListOf(
        "Mumbai",
        "Delhi",
        "Bangalore",
        "Hyderabad",
        "Kolkata",
        "Chennai",
        "Pune",
        "Ahmedabad",
        "Jaipur",
        "Surat",
        "Lucknow",
        "Kanpur",
        "Nagpur",
        "Visakhapatnam",
        "Indore",
        "Thane",
        "Bhopal",
        "Patna",
        "Vadodara",
        "Ghaziabad"
    )
    private lateinit var city: String
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileDetailBinding.inflate(inflater, container, false)

        val view: View = inflater.inflate(R.layout.fragment_edit_profile_detail, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        city = "Select City"

        val autoCompleter: AutoCompleteTextView = binding!!.autoComplete
        val adapter = ArrayAdapter(requireContext(), R.layout.doctor_list_patient, items)

        autoCompleter.setAdapter(adapter)
        autoCompleter.onItemClickListener = AdapterView.OnItemClickListener { adapter, _, i, l ->
            city = adapter.getItemAtPosition(i).toString()
            Toast.makeText(requireContext(), city, Toast.LENGTH_SHORT).show()
        }


        binding!!.button4.setOnClickListener {
            val name = "Dr. " + binding!!.editTextText.text.toString()
            val spec = binding!!.editTextText2.text.toString()
            val yoe = binding!!.editTextNumber.text.toString() + " Years"
            val hosname = binding!!.editTextText4.text.toString()
            val availability = binding!!.editTextText5.text.toString()
            val contect = "+91" + binding!!.editTextText6.text.toString()
            val fee = binding!!.editTextNumber2.text.toString() + " Rs/Hour"

            if (name.isNotEmpty() && spec.isNotEmpty() && yoe.isNotEmpty() && hosname.isNotEmpty() && availability.isNotEmpty() && contect.isNotEmpty() && fee.isNotEmpty() && city != "Select City") {

                updateData(name, spec, yoe, hosname, availability, contect, fee, city)
            }else{
                Toast.makeText(requireActivity(), "Please enter all details", Toast.LENGTH_SHORT).show()
            }




        }



        return binding!!.root
    }

    @SuppressLint("SuspiciousIndentation")
    private fun updateData(
        name: String,
        spec: String,
        yoe: String,
        hosname: String,
        availability: String,
        contact: String,
        fee: String,
        city: String
    ) {
        val uid = firebaseAuth.currentUser!!.uid
        database = FirebaseDatabase.getInstance().getReference("DocProfile")
        val user = mapOf<String, String>(
            "name" to name,
            "spec" to spec,
            "yoe" to yoe,
            "hosname" to hosname,
            "availability" to availability,
            "contact" to contact,
            "fee" to fee,
            "city" to city,
        )

            database.child(uid).updateChildren(user).addOnSuccessListener {
                Toast.makeText(requireContext(), "Update Done!", Toast.LENGTH_SHORT).show()
                val editP = Profile_doc()
                val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
                transaction.replace(R.id.frame_layout, editP)
                transaction.commit()
            }.addOnFailureListener {

            }



    }

}