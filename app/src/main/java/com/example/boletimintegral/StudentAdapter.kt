package com.example.boletimintegral

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.boletimintegral.databinding.ItemResMessageBinding
import com.example.boletimintegral.databinding.ItemResStudentBinding
import com.example.boletimintegral.model.DataStudent

class StudentAdapter(val students : MutableList<DataStudent>) :
    RecyclerView.Adapter<
            StudentAdapter.StudentViewHolder
            >(){

    inner class StudentViewHolder(itemView : ItemResStudentBinding)
        : RecyclerView.ViewHolder(itemView.root){

        val nameStudent : TextView = itemView.studentName
        val schoolStudent : TextView = itemView.studentSchool


            fun bind(fullName : String, school: String){

                nameStudent.text = fullName
                schoolStudent.text = school

            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemInflate = ItemResStudentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return StudentViewHolder(itemView = itemInflate)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {

        holder.bind(
            students[position].fullName,
            students[position].school
        )
    }
}