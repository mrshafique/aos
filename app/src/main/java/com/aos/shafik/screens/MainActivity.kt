package com.aos.shafik.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aos.shafik.R
import com.aos.shafik.commons.CustomLoadingDialog
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author: Shafik Shaikh
 * @Date: 09-10-2022
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var pdMain: CustomLoadingDialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pdMain = CustomLoadingDialog(context = this@MainActivity)
    }
}