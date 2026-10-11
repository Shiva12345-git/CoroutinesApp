package com.mastercoding.coroutinesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.btn)
        val textView = findViewById<TextView>(R.id.textView)

        var counter = 0

        btn.setOnClickListener {
            counter++
            textView.text = counter.toString()

            // Run big download on IO Dispatcher via Coroutine
            CoroutineScope(Dispatchers.IO).launch {
                downloadBigFileFromNet()
            }
        }

        // Creating Coroutine Scope
        val mainScope = CoroutineScope(Dispatchers.Main)

        // Running Main Scope Coroutine
        mainScope.launch {
            Log.v("TAGY", "Using the ${coroutineContext}")

            // Switching to IO Dispatcher Temporarily
            withContext(Dispatchers.IO) {
                Log.v("TAGY", "Using the ${coroutineContext}")
                Log.v("TAGY", "The IO Thread")
                delay(1000)
            }

            // Switching back to Main Dispatcher
            Log.v("TAGY", "Using the ${coroutineContext}")
        }
    }

    private fun downloadBigFileFromNet() {
        for (i in 1..100) {
            Log.i("TAGY", "Downloading $i")
        }
    }
}
