package com.example.dynamicdata

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.*
import kotlin.concurrent.timerTask
import kotlin.random.Random

class MainLifeCycle : LifecycleObserver {

    private var timer: Timer? = null
    private val mDataResource = BehaviorSubject.create<List<Student>>()

    val observeDataResource: @NonNull Observable<List<Student>>
        get() = mDataResource.hide()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Log.d(TAG, "onCreate: ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.d(TAG, "onStart: ")
        //init timer
        timer = Timer("MainLifeCycle")
    }
    
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.d(TAG, "onResume thread : ${Thread.currentThread().name}")
        timer?.schedule(timerTask {
            Log.d(TAG, "timer thread --> : ${Thread.currentThread().name}")
            updateData()
        },0,1000 )
    }

    private fun updateData() {
        Log.d(TAG, "updateData: ")
        mDataResource.onNext(produceTestData())

    }

    private fun produceTestData() : List<Student> {
        val students = ArrayList<Student>()
        for (i in 0..2) {
            students.add(Student(name = "hyy$i", age = i * Random.nextInt(3)))
        }
        return students
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.d(TAG, "onPause: ")

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Log.d(TAG, "onStop: ")
        timer?.cancel()
        timer = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Log.d(TAG, "onDestroy: ")

    }

    companion object {
         const val TAG = "MainLifeCycle"
    }

}