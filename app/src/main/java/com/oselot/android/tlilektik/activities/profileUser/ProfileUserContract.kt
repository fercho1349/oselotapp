package com.oselot.android.tlilektik.activities.profileUser

class ProfileUserContract {

    interface PresenterInterface {
        fun addProfileUser(username: String, name: String, lastname: String, description: String)
    }

    interface ViewInterface {
        fun showToast(string: String)
        fun navigationToGallery()
    }

}