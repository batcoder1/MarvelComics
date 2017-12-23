package com.marvelapp.autentia.comics


import android.content.Context
import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobile.auth.core.IdentityManager


import android.content.Intent


class SplashActivity  : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val appContext: Context = applicationContext
        val awsConfig = AWSConfiguration(appContext)
        val identityManager = IdentityManager(appContext, awsConfig)
        IdentityManager.setDefaultIdentityManager(identityManager)


         // Go to the main activity
        val intent = Intent(this, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        this.startActivity(intent)
        this.finish()


    }

}
