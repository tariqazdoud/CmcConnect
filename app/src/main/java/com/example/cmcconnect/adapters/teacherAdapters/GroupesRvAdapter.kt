package com.example.cmcconnect.adapters.teacherAdapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cmcconnect.R
import com.example.cmcconnect.formateur.formateurGroups.GroupsFragment
import com.example.cmcconnect.model.GroupeDto
import com.example.cmcconnect.model.Item

class GroupesRvAdapter(private val navController: NavController, private val items: List<Item>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            GroupsFragment.ItemType.HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.rv_item_header, parent, false)
                HeaderViewHolder(view)
            }

            GroupsFragment.ItemType.GROUP -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.rv_formateur_groups_item, parent, false)
                GroupsViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is HeaderViewHolder -> holder.bind(item)
            is GroupsViewHolder -> holder.bind(item.group!!)
        }
    }

    override fun getItemCount(): Int = items.size


    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val headerTitle: TextView = itemView.findViewById(R.id.headerTitle)
        fun bind(item: Item) {
            headerTitle.text = item.name
        }
    }

    inner class GroupsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val groupName: TextView = itemView.findViewById(R.id.group_name)
        private val viewButton: Button = itemView.findViewById(R.id.show_btn)
        fun bind(group: GroupeDto) {
            groupName.text = group.name
            viewButton.setOnClickListener {

                val bundle = Bundle().apply {
                    putSerializable("clickedGroup", group)
                }
                navController.navigate(R.id.id_groupeStudentsFragment,bundle)
            }

        }

    }
}