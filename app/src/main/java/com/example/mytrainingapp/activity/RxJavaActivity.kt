package com.example.mytrainingapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mytrainingapp.R
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.xml.datatype.DatatypeConstants.SECONDS
import android.R.attr.delay
import io.reactivex.schedulers.Schedulers.io
import android.annotation.SuppressLint
import io.reactivex.internal.disposables.DisposableHelper.isDisposed
import androidx.work.ListenableWorker.Result.retry
import android.os.Looper
import android.os.Handler
import android.view.View
import com.example.mytrainingapp.models.RxJavaPerson
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.exceptions.Exceptions
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_rx_java.*
import java.util.*
import java.util.concurrent.TimeUnit


class RxJavaActivity : AppCompatActivity() {

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_java)
        handler = Handler(Looper.getMainLooper())

    }

    /**
     * Combines the emissions of two observables. The concat operator waits for one observable to complete before emitting values from the second.
     * http://reactivex.io/documentation/operators/concat.html
     *
     * @param view
     */

    @SuppressLint("CheckResult")
    fun onStartConcat(view: View) {
        val observable1 = Observable.just(1, 2, 3, 4)
        val observable2 = Observable.just(5, 6, 7, 8)

        Observable.concat(observable1, observable2)
            .subscribeOn(io())
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ integerList ->
                Thread {
                    for (integer in integerList) {
                        try {
                            Thread.sleep(1000)
                            handler.post { tvEmissions.text = integer.toString() }
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }

                    }
                }.start()
            }) { it.printStackTrace() }
    }

    /**
     * Combines the emission of two observables. The merge operator may interleave the emissions of both observables. Order is not guaranteed.
     * http://reactivex.io/documentation/operators/merge.html
     *
     * @param view
     */

    @SuppressLint("CheckResult")
    fun onStartMerge(view: View) {
        val observable1 = Observable.just(1, 2, 3, 4)
        val observable2 = Observable.just(5, 6, 7, 8)

        Observable.merge(observable1, observable2)
            .subscribeOn(io())
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ integerList ->
                Thread {
                    for (integer in integerList) {
                        try {
                            Thread.sleep(1000)
                            handler.post { tvEmissions.text = integer.toString() }
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }

                    }
                }.start()
            }) { it.printStackTrace() }
    }

    /**
     * Combines the emissions of two observables, then emits them one by one after a 1 second delay. ConcatMap maintains the order of the emitted values.
     * http://reactivex.io/documentation/operators/concat.html
     *
     * @param view
     */

    @SuppressLint("CheckResult")
    fun onStartSwitchMap(view: View) {
        val observable1 = Observable.just(1, 2, 3, 4)
        val observable2 = Observable.just(5, 6, 7, 8)

        Observable.concat(observable1, observable2)
            .subscribeOn(io())
            .observeOn(io())
            .concatMap { integer -> Observable.just(integer).delay(1, TimeUnit.SECONDS) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { integer -> tvEmissions.text = integer.toString() },
                { it.printStackTrace() },
                { Toast.makeText(this, "Completed", Toast.LENGTH_SHORT).show() })
    }

    /**
     * Combines the emissions of two observables, then emits them one by one after a 1 second delay. This is very similar to concatMap above, but may result
     * in interleaved values. Order is not guaranteed.
     * http://reactivex.io/documentation/operators/flatmap.html
     *
     * @param view
     */

    @SuppressLint("CheckResult")
    fun onStartFlatMap(view: View) {
        val observable1 = Observable.just(1, 2, 3, 4)
        val observable2 = Observable.just(5, 6, 7, 8)

        /*Observable.concat(observable1, observable2)
            .subscribeOn(io())
            .observeOn(io())
            .flatMap { Observable.just(it) }
            .zipWith(Observable.interval(1, TimeUnit.SECONDS), BiFunction { n: Int, _: Int -> n })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { tvEmissions.setText(it) }, { it.printStackTrace() },
                { Toast.makeText(this, "Completed", Toast.LENGTH_SHORT).show() })*/
    }

    /**
     * Instantiates two Person objects each with a list of friends, emitting each friend downstream to the observer.
     * http://reactivex.io/documentation/operators/flatmap.html
     *
     * @param view
     */

    @SuppressLint("CheckResult")
    fun onStartFlatMapList(view: View) {
        val person1Friends = mutableListOf("jason", "robby", "jones", "david")
        val person2Friends = mutableListOf("larry", "bobby", "jerry", "yoMomma")
        val personList = mutableListOf(
            RxJavaPerson(name = "Janice", friends = person1Friends),
            RxJavaPerson(name = "Felicia", friends = person2Friends)
        )

        Observable.fromIterable(personList)
            .subscribeOn(io())
            .observeOn(io())
            .flatMap { person -> Observable.fromIterable(person.friends) }
            .observeOn(AndroidSchedulers.mainThread())
            .toList()
            .subscribe({ friendList ->
                val sb = StringBuilder()
                for (friend in friendList) {
                    sb.append(friend)
                    sb.append(", ")
                }
                tvEmissions.text = sb.toString()
            }) { it.printStackTrace() }
    }

    /**
     * Filters the emissions from the source observable, and then maps each value that passes the predicate test to a string appended with "mapped".
     * http://reactivex.io/documentation/operators/filter.html
     * http://reactivex.io/documentation/operators/map.html
     *
     * @param view
     */

    @SuppressLint("CheckResult")
    fun onStartFilterAndMap(view: View) {
        val alphabet = arrayOf(
            "a",
            "b",
            "c",
            "d",
            "e",
            "f",
            "g",
            "h",
            "i",
            "j",
            "k",
            "l",
            "m",
            "n",
            "o",
            "p",
            "q",
            "r",
            "s",
            "t",
            "u",
            "v",
            "w",
            "x",
            "y",
            "z"
        )
        Observable.fromArray(*alphabet)
            .subscribeOn(io())
            .observeOn(io())
            .filter { letter -> letter == "a" || letter == "b" || letter == "c" }
            .map { "$it mapped" }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                val sb = StringBuilder()
                for (emission in list) {
                    sb.append(emission)
                    sb.append(" ")
                }
                tvEmissions.text = sb.toString()
            }) { it.printStackTrace() }
    }


    /**
     * Emits a random integer. If the integer is not divisible by 3, the retry operator resubscribes to the source observable.
     * http://reactivex.io/documentation/operators/retry.html
     *
     * @param view
     */

    @SuppressLint("CheckResult")
    fun onStartRetry(view: View) {

        Observable.create { emitter: ObservableEmitter<Int> ->
            val random = Random()
            val randomInt = random.nextInt(50) + 1

            if (randomInt % 3 != 0 && !emitter.isDisposed) {
                emitter.onError(Throwable("Number $randomInt is not divisible by 3"))
            } else {
                emitter.onNext(randomInt)
                emitter.onComplete()
            }

        }.subscribeOn(io())
            .doOnError { error -> tvEmissions.text = error.message }
            .observeOn(io())
            .delay(1, TimeUnit.SECONDS)
            .retry()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { integer -> tvEmissions.text = integer.toString() },
                { it.printStackTrace() },
                { Toast.makeText(this, "Completed!", Toast.LENGTH_SHORT).show() })

    }

    /**
     * Emits a random integer. If the integer is not divisible by 3, then we resubscribe three times before giving up. This is just like the retry operator, the only
     * difference being that we can specify our own retry logic.
     * https://blog.danlew.net/2016/01/25/rxjavas-repeatwhen-and-retrywhen-explained/
     *
     * @param view
     */

    @SuppressLint("CheckResult")
    fun onStartRetryWhen(view: View) {

        Observable.fromCallable {
            val random = Random()
            val randomInt = random.nextInt(50) + 1
            if (randomInt % 3 != 0) {
                throw Exception("Number $randomInt is not divisible by 3")
            } else {
                return@fromCallable randomInt
            }
        }.subscribeOn(io())
            .observeOn(io())
            .retryWhen { errors ->
                errors.zipWith(Observable.range(1, 3), BiFunction { n: Throwable, i: Int ->
                    tvEmissions.text = n.message
                    if (i == 3) {
                        Thread.sleep(1000)
                        throw Exceptions.propagate(n)
                    } else {
                        return@BiFunction i
                    }
                }).delay(1, TimeUnit.SECONDS)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ integer -> tvEmissions.text = integer.toString() },
                { tvEmissions.text = "Failed" },
                { Toast.makeText(this, "Completed!", Toast.LENGTH_SHORT).show() })

    }
}
