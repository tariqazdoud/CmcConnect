package com.example.cmcconnect.adapters.adminAdapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cmcconnect.R
import com.example.cmcconnect.model.JustifWithStudent
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment

class SeeJustifsAdapter: RecyclerView.Adapter<SeeJustifsAdapter.SeeJustifsViewHolder>() {
    private var justifs: List<JustifWithStudent> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeeJustifsAdapter.SeeJustifsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_admin_justifs_item, parent, false)
        return SeeJustifsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SeeJustifsAdapter.SeeJustifsViewHolder, position: Int) {
        val justifWithStudent = justifs[position]
        holder.bind(justifWithStudent)
    }

    override fun getItemCount(): Int {
        return justifs.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newJustif: List<JustifWithStudent>) {
        this.justifs = newJustif
        notifyDataSetChanged()
    }

    inner class SeeJustifsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.student_name)
        private val motif: TextView = itemView.findViewById(R.id.motif_absence)
        private val btnSeeDetails: Button = itemView.findViewById(R.id.btn_SeeDetails)

        fun bind(justifWithStudent: JustifWithStudent) {
            val justif = justifWithStudent
            val student = justif.student

            name.text = student.name
            motif.text = justif.motif

            btnSeeDetails.setOnClickListener {
                showPopUpitem(student.name, student.groupe?.name, justif.motif, justif.file)
            }
        }

        fun showPopUpitem(student: String, group: String?, motif: String, file: String) {
            val inflater = LayoutInflater.from(itemView.context)
            val dialogView = inflater.inflate(R.layout.justifs_popup, null)

            val builder = AlertDialog.Builder(itemView.context)
            builder.setView(dialogView)

            val alertDialog = builder.create()

            val widthInDp = 500
            val heightInDp = 220

            val widthInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, widthInDp.toFloat(), itemView.context.resources.displayMetrics).toInt()
            val heightInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, heightInDp.toFloat(), itemView.context.resources.displayMetrics).toInt()

            alertDialog.window?.setLayout(widthInPx, heightInPx)
            alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            val exitIcon: ImageView = dialogView.findViewById(R.id.exit)
            val studentNameTv: TextView = dialogView.findViewById(R.id.name_student)
            val studentGroupTv: TextView = dialogView.findViewById(R.id.group_student)
            val motifTv: TextView = dialogView.findViewById(R.id.justif_motif_admin)
            val justifTitleTv: TextView = dialogView.findViewById(R.id.title)
            val downloadIcon: ImageView = dialogView.findViewById(R.id.downloadIcon)

            studentNameTv.text = student
            studentGroupTv.text = group
            motifTv.text = motif

            val fileName = file.substring(file.lastIndexOf("/") + 1)
            justifTitleTv.text = fileName

            exitIcon.setOnClickListener {
                alertDialog.dismiss()
            }

            downloadIcon.setOnClickListener {
                downloadFile(file, fileName)
            }

            alertDialog.show()
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