package com.example.cmcconnect.adapters.teacherAdapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cmcconnect.R
import com.example.cmcconnect.formateur.formateurSeeRequests.OnStudentRequestReplyListener
import com.example.cmcconnect.model.RequestWithStudent
import com.example.cmcconnect.model.UserInInfo

class SeeRequestsAdapter(private val studentRequestReplyListener: OnStudentRequestReplyListener) : RecyclerView.Adapter<SeeRequestsAdapter.SeeRequestsViewHolder>() {
    private var requests: List<RequestWithStudent> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeeRequestsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_formateur_requests_item, parent, false)
        return SeeRequestsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SeeRequestsViewHolder, position: Int) {
        val requestWithStudent = requests[position]
        holder.bind(requestWithStudent)
    }

    override fun getItemCount(): Int {
        return requests.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newRequests: List<RequestWithStudent>) {
        this.requests = newRequests
        notifyDataSetChanged()
    }

    inner class SeeRequestsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.student_name)
        private val motif: TextView = itemView.findViewById(R.id.student_reason)
        private val btnReply: Button = itemView.findViewById(R.id.post_reply)

        fun bind(requestWithStudent: RequestWithStudent) {
            val request = requestWithStudent
            val student = request.student

            name.text = student.name
            motif.text = request.motif

            btnReply.setOnClickListener {
                showRequestPopUp(student.name, student.groupe?.name, request.motif, student.id, request.id)
            }
        }

        private fun showRequestPopUp(studentName: String, groupName: String?, requestMotif: String, studentId: Int, requestId: Int) {
            val inflater = LayoutInflater.from(itemView.context)
            val dialogView = inflater.inflate(R.layout.formateur_request_popup, null)

            val builder = AlertDialog.Builder(itemView.context)
            builder.setView(dialogView)

            val alertDialog = builder.create()

            val widthInDp = 500
            val heightInDp = 220

            val widthInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, widthInDp.toFloat(), itemView.context.resources.displayMetrics).toInt()
            val heightInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, heightInDp.toFloat(), itemView.context.resources.displayMetrics).toInt()

            alertDialog.window?.setLayout(widthInPx, heightInPx)
            alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            val exitBtn = dialogView.findViewById<ImageButton>(R.id.exit_button)
            val studentNameTextView = dialogView.findViewById<TextView>(R.id.student_name)
            val studentGroupTextView = dialogView.findViewById<TextView>(R.id.group_name)
            val motifTextView = dialogView.findViewById<TextView>(R.id.reason)
            val reply = dialogView.findViewById<EditText>(R.id.note_input)
            val btnSend = dialogView.findViewById<Button>(R.id.post_reply)

            studentNameTextView.text = studentName
            studentGroupTextView.text = groupName ?: "Unknown"
            motifTextView.text = requestMotif

            exitBtn.setOnClickListener {
                alertDialog.dismiss()
            }

            btnSend.setOnClickListener {
                val response = reply.text.toString()
                if (response.isNotEmpty()) {
                    studentRequestReplyListener.onStudentRequestReply(response, UserInInfo.id, studentId, requestId)
                }
                alertDialog.dismiss()
            }

            alertDialog.show()
        }
    }
}

