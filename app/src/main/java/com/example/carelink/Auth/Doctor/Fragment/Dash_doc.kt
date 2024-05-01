package com.example.carelink.Auth.Doctor.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carelink.R
import com.example.carelink.databinding.FragmentDashDocBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Locale
import java.util.Objects


class Dash_doc : Fragment() {

    private var binding: FragmentDashDocBinding?=null
    private lateinit var databaseReference : DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var mic: ImageView
    private val REQUEST_CODE_SPEECH_INPUT = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashDocBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()


        thisIsMicToText()
        binding!!.imageView15.setOnClickListener {
            val searchText = binding!!.editTextText3.text
            searchText.replace("\\s+".toRegex(),"+")

            val searchUri = Uri.parse("https://www.google.com/search?q=$searchText")
            val intent = Intent(Intent.ACTION_VIEW, searchUri)

            if (searchText != null) {
                // Start the activity
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Please Type Something", Toast.LENGTH_SHORT).show()
            }
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("DocProfile")


        return binding!!.root
    }
    private fun thisIsMicToText() {
        mic = binding!!.imageView14

        mic.setOnClickListener{
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")

            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            }catch (e:Exception){
                Toast.makeText(requireContext(), "e.message", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_SPEECH_INPUT){
            if (resultCode == AppCompatActivity.RESULT_OK && data != null){
                val res : ArrayList<String> = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
                binding!!.editTextText3.setText(
                    Objects.requireNonNull(res)[0]
                )
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        val uid = auth.uid
        databaseReference.child(uid!!).get().addOnSuccessListener {
            if(it.exists()){
                val name = it.child("name").value

















                binding!!.textView32.text = "Welcome, ${name.toString().uppercase()}"
            }
        }

    }




}