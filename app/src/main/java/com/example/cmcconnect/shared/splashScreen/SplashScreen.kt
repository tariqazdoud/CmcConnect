package com.example.cmcconnect.shared.splashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cmcconnect.R
import com.example.cmcconnect.shared.login.LoginActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val logo: ImageView = findViewById(R.id.logo)
        val text: TextView = findViewById(R.id.text)
        val container: RelativeLayout = findViewById(R.id.container)

        val slideInLogo = AnimationUtils.loadAnimation(this, R.anim.slide_in)
        logo.startAnimation(slideInLogo)

        Handler().postDelayed({
            text.visibility = View.VISIBLE
            val slideInText = AnimationUtils.loadAnimation(this, R.anim.slide_in)
            text.startAnimation(slideInText)

            // After the text animation, center the combined view
            Handler().postDelayed({
                container.post {
                    val layoutParams = container.layoutParams as RelativeLayout.LayoutParams
                    layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
                    container.layoutParams = layoutParams
                }
            }, 2000) // Delay slightly more to ensure the text slide-in animation is finished
        }, 2000) // Delay for 1 second before starting the text animation

        Handler().postDelayed({
            // Navigate to the main activity after the splash screen
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000) // Splash screen duration

    }
}