package com.example.cmcconnect.adapters.adminAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cmcconnect.R
import com.example.cmcconnect.model.AnsweredRequestsWithRequestDetails
import com.example.cmcconnect.model.RequestDto

class AdminSeeAnsweredRequestsAdapter: RecyclerView.Adapter<AdminSeeAnsweredRequestsAdapter.AdminSeeAnsweredRequestsViewHolder>() {
    private var answeredRequests: List<AnsweredRequestsWithRequestDetails> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminSeeAnsweredRequestsAdapter.AdminSeeAnsweredRequestsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_admin_answered_requests_item, parent, false)
        return AdminSeeAnsweredRequestsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdminSeeAnsweredRequestsAdapter.AdminSeeAnsweredRequestsViewHolder, position: Int) {
        val answeredRequest = answeredRequests[position]
        holder.bind(answeredRequest)
    }

    override fun getItemCount(): Int {
        return answeredRequests.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newAnsweredRequest: List<AnsweredRequestsWithRequestDetails>) {
        this.answeredRequests = newAnsweredRequest
        notifyDataSetChanged()
    }

    inner class AdminSeeAnsweredRequestsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val typeRequestTv: TextView = itemView.findViewById(R.id.request_type)
        private val studentNameTv: TextView = itemView.findViewById(R.id.student_name)
        private val studentGroupTv: TextView = itemView.findViewById(R.id.student_group)
        private val motif: TextView = itemView.findViewById(R.id.student_reason)
        private val date: TextView = itemView.findViewById(R.id.date_input)

        fun bind(request: AnsweredRequestsWithRequestDetails) {
            if (request.request.id_request_type_fk == 1) {
                typeRequestTv.text = "Demande de note"
            } else if (request.request.id_request_type_fk == 2) {
                typeRequestTv.text = "Demande de certificat de scolarité"
            } else if (request.request.id_request_type_fk == 3) {
                typeRequestTv.text = "Demande de retrait du baccalauréat"
            } else {
                typeRequestTv.text = "Demande de relevé de notes"
            }
            studentNameTv.text = request.student.name
            studentGroupTv.text = request.student.groupe?.name
            motif.text = request.request.motif
            date.text = request.response
        }
    }
}