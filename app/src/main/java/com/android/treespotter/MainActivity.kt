package com.android.treespotter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

private const val TAG = "MAIN_ACTIVITY"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showFragment("MAP")

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.show_map -> {
                    showFragment("MAP")
                    true
                }
                R.id.show_list-> {
                    showFragment("LIST")
                    true
                } else -> {
                    false
                }
            }
        }
    }
    // Function to show either the map or the list
    private fun showFragment(tag: String) {
        // If we are not seeing the fragment with the given tag, display it
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            val transaction = supportFragmentManager.beginTransaction()
            when (tag) {
                "MAP" -> transaction.replace(R.id.fragmentContainerView, TreeMapFragment.newInstance(), "MAP")
                "LIST" -> transaction.replace(R.id.fragmentContainerView, TreeListFragment.newInstance(), "LIST")
            }
            // Makes the changes
            transaction.commit()
        }
    }
}