package com.oselot.android.tlilektik.activities.gallery

import com.oselot.android.tlilektik.models.ProfileUser

class GalleryContract {

    interface PresenterInterface {
        fun getProfileUser()
        fun stop()
    }

    interface ViewInterface {
        fun displayProfileUser(profileUser: ProfileUser?)
        fun navigationToGalleryDetail()
    }

}