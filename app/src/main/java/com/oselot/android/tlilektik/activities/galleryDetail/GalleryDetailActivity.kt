package com.oselot.android.tlilektik.activities.galleryDetail

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.transition.Transition
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import com.oselot.android.tlilektik.R
import com.oselot.android.tlilektik.utils.Place
import com.oselot.android.tlilektik.utils.PlaceData
import kotlinx.android.synthetic.main.activity_photo_detail.*

class GalleryDetailActivity : AppCompatActivity() {

  companion object {
    const val EXTRA_PARAM_ID = "place_id"

    fun newIntent(context: Context, position: Int): Intent {
      val intent = Intent(context, GalleryDetailActivity::class.java)
      intent.putExtra(EXTRA_PARAM_ID, position)
      return intent
    }
  }

  private lateinit var inputManager: InputMethodManager
  private lateinit var place: Place

  private var isEditTextVisible: Boolean = false
  private var defaultColor: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_photo_detail)

    setupValues()
    loadPlace()
    windowTransition()
    getPhoto()
  }

  private fun setupValues() {
    place = PlaceData.placeList()[intent.getIntExtra(EXTRA_PARAM_ID, 0)]
    defaultColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
    inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    isEditTextVisible = false
  }

  private fun loadPlace() {
    placeTitle.text = place.name
    placeImage.setImageResource(place.getImageResourceId(this))
  }

  private fun windowTransition() {
    window.enterTransition.addListener(object : Transition.TransitionListener {
      override fun onTransitionEnd(transition: Transition) {
        window.enterTransition.removeListener(this)
      }

      override fun onTransitionResume(transition: Transition) { }
      override fun onTransitionPause(transition: Transition) { }
      override fun onTransitionCancel(transition: Transition) { }
      override fun onTransitionStart(transition: Transition) { }
    })
  }

  private fun getPhoto() {
    val photo = BitmapFactory.decodeResource(resources, place.getImageResourceId(this))
    colorize(photo)
  }

  private fun colorize(photo: Bitmap) {
    val palette = Palette.from(photo).generate()
    applyPalette(palette)
  }

  private fun applyPalette(palette: Palette) {
    window.setBackgroundDrawable(ColorDrawable(palette.getDarkMutedColor(defaultColor)))
    placeNameHolder.setBackgroundColor(palette.getMutedColor(defaultColor))
  }

  override fun onBackPressed() {
    finishAfterTransition()
  }
}