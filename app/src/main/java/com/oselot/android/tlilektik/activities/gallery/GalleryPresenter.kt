package com.oselot.android.tlilektik.activities.gallery

import androidx.annotation.NonNull
import com.oselot.android.tlilektik.models.LocalDataSource
import com.oselot.android.tlilektik.models.ProfileUser
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class GalleryPresenter(private var viewInterface: GalleryContract.ViewInterface,
                       private var dataSource: LocalDataSource): GalleryContract.PresenterInterface {

    private val compositeDisposable = CompositeDisposable()

    private val myNotesObservable: Observable<ProfileUser>
        get() = dataSource.allProfileUser


    private val observer: DisposableObserver<ProfileUser>
        get() = object : DisposableObserver<ProfileUser>() {

            override fun onNext(displayProfileUser: ProfileUser) {
                viewInterface.displayProfileUser(displayProfileUser)
            }

            override fun onError(@NonNull e: Throwable) {
                e.printStackTrace()
            }

            override fun onComplete() {}
        }

    override fun getProfileUser() {
        val myNotesDisposable = myNotesObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)

        compositeDisposable.add(myNotesDisposable)
    }

    override fun stop() {
        compositeDisposable.clear()
    }

}