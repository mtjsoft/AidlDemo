package cn.mtjsoft.aidlservice

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.mtjsoft.aidlservice.service.AIDLService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startService(v: View) {
        startService(Intent(this, AIDLService::class.java))
    }
}