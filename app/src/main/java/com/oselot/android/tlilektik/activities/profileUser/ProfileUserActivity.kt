package com.oselot.android.tlilektik.activities.profileUser

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.oselot.android.tlilektik.R
import com.oselot.android.tlilektik.activities.gallery.GalleryActivity
import com.oselot.android.tlilektik.models.LocalDataSource
import kotlinx.android.synthetic.main.activity_profile_user.*

class ProfileUserActivity: AppCompatActivity(), ProfileUserContract.ViewInterface, View.OnClickListener {

    private lateinit var profileUserPresenter: ProfileUserContract.PresenterInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)
        setupPresenter()
        buttonSave.setOnClickListener(this)
    }

    private fun setupPresenter() {
        val dataSource = LocalDataSource(application)
        profileUserPresenter = ProfileUserPresenter(this, dataSource)
    }

    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show()
    }

    override fun navigationToGallery() {
        startActivity(Intent(this, GalleryActivity::class.java))
        finish()
    }

    override fun onClick(v: View?) {
        profileUserPresenter.addProfileUser(etxtUsername.text.toString(), etxtName.text.toString(),
                etxtLastname.text.toString(), etxtDescription.text.toString())
    }


}