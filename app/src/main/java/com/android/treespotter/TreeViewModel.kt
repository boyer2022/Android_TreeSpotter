package com.android.treespotter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "TREE_VIEW_MODEL"
class TreeViewModel: ViewModel() {

    // Need to connect to Firebase
    private val db = Firebase.firestore
    private val treeCollectionReference = db.collection("trees")

    // Create Live data to store all of the trees
    val latestTrees = MutableLiveData<List<Tree>>()

    // Need to get all the trees sightings
    private val latestTreesListener = treeCollectionReference
        .orderBy("dateSpotted", Query.Direction.DESCENDING)
        .limit(10)
        .addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e(TAG, "Error fetching latest trees", error)
            } else if (snapshot != null) {
//                val trees = snapshot.toObjects(Tree::class.java)
/*
Instead of converting the entire list of documents directly to tree objects,
we are looping over the list of documents creating each document individually to a tree object.
Setting a reference the Firebase reference."Where is it in the Firebase database?" and adding that
tree to the list of trees.
 */
                val trees = mutableListOf<Tree>()
                for (treeDocument in snapshot) {
                    val tree = treeDocument.toObject(Tree::class.java)
                    tree.documentReference = treeDocument.reference
                    trees.add(tree)
                }

                Log.d(TAG, "Trees from firebase: $trees.")
                latestTrees.postValue(trees)
            }
        }

    fun setIsFavorite(tree: Tree, favorite: Boolean) {
        // Which tree is being updated?
        Log.d(TAG, "Updating tree $tree to favorite $favorite")
        tree.documentReference?.update("favorite", favorite)
    }
}