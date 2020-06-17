package com.example.dynamicdata

import androidx.recyclerview.widget.DiffUtil

class StudentDiff(val students: List<Student>, val oldStudents: List<Student>) : DiffUtil.Callback(){

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return students[newItemPosition].name == oldStudents[oldItemPosition].name
    }

    override fun getOldListSize(): Int {
        return oldStudents.size
    }

    override fun getNewListSize(): Int {
        return students.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldStudent: Student = oldStudents.get(oldItemPosition)
        val newStudent: Student = students.get(newItemPosition)

        var same = true

        same = oldStudent.name == newStudent.name && same
        same = same && oldStudent.age == newStudent.age

        return same
    }

}