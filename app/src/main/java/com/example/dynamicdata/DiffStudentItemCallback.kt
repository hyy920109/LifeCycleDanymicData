package com.example.dynamicdata

import androidx.recyclerview.widget.DiffUtil

class DiffStudentItemCallback : DiffUtil.ItemCallback<Student>() {
    override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
        var same = true

        same = oldItem.name == newItem.name && same
        same = same && oldItem.age == newItem.age

        return same
    }

    //如果要精确打到item的某个属性改变了 那么可以实现这个
    override fun getChangePayload(oldItem: Student, newItem: Student): Any? {
        return null
    }
}