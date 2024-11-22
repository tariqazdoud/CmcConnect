package com.example.cmcconnect.adapters.teacherAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cmcconnect.R
import com.example.cmcconnect.model.StudentDto

class StudentsGroupRvAdapter : RecyclerView.Adapter<StudentsGroupRvAdapter.StudentsViewHolder>(){
    private var allStudents : List<StudentDto> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_formateur_students_item,parent,false)
        return StudentsViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: StudentsViewHolder, position: Int) {
        val student = allStudents[position]
        holder.bind(student)
    }
    override fun getItemCount(): Int {
        return allStudents.size ?: 0
    }
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newStudents : List<StudentDto>){
        allStudents = newStudents
        notifyDataSetChanged()
    }


    inner class StudentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val studentName : TextView = itemView.findViewById(R.id.student_name)
        fun bind(student : StudentDto){
            studentName.text = student.name
        }
    }
}