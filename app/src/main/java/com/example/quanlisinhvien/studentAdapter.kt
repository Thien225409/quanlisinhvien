package com.example.quanlisinhvien

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class studentAdapter (
    context: Context,
    private val students: MutableList<Student>
): ArrayAdapter<Student>(context, 0, students) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_student, parent, false)
        val student = students[position]

        view.findViewById<TextView>(R.id.tvName).text = student.name
        view.findViewById<TextView>(R.id.tvMSSV).text = "MSSV: ${student.mssv}"
        view.findViewById<TextView>(R.id.tvEmail).text = "Email: ${student.email}"
        view.findViewById<TextView>(R.id.tvSdt).text = "SĐT: ${student.phone}"

        return view
    }


}