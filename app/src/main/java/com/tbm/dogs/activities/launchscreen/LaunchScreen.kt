package com.tbm.dogs.activities.launchscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.tbm.dogs.Helper.Action
import com.tbm.dogs.Helper.Shared
import com.tbm.dogs.Helper.Var
import com.tbm.dogs.R
import com.tbm.dogs.activities.login.Login

class LaunchScreen : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_launch_screen)
        val available = Action().checkServices(this)
        Log.e("services:", available.toString())
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        if(available){
            Handler().postDelayed({
                /* Create an Intent that will start the Menu-Activity. */
                val info = Shared(this@LaunchScreen).infoShiper
                if(info.isEmpty()){
                    val mainIntent = Intent(this@LaunchScreen, Login::class.java)
                    this@LaunchScreen.startActivity(mainIntent)
                    this@LaunchScreen.finish()
                }else{
                    this@LaunchScreen.startActivity(Intent(this@LaunchScreen, Login::class.java).putExtra("shiper",info))
                    this@LaunchScreen.finish()
                }
            }, Var.delayLaunchScreen.toLong())
        }else{
            Toast.makeText(this,"Máy của bạn hiện chưa cài đặt dịch vụ của Google, ứng dụng cần sử dụng dịch vụ của Google!",Toast.LENGTH_LONG).show()
        }

    }
}
