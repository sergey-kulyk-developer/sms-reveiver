package com.sergeykulyk.smsreceiver

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Telephony
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sergeykulyk.smsreceiver.databinding.ActivityMessageBinding

class MessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        checkSmsPermissions()
    }

    private fun registerSmsReceiver() {
        // phoneNumber example: +123456789
        // letteral address your service address name : Taxi, Supermarket
        val smsReceiver = SmsReceiver("Triare") { message ->
            binding.messageTextView.text = message
        }

        val intentFilter = IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        registerReceiver(smsReceiver, intentFilter)
    }

    private fun checkSmsPermissions() {
        val readSmsPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_SMS
        )
        val receiveSmsPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.RECEIVE_SMS
        )
        if (readSmsPermissionGranted != PackageManager.PERMISSION_GRANTED ||
            receiveSmsPermissionGranted != PackageManager.PERMISSION_GRANTED
        ) {
            requestSmsPermissions()
        } else {
            registerSmsReceiver()
        }
    }

    private fun requestSmsPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS
            ),
            SMS_PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                registerSmsReceiver()
            } else {
                Toast.makeText(this, "Autocomplete disabled", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        const val SMS_PERMISSION_CODE = 101
    }
}