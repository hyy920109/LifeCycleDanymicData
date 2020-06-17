package com.example.dynamicdata

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dynamicdata.databinding.FragmentMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {

    private val firstLifeCycle: MainLifeCycle by lazy {
        MainLifeCycle()
    }

    private val adapter: StudentsAdapter by lazy {
        StudentsAdapter()
    }

    private lateinit var mBinding: FragmentMainBinding
    private val mDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(firstLifeCycle)

    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentMainBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mDisposable.clear()
        Log.d(TAG, "onDestroyView: ")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initData()
        ensureSubScribe()
    }

    private fun initData() {
        adapter.setList(produceTestData())
    }

    private fun initView() {
        mBinding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        mBinding.rvStudents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@MainFragment.adapter
        }
    }


    private fun ensureSubScribe() {
        val subscribe = firstLifeCycle.observeDataResource
            .delay(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                updateStudentsList(it)
            }
            .subscribe()
        mDisposable.add(subscribe)
    }

    private fun updateStudentsList(students: List<Student>) {
        students.apply {
            val oldStudents = adapter.data
            val result = DiffUtil.calculateDiff(StudentDiff(this, oldStudents), false)
            adapter.setList(students)
            result.dispatchUpdatesTo(adapter)
        }
    }

    private fun produceTestData() : List<Student> {
        val students = ArrayList<Student>()
        for (i in 0..10) {
            students.add(Student(name = "hyy$i", age = i * Random.nextInt(3)))
        }
        return students
    }

    companion object {
        const val TAG = "FirstFragment"
    }
}