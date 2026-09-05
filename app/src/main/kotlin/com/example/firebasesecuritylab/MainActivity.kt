package com.example.firebasesecuritylab

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasesecuritylab.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Database
        database = Firebase.database.reference

        // Set up click listeners
        binding.btnTest.setOnClickListener { testFirebase() }
        binding.btnWrite.setOnClickListener { writeToFirebase() }
        binding.btnRead.setOnClickListener { readFromFirebase() }
    }

    private fun testFirebase() {
        val url = binding.etFirebaseUrl.text.toString().trim()
        val apiKey = binding.etApiKey.text.toString().trim()

        if (url.isEmpty() || apiKey.isEmpty()) {
            showError("Please enter Firebase URL and API Key")
            return
        }

        try {
            // Reconfigure Firebase with custom URL
            val firebaseOptions = com.google.firebase.FirebaseOptions.Builder()
                .setDatabaseUrl(url)
                .build()

            binding.tvStatus.text = "Testing Firebase connection..."
            binding.tvStatus.setTextColor(getColor(android.R.color.holo_blue_light))

            Toast.makeText(this, "Testing connection...", Toast.LENGTH_SHORT).show()

            // Try a simple test write
            val testRef = database.child("test").child("connection")
            testRef.setValue("Connection test at ${getCurrentTime()}")
                .addOnSuccessListener {
                    showSuccess("✓ Firebase connection successful!")
                }
                .addOnFailureListener { e ->
                    showError("✗ Connection failed: ${e.message}")
                }
        } catch (e: Exception) {
            showError("Error: ${e.message}")
        }
    }

    private fun writeToFirebase() {
        val fieldName = binding.etFieldName.text.toString().trim()
        val fieldValue = binding.etFieldValue.text.toString().trim()

        if (fieldName.isEmpty() || fieldValue.isEmpty()) {
            showError("Please enter field name and value")
            return
        }

        try {
            val data = hashMapOf(
                "value" to fieldValue,
                "timestamp" to getCurrentTime(),
                "deviceId" to android.provider.Settings.Secure.getString(
                    contentResolver,
                    android.provider.Settings.Secure.ANDROID_ID
                )
            )

            database.child("data").child(fieldName).setValue(data)
                .addOnSuccessListener {
                    showSuccess("✓ Data written successfully!")
                    binding.etFieldName.text.clear()
                    binding.etFieldValue.text.clear()
                }
                .addOnFailureListener { e ->
                    showError("✗ Write failed: ${e.message}")
                }
        } catch (e: Exception) {
            showError("Error: ${e.message}")
        }
    }

    private fun readFromFirebase() {
        val fieldName = binding.etFieldName.text.toString().trim()

        if (fieldName.isEmpty()) {
            showError("Please enter field name to read")
            return
        }

        try {
            database.child("data").child(fieldName).get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        val data = snapshot.value
                        binding.tvStatus.text = "✓ Data read:\n$data"
                        binding.tvStatus.setTextColor(getColor(android.R.color.holo_green_light))
                        showSuccess("✓ Data read successfully!")
                    } else {
                        showError("✗ No data found for: $fieldName")
                    }
                }
                .addOnFailureListener { e ->
                    showError("✗ Read failed: ${e.message}")
                }
        } catch (e: Exception) {
            showError("Error: ${e.message}")
        }
    }

    private fun showSuccess(message: String) {
        binding.tvStatus.text = message
        binding.tvStatus.setTextColor(getColor(android.R.color.holo_green_light))
    }

    private fun showError(message: String) {
        binding.tvStatus.text = message
        binding.tvStatus.setTextColor(getColor(android.R.color.holo_red_light))
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return formatter.format(Date())
    }
}
