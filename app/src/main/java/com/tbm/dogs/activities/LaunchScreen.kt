package com.tbm.dogs.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

import com.tbm.dogs.Helper.Var
import com.tbm.dogs.R
import com.tbm.dogs.activities.login.Login

class LaunchScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_launch_screen)
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        Handler().postDelayed({
            /* Create an Intent that will start the Menu-Activity. */
            val mainIntent = Intent(this@LaunchScreen, Login::class.java)
            this@LaunchScreen.startActivity(mainIntent)
            this@LaunchScreen.finish()
        }, Var.delayLaunchScreen.toLong())
    }
}
