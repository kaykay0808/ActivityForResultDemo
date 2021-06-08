package com.example.activityforresultdemo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.activityforresultdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val NAME = "name"
        const val EMAIL = "email"
    }

    private val firstResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                binding.tvFirstActivityResult.text = "First Activity For Result Success"
            } else if (result.resultCode == Activity.RESULT_CANCELED) {
                Log.e("Cancelled", "Cancelled")
                Toast.makeText(this, "Result Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    private val secondResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                if (data != null) {
                    val name = data.getStringExtra(NAME)
                    val email = data.getStringArrayExtra(EMAIL)
                    binding.tvSecondActivityResult.text = "$name => $email"
                }
            } else if (result.resultCode == Activity.RESULT_CANCELED) {
                Log.e("Cancelled", "Cancelled")
                Toast.makeText(this, "Result Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // click events and launch the respective activities with request codes
        binding.btnLaunchActivityFirst.setOnClickListener {
            // intention?
            val intent = Intent(this, FirstActivity::class.java)
            firstResultLauncher.launch(intent)
        }

        binding.btnLaunchActivitySecond.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            secondResultLauncher.launch(intent)
        }

    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == FIRST_ACTIVITY_REQUEST_CODE) {
                binding.tvFirstActivityResult.text = "First Activity Result Success"
            }else if(requestCode == SECOND_ACTIVITY_REQUEST_CODE){
               if(data!= null){
                   val name = data.getStringExtra(NAME)
                   val email = data.getStringArrayExtra(EMAIL)
                   binding.tvSecondActivityResult.text = "$name => $email"
               }


            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("Cancelled", "Cancelled")
            Toast.makeText(this, "Result Cancelled", Toast.LENGTH_SHORT).show()
        }
    }*/
}