package br.com.rads.rxchrono

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val chronoManager = ChronoManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chronoManager.addObserver(myObserver())
        chronoManager.startTimer()

    }

    var myDisposable: Disposable? = null
    private fun myObserver(): Observer<Long> {
        return object : Observer<Long>{
            override fun onComplete() {
                myDisposable?.dispose()
                chronoManager.addObserver(myObserver())
                chronoManager.startTimer()
            }

            override fun onSubscribe(d: Disposable) {
                myDisposable = d
            }

            override fun onNext(t: Long) {
                my_textview.text = t.toString()
            }

            override fun onError(e: Throwable) {
            }

        }
    }

}
