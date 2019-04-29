package com.builder.microsoftassistant

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val assistant = Settings.Secure.getString(
            contentResolver,
            "voice_interaction_service"
        )
        val screenText = intent.getStringExtra("DATA")
        if(!TextUtils.isEmpty(screenText)) {
            screenDataText.text = screenText
        }

        var areWeGood = false

        if (assistant != null) {
            val cn = ComponentName.unflattenFromString(assistant)

            if (cn!!.packageName == packageName) {
                areWeGood = true
            }
        }

        if (areWeGood) {
            Toast
                .makeText(this, "Assistant Active", Toast.LENGTH_LONG)
                .show()
            fab.isEnabled = false
        } else {
            Toast
                .makeText(this, "Assistant is Down :-(", Toast.LENGTH_LONG)
                .show()

            fab.isEnabled = true
        }
        fab.setOnClickListener { view ->
            startActivityForResult(Intent(Settings.ACTION_VOICE_INPUT_SETTINGS), 123)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
