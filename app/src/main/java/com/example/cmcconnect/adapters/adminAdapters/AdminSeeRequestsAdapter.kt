import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.cmcconnect.R
import com.example.cmcconnect.admin.adminSeeRequests.OnStudentRequestsForAdminListener
import com.example.cmcconnect.model.RequestWithStudent
import com.example.cmcconnect.model.UserInInfo
import java.util.*

class AdminSeeRequestsAdapter(private val onStudentRequestForAdminReplyListener: OnStudentRequestsForAdminListener)
    : RecyclerView.Adapter<AdminSeeRequestsAdapter.AdminSeeRequestsViewHolder>() {
    private var requests: List<RequestWithStudent> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminSeeRequestsAdapter.AdminSeeRequestsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_admin_requests_item, parent, false)
        return AdminSeeRequestsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdminSeeRequestsAdapter.AdminSeeRequestsViewHolder, position: Int) {
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

    inner class AdminSeeRequestsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), DatePickerDialog.OnDateSetListener {
        private val typeRequestTv: TextView = itemView.findViewById(R.id.request_type)
        private val studentName: TextView = itemView.findViewById(R.id.student_name)
        private val studentGroup: TextView = itemView.findViewById(R.id.student_group)
        private val motif: TextView = itemView.findViewById(R.id.student_reason)
        private val btnReply: Button = itemView.findViewById(R.id.post_reply)
        private lateinit var dateInput: TextView

        fun bind(requestWithStudent: RequestWithStudent) {
            typeRequestTv.text = requestWithStudent.type_request.name
            studentName.text = requestWithStudent.student.name
            studentGroup.text = requestWithStudent.student.groupe?.name
            motif.text = requestWithStudent.motif

            btnReply.setOnClickListener {
                showRequestPopUp(
                    requestWithStudent.type_request.name,
                    requestWithStudent.student.name,
                    requestWithStudent.student.groupe?.name,
                    requestWithStudent.motif,
                    requestWithStudent.student.id,
                    requestWithStudent.id
                )
            }
        }

        private fun showRequestPopUp(typeRequest: String, studentName: String, groupName: String?, requestMotif: String, studentId: Int, requestId: Int) {
            val inflater = LayoutInflater.from(itemView.context)
            val dialogView = inflater.inflate(R.layout.admin_request_popup, null)

            val builder = AlertDialog.Builder(itemView.context)
            builder.setView(dialogView)

            val alertDialog = builder.create()

            val widthInDp = 500
            val heightInDp = 220

            val widthInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, widthInDp.toFloat(), itemView.context.resources.displayMetrics).toInt()
            val heightInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, heightInDp.toFloat(), itemView.context.resources.displayMetrics).toInt()

            alertDialog.window?.setLayout(widthInPx, heightInPx)
            alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            val exitBtn: ImageButton = dialogView.findViewById(R.id.exit_button)
            val requestTypeTextView: TextView = dialogView.findViewById(R.id.request_type)
            val studentNameTextView = dialogView.findViewById<TextView>(R.id.student_name)
            val studentGroupTextView = dialogView.findViewById<TextView>(R.id.group_name)
            val motifTextView = dialogView.findViewById<TextView>(R.id.reason)
            val calendarIcon: ImageView = dialogView.findViewById(R.id.calendar_icon)
            dateInput = dialogView.findViewById(R.id.date_input)
            val clearText: ImageView = dialogView.findViewById(R.id.cancel_icon)
            val btnReply: Button = dialogView.findViewById(R.id.post_reply)

            requestTypeTextView.text = typeRequest
            studentNameTextView.text = studentName
            studentGroupTextView.text = groupName
            motifTextView.text = requestMotif

            calendarIcon.setOnClickListener {
                showDatePickerDialog()
            }

            clearText.setOnClickListener {
                dateInput.text = ""
            }

            exitBtn.setOnClickListener {
                alertDialog.dismiss()
            }

            btnReply.setOnClickListener {
                val response = dateInput.text.toString()
                if (response.isNotEmpty()) {
                    onStudentRequestForAdminReplyListener.onStudentRequestForAdminReply(response, UserInInfo.id, studentId, requestId)
                }
                alertDialog.dismiss()
            }

            alertDialog.show()
        }

        private fun showDatePickerDialog() {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(itemView.context, this, year, month, day)
            datePickerDialog.show()
        }

        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            dateInput.text = selectedDate
        }
    }
}
