package com.example.cmcconnect.adapters.studentAdapters

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cmcconnect.R
import com.example.cmcconnect.model.RessourceDto

class ResourcesRvAdapter : RecyclerView.Adapter<ResourcesRvAdapter.ResourcesViewHolder>() {

    private var allResources: List<RessourceDto> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourcesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_resource_item, parent, false)
        return ResourcesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ResourcesViewHolder, position: Int) {
        val resource = allResources[position]
        if (allResources != null) {
            holder.bind(resource)
        }
    }

    override fun getItemCount(): Int {
        return allResources.size ?: 0
    }
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newResources : List<RessourceDto>){
        allResources = newResources
        notifyDataSetChanged()
    }


    inner class ResourcesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon : ImageView = itemView.findViewById(R.id.icon)
        private val title : TextView = itemView.findViewById(R.id.title)
        private val date : TextView = itemView.findViewById(R.id.date)
        private val downloadIcon : ImageView = itemView.findViewById(R.id.downloadIcon)
        fun bind(resource : RessourceDto){
            if (resource.type == "pdf"){
                icon.setImageResource(R.drawable.icon_pdf)
            }else{
                icon.setImageResource(R.drawable.icon_video)
            }
            title.text = resource.title
            date.text = resource.pubDate.toString()
            val url = resource.file
            val fileName = url.substring(url.lastIndexOf("/") + 1)

            downloadIcon.setOnClickListener {
                downloadFile(url,fileName)
            }
        }
        private fun downloadFile(url: String, fileName: String) {
            val request = DownloadManager.Request(Uri.parse(url))
                .setTitle(fileName)
                .setDescription("Downloading $fileName")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

            val downloadManager = itemView.context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(request)
        }

    }


}