package com.trial.listsectionssample.core.platform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trial.listsectionssample.R
import com.trial.listsectionssample.core.networkError.RetrofitException
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()
    val error = MutableLiveData<Status.Error>()
    val loading = MutableLiveData<Boolean>()
    val logout = MutableLiveData<Boolean>()
    private fun handleError(exception: Throwable) {
        when (exception) {
            is RetrofitException -> {
                when (exception.getKind()) {
                    RetrofitException.Kind.NETWORK -> {
                        error.postValue(
                            Status.Error(
                                ErrorType.NoInternetConnection,
                                errorRes = R.string.error_no_connection
                            )
                        )
                    }
                    RetrofitException.Kind.HTTP -> {
                        exception.getResponse()?.let {
                            if (it.code() == 401) {
                                logout.postValue(true)
                                error.postValue(
                                    Status.Error(
                                        ErrorType.NetworkError,
                                        errorRes = R.string.unauthorized,
                                        code = it.code()
                                    )
                                )
                            } else {
                                val jsonObject = JSONObject(it.errorBody()?.string())
                                error.postValue(
                                    Status.Error(
                                        ErrorType.NetworkError,
                                        error = jsonObject.getString("message"),
                                        code = it.code()
                                    )
                                )
                            }
                        } ?: run {
                            error.postValue(
                                Status.Error(
                                    ErrorType.NetworkError,
                                    error = exception.message,
                                    code = exception.getResponse()?.code()
                                )
                            )
                        }
                    }
                    RetrofitException.Kind.UNEXPECTED -> {
                        error.postValue(
                            Status.Error(
                                ErrorType.NetworkError,
                                error = exception.message,
                                code = exception.getResponse()?.code()
                            )
                        )
                    }
                }
            }
            is ApplicationException -> {
                error.postValue(
                    Status.Error(
                        ErrorType.ValidationError,
                        error = exception.error.error,
                        errorRes = exception.error.errorRes
                    )
                )
            }
            else -> {
                // todo
            }
        }
    }

    fun <T> subscribeObservable(
        observable: Observable<T>,
        success: Consumer<T>,
        error: Consumer<Throwable> = Consumer { },
        subscribeScheduler: Scheduler = Schedulers.io(),
        observeOnMainThread: Boolean = true,
        showLoading: Boolean = true
    ) {
        val observerScheduler =
            if (observeOnMainThread) AndroidSchedulers.mainThread()
            else subscribeScheduler

        compositeDisposable.add(
            observable
                .subscribeOn(subscribeScheduler)
                .observeOn(observerScheduler)
                .compose { single ->
                    composeObservable<T>(single, showLoading)
                }
                .subscribe(success, error)
        )
    }

    fun subscribe(
        completable: Completable,
        success: Action,
        error: Consumer<Throwable> = Consumer { },
        subscribeScheduler: Scheduler = Schedulers.io(),
        observeOnMainThread: Boolean = true,
        showLoading: Boolean = true
    ) {
        val observerScheduler =
            if (observeOnMainThread) AndroidSchedulers.mainThread()
            else subscribeScheduler
        compositeDisposable.add(
            completable
                .subscribeOn(subscribeScheduler)
                .observeOn(observerScheduler)
                .compose { composeComplete(completable, showLoading) }
                .subscribe(success, error)
        )
    }

    fun <T> subscribe(
        single: Single<T>,
        success: Consumer<T>,
        error: Consumer<Throwable> = Consumer { },
        subscribeScheduler: Scheduler = Schedulers.io(),
        observeOnMainThread: Boolean = true,
        showLoading: Boolean = true
    ) {
        val observerScheduler =
            if (observeOnMainThread) AndroidSchedulers.mainThread()
            else subscribeScheduler

        compositeDisposable.add(
            single
                .subscribeOn(subscribeScheduler)
                .observeOn(observerScheduler)
                .compose { single ->
                    composeSingle<T>(single, showLoading)
                }
                .subscribe(success, error))
    }

    private fun <T> composeObservable(
        observable: Observable<T>,
        showLoading: Boolean
    ): Observable<T> {
        return observable
            .flatMap { item ->
                Observable.just(item)
            }
            .doOnError {
                Timber.e(it)
                handleError(it)
            }
            .doOnSubscribe {
                loading.postValue(showLoading)
            }
            .doAfterTerminate {
                loading.postValue(false)
            }
    }

    private fun <T> composeSingle(single: Single<T>, showLoading: Boolean = true): Single<T> {
        return single
            .flatMap { item ->
                Single.just(item)
            }
            .doOnError {
                Timber.e(it)
                handleError(it)
            }
            .doOnSubscribe {
                loading.postValue(showLoading)
            }
            .doAfterTerminate {
                loading.postValue(false)
            }
    }

    private fun composeComplete(
        completable: Completable,
        showLoading: Boolean = true
    ): Completable {
        return completable.doOnError {
            Timber.e(it)
            handleError(it)
        }.doOnSubscribe {
            loading.postValue(showLoading)
        }.doAfterTerminate {
            loading.postValue(false)
        }
    }

    fun clearSubscription() {
        if (compositeDisposable.isDisposed.not()) compositeDisposable.clear()
    }

    override fun onCleared() {
        clearSubscription()
        super.onCleared()
    }
}