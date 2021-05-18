package com.abproject.athsample.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.abproject.athsample.view.main.MainActivity
import com.abproject.athsample.R
import com.abproject.athsample.view.auth.AuthActivity
import org.koin.android.viewmodel.ext.android.viewModel
/**
 * Created by Abolfazl on 5/15/21
 */
class SplashActivity : AppCompatActivity() {
    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handleSplashScreen()
    }

    private fun handleSplashScreen() {
        Handler().postDelayed({
            if (splashViewModel.checkExistingUser()) {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                this.finish()
            } else {
                val intent = Intent(this, AuthActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                this.finish()
            }
        }, 2000)
    }
}