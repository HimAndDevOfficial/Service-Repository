package com.example.servicesample

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.servicesample.BoundService.MyBinder
import com.example.servicesample.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    //BoundService class Object
    var boundService: BoundService? = null

    //boolean variable to keep a check on service bind and unbind event
    var isBound = false

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btnStart.setOnClickListener {
                startService(Intent(this@MainActivity, MyService::class.java))
            }

            btnStop.setOnClickListener {
                stopService(Intent(this@MainActivity, MyService::class.java))
            }
        }

    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, BoundService::class.java)
        startService(intent)
        bindService(intent, boundServiceConnection, BIND_AUTO_CREATE)
    }

    override fun onResume() {
        super.onResume()
        val runnable = Runnable {
            Toast.makeText(
                this@MainActivity,
                boundService!!.randomGenerator().toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
        val handler = Handler()
        handler.postDelayed(runnable, 3000)
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(boundServiceConnection)
            isBound = false
        }
    }
//Called when a connection to the Service has been established, with the IBinder of the communication channel to the Service.
    private val boundServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binderBridge = service as MyBinder
            boundService = binderBridge.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
            boundService = null
        }
    }


}

