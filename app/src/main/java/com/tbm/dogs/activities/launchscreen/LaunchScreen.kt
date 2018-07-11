package com.tbm.dogs.activities.launchscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.tbm.dogs.Helper.Shared
import com.tbm.dogs.Helper.Var
import com.tbm.dogs.R
import com.tbm.dogs.activities.login.Login
import com.tbm.dogs.activities.main.Main

class LaunchScreen : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_launch_screen)
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        Handler().postDelayed({
            /* Create an Intent that will start the Menu-Activity. */
            val info = Shared(this@LaunchScreen).infoShiper
            if(info.isEmpty()){
                val mainIntent = Intent(this@LaunchScreen, Login::class.java)
                this@LaunchScreen.startActivity(mainIntent)
                this@LaunchScreen.finish()
            }else{
                this@LaunchScreen.startActivity(Intent(this@LaunchScreen, Main::class.java).putExtra("user",info))
                this@LaunchScreen.finish()
            }
        }, Var.delayLaunchScreen.toLong())
    }
}
