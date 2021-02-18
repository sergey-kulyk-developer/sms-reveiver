package com.sergeykulyk.smsreceiver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sergeykulyk.smsreceiver.databinding.ActivityChooseParserBinding

class ChooseParserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChooseParserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseParserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.smsParser.setOnClickListener {
            val intent = Intent(this, MessageActivity::class.java)
            startActivity(intent)
        }
        binding.smsPatternParser.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}