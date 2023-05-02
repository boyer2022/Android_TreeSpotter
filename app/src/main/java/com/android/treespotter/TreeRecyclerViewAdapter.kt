package com.android.treespotter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

        // Alt+Enter to "Implement Members" with all 3 choices. Creates 3 placeholders
class TreeRecyclerViewAdapter(var trees: List<Tree>, val treeHeartListener: (Tree, Boolean) -> Unit ):
        // Constructor indicated by the parenthesis
        RecyclerView.Adapter<TreeRecyclerViewAdapter.ViewHolder>() {
            inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
                fun bind(tree: Tree) {
                    view.findViewById<TextView>(R.id.tree_name).text = tree.name
                    view.findViewById<TextView>(R.id.date_spotted).text = "${tree.dateSpotted}"
                    view.findViewById<CheckBox>(R.id.heart_check).apply {
                        isChecked = tree.favorite ?: false
                        setOnCheckedChangeListener { checkbox, isChecked ->
                            // Need to talk to Firebase
                            treeHeartListener(tree, isChecked)
                        }
                    }
                }
            }

// Be able to create the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_tree_list_item, parent, false)
        return ViewHolder(view)
    }

// Know how many items you have
    override fun getItemCount(): Int {
        return trees.size
    }

// Be able to connect data to ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tree = trees[position]
    holder.bind(tree)
    }
}