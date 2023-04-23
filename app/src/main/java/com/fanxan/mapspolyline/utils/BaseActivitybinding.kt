package com.fanxan.mapspolyline.utils

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivitybinding<B : ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: B

    abstract fun inflateBinding(): B

    abstract fun oncreateBinding(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateBinding()
        setContentView(binding.root)
        oncreateBinding(savedInstanceState)
    }
}