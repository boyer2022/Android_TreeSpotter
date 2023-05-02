package com.android.treespotter

import java.util.Date

data class Tree(val name: String? = null,
                val dateSpotted: Date? = null,
                val favorite: Boolean? = null
)