package com.fanxan.mapspolyline

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.fanxan.mapspolyline.databinding.ActivityUserBinding

class UserActivity:AppCompatActivity() {
    private lateinit var binding:ActivityUserBinding
    private var counter = 1
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCounter.setOnClickListener {
            counter +=1
            binding.textCounter.text = "$counter"

            println("------- Counter ----> $counter -------")
        }
    }
}