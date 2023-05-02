package com.android.treespotter

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.GeoPoint
import java.util.Date

data class Tree(
    val name: String? = null,
    val dateSpotted: Date? = null,
    val location: GeoPoint? = null,
    val favorite: Boolean? = null,


    @get:Exclude @set:Exclude var documentReference: DocumentReference? = null
)