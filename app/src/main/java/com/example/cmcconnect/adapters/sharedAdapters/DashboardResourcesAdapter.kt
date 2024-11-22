package com.example.cmcconnect.adapters.sharedAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cmcconnect.R
import com.example.cmcconnect.model.RessourceDto

class DashboardResourcesAdapter: RecyclerView.Adapter<DashboardResourcesAdapter.DashboardResourcesViewHolder>() {
    private var recentStudentResources: List<RessourceDto> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardResourcesAdapter.DashboardResourcesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_recent_resources_item, parent, false
        )
        return DashboardResourcesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DashboardResourcesAdapter.DashboardResourcesViewHolder, position: Int) {
        val recentStudentResources = recentStudentResources[position]
        if (recentStudentResources != null) {
            holder.bind(recentStudentResources)
        }
    }

    override fun getItemCount(): Int {
        return recentStudentResources.size ?: 0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(recentStudentResource: List<RessourceDto>) {
        recentStudentResources = recentStudentResource
        notifyDataSetChanged()
    }

    inner class DashboardResourcesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val resourceIcon: ImageView = itemView.findViewById(R.id.ressource_icon)
        private val resourceTitle: TextView = itemView.findViewById(R.id.ressource_title)
        private val resourcePubDate: TextView = itemView.findViewById(R.id.ressource_upload_date)

        fun bind(resourceDto: RessourceDto) {
            if (resourceDto.type == "pdf") {
                resourceIcon.setImageResource(R.drawable.icon_pdf)
            } else if (resourceDto.type == "video") {
                resourceIcon.setImageResource(R.drawable.icon_video)
            }
            resourceTitle.text = resourceDto.title
            resourcePubDate.text = resourceDto.pubDate.toString()
        }
    }
}