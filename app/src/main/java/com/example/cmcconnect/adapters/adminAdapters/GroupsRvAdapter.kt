package com.example.cmcconnect.adapters.adminAdapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cmcconnect.R
import com.example.cmcconnect.model.GroupeDto
import com.example.cmcconnect.model.RessourceDto

class GroupsRvAdapter(private val navController: NavController) : RecyclerView.Adapter<GroupsRvAdapter.AdminGroupsViewHolder>() {
    private var allGroups: List<GroupeDto> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminGroupsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_formateur_groups_item, parent, false)
        return AdminGroupsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdminGroupsViewHolder, position: Int) {
        val group = allGroups[position]
        holder.bind(group)
    }

    override fun getItemCount(): Int {
        return allGroups.size ?: 0
    }

    fun submitList(newGroup : List<GroupeDto>){
        allGroups = newGroup
        notifyDataSetChanged()
    }


    inner class AdminGroupsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon : ImageView = itemView.findViewById(R.id.image)
        private val groupName: TextView = itemView.findViewById(R.id.group_name)
        private val viewButton: Button = itemView.findViewById(R.id.show_btn)
        fun bind(group: GroupeDto) {
            groupName.text = group.name
            viewButton.setOnClickListener {

                val bundle = Bundle().apply {
                    putSerializable("clickedGroup", group)
                }
                navController.navigate(R.id.action_id_filiereFragment_to_studentsByGroupeFragment,bundle)
            }
        }
    }
}