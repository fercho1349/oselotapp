package com.oselot.android.tlilektik.activities.profileUser

import com.oselot.android.tlilektik.models.LocalDataSource
import com.oselot.android.tlilektik.models.ProfileUser

class ProfileUserPresenter (private var viewInterface: ProfileUserContract.ViewInterface,
                            private var dataSource: LocalDataSource): ProfileUserContract.PresenterInterface {

    override fun addProfileUser(username: String, name: String, lastname: String, description: String) {
        if (username.isEmpty() || description.isEmpty()) {
            viewInterface.showToast("Ingrese los datos requeridos")
        } else {
            val profileUser = ProfileUser(username,name, lastname, description)
            dataSource.insert(profileUser)
            viewInterface.navigationToGallery()
        }
    }
}