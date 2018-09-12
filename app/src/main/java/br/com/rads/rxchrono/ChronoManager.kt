package br.com.rads.rxchrono

import android.os.CountDownTimer
import android.util.Log
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.*

class ChronoManager {

    var publishSubject : PublishSubject<Long>? = null

    private val countDownTimer: CountDownTimer = object : CountDownTimer(5000, 1000) {
        override fun onFinish() {
            Log.d("TIMER", "finished")
            callback?.invoke()
            publishSubject?.onComplete()
        }

        override fun onTick(p0: Long) {
            Log.d("TIMER", "tick $p0")
            publishSubject?.onNext(p0)
        }
    }

    private var callback: (() -> Unit?)? = null

    fun addObserver(obs: Observer<Long>) {
        publishSubject = PublishSubject.create<Long>().apply {
                observeOn(AndroidSchedulers.mainThread())
                subscribeOn(Schedulers.io())
                repeat()
                subscribe(obs)
        }
    }

    fun startTimer(onFinish: (() -> Unit)?) {
        callback = onFinish
        countDownTimer.start()
    }

    fun startTimer() {
        countDownTimer.start()
    }
}