package com.android.treespotter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

private const val TAG = "MAIN_ACTIVITY"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Making database
        val db = Firebase.firestore

        // Example data
//        val tree = mapOf("name" to "pine", "dateSpotted" to Date())
//        db.collection("trees").add(tree)
//
//        val tree2 = mapOf("name" to "oak", "dateSpotted" to Date())
//        db.collection("trees").add(tree2)

        // Making a Tree object
            // fromTree.kt
//        val tree = Tree("Pine", Date())
//        db.collection("trees").add(tree)

        // Getting all of the data on the DB
//        db.collection("trees").get().addOnSuccessListener { treeDocuments ->
        // Snapshot monitors all of the documents for changes and updates
        db.collection("trees")
            .whereEqualTo("name", "Pine")           // Filtering out values/documents.
            .whereEqualTo("favorite", true)         // Add as many as wanted
            .orderBy("dateSpotted", Query.Direction.DESCENDING)// Direction in descending order
            .limit(3)
            .addSnapshotListener { treeDocuments, error ->

            // Check for errors, no data
            if (error != null) {
                Log.e(TAG, "Error getting all trees.", error)
            }
            if (treeDocuments != null) {
                // Looping over each data set
                for (treeDoc in treeDocuments) {
                    val treeFromFirebase = treeDoc.toObject(Tree::class.java)
//                    val name = treeDoc["name"]
//                    val dateSpotted = treeDoc["dateSpotted"]
//                    val favorite = treeDoc["favorite"]
                    // Where is the path?
                    // Shows in Logcat the document id from Firestore
                    val path = treeDoc.reference.path

                    Log.d(TAG, "$treeFromFirebase, $path")
                }
            }
        }
    }
}