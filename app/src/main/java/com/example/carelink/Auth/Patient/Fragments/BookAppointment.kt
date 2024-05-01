package com.example.carelink.Auth.Patient.Fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.carelink.R
import com.example.carelink.databinding.FragmentBookAppointmentBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore

class BookAppointment : Fragment() {
    private var binding: FragmentBookAppointmentBinding? = null
    private lateinit var gen: String
    private lateinit var doc: String
    private lateinit var ids: String
    private val db = Firebase.firestore
    lateinit var ref: DocumentReference
    lateinit var ref2: DocumentReference
    lateinit var ref3: DocumentReference
    lateinit var docName: DatabaseReference
    var items = mutableMapOf<String, String>()
    var keysList = mutableListOf<String>()
    val uid = FirebaseAuth.getInstance().currentUser!!.uid


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        docName = FirebaseDatabase.getInstance().getReference("DocDetail")
        fetchDocName()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookAppointmentBinding.inflate(inflater, container, false)
        gen = "Male"
        binding!!.textView9.setTextColor(Color.parseColor("#72FFEB3B"))

        ref = db.collection("Users").document(uid).collection(uid).document()
        ids = ref.id
        ref2 = db.collection("Admin").document(ids)


        doc = "Select Doctor"




        binding!!.switch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                gen = "Female"
                binding!!.textView7.setTextColor(Color.parseColor("#72FFEB3B"))
                binding!!.textView9.setTextColor(Color.BLACK)
            } else {
                gen = "Male"
                binding!!.textView9.setTextColor(Color.parseColor("#72FFEB3B"))
                binding!!.textView7.setTextColor(Color.BLACK)
            }
        }




        binding!!.button3.setOnClickListener {
            val name = binding!!.name.text.toString().trim()
            val dob = binding!!.date.text.toString().trim()
            val gender = gen
            val phone = binding!!.phone.text.toString().trim()
            val reason = binding!!.reason.text.toString().trim()
            val doctor = doc
            val ids = ids
            val uid = uid
            val doc_id = items[doc]
            ref3 = db.collection("Doctor_Patient").document(doc_id.toString()).collection("Patients")
                    .document();

            if (name.isEmpty()) {
                binding!!.name.error = "Please enter your name"
                return@setOnClickListener
            }
            if (dob.isEmpty()) {
                binding!!.date.error = "Please enter your date of birth"
                return@setOnClickListener
            }

            if (phone.isEmpty()) {
                binding!!.phone.error = "Please enter your phone number"
                return@setOnClickListener
            }
            else if (phone.length != 10) {
                binding!!.phone.error = "Please enter a valid phone number"
                return@setOnClickListener
            }


            if (reason.isEmpty()) {
                binding!!.reason.error = "Please enter your reason"
                return@setOnClickListener
            }
            else if(reason.length < 10){
                binding!!.reason.error = "Please enter detailed reason (min 10 characters)"
                return@setOnClickListener
            }

            if (doctor.isEmpty()) {
                binding!!.autoComplete.error = "Please select a doctor"
                return@setOnClickListener
            }
            if (doctor == "Select Doctor") {
                Toast.makeText(requireContext(), "Please select a doctor", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if(name.isNotEmpty() && dob.isNotEmpty() && phone.isNotEmpty() && reason.isNotEmpty() && doctor.isNotEmpty() && ids.isNotEmpty() && uid.isNotEmpty() &&
                phone.length == 10 && reason.length >= 10 && doctor != "Select Doctor") {

                val userMap = hashMapOf(
                    "name" to name,
                    "dob" to dob,
                    "gender" to gender,
                    "phone" to phone,
                    "reason" to reason,
                    "doctor" to doctor,
                    "id" to ids,
                    "uid" to uid,
                )
                val docpatient = hashMapOf(
                    "name" to name,
                    "dob" to dob,
                    "gender" to gender,
                    "phone" to phone,
                    "reason" to reason,
                )
                ref.set(userMap)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Appointment Booked", Toast.LENGTH_SHORT)
                            .show()
                        binding!!.name.text.clear()
                        binding!!.date.text.clear()
                        binding!!.phone.text.clear()
                        binding!!.reason.text.clear()
                    }.addOnFailureListener {
                        Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                ref2.set(userMap)
                ref3.set(docpatient)
            }else{
                Toast.makeText(requireContext(), "Please enter every detail", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return binding!!.root
    }

    fun fetchDocName() {
        val docRef = docName
        docRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { doc ->
                    val name = doc.child("name").getValue(String::class.java)
                    val doc_id = doc.child("id").getValue(String::class.java)

                    items[name.toString()] = doc_id.toString()
                    keysList = items.keys.toMutableList()

                    spinner(keysList)

                }
            }


            override fun onCancelled(error: DatabaseError) {
                Log.d("BookAppointment", "Error: ${error.message}")
            }

        })
    }


    private fun spinner(keysList: MutableList<String>) {
        val autoCompleter: AutoCompleteTextView = binding!!.autoComplete

        println(keysList)
        val adapter = ArrayAdapter(requireContext(), R.layout.doctor_list_patient, keysList)

        autoCompleter.setAdapter(adapter)
        autoCompleter.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->

                doc = adapterView.getItemAtPosition(i).toString()
                Toast.makeText(requireContext(), doc, Toast.LENGTH_SHORT).show()
            }
    }
}