package com.aos.shafik.screens.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.aos.shafik.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @Author: Shafik Shaikh
 * @Date: 09-10-2022
 */
class FragSplash : Fragment(R.layout.frag_splash) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            delay(1800)
            findNavController()
                .navigate(
                    FragSplashDirections.actionFragSplashToFragDashboard(),
                    NavOptions.Builder()
                        .setPopUpTo(
                            R.id.fragSplash,
                            true
                        ).build()
                )
        }
    }
}