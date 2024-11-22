package com.example.cmcconnect.adapters.adminAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cmcconnect.R
import com.example.cmcconnect.model.TeacherDto

class TeachersRvAdapter: RecyclerView.Adapter<TeachersRvAdapter.TeacherViewHolder>(){
    private var allTeachers : List<TeacherDto> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_admin_teachers, parent, false)
        return TeacherViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return allTeachers.size ?: 0
    }

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        val teacher = allTeachers[position]
        holder.bind(teacher)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newTeachers : List<TeacherDto>){
        allTeachers = newTeachers
        notifyDataSetChanged()

    }
    inner class TeacherViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageView : ImageView = itemView.findViewById(R.id.img_teacher)
        val nameTeacher : TextView = itemView.findViewById(R.id.teacher_name)
        val assignBtn : TextView = itemView.findViewById(R.id.assing_btn)
        val editBtn : ImageView = itemView.findViewById(R.id.edit_bnt)
        val deleteBtn : ImageView = itemView.findViewById(R.id.delete_btn)
        fun bind(teacher:TeacherDto){
            nameTeacher.text = teacher.name

        }
    }


}