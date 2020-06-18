package com.example.dynamicdata

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class StudentsAdapter : BaseQuickAdapter<Student, BaseViewHolder>(R.layout.item_student) {

    override fun convert(holder: BaseViewHolder, item: Student) {
        holder.setText(R.id.name, item.name)
            .setText(R.id.age, item.age.toString())
    }

    override fun convert(holder: BaseViewHolder, item: Student, payloads: List<Any>) {
        holder.setText(R.id.name, item.name)
            .setText(R.id.age, item.age.toString())

    }
}