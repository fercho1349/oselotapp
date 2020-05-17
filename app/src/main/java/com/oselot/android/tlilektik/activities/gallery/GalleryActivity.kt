package com.oselot.android.tlilektik.activities.gallery

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.activity_photo_detail.*
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.navigation.NavigationView
import com.oselot.android.tlilektik.activities.galleryDetail.GalleryDetailActivity
import com.oselot.android.tlilektik.activities.musicPlayer.MusicPlayerActivity
import com.oselot.android.tlilektik.models.LocalDataSource
import com.oselot.android.tlilektik.models.ProfileUser
import com.oselot.android.tlilektik.utils.CircleTransform
import com.squareup.picasso.Picasso
import androidx.core.util.Pair
import com.oselot.android.tlilektik.R
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class GalleryActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, GalleryContract.ViewInterface {

  private lateinit var staggeredLayoutManager: StaggeredGridLayoutManager
  private lateinit var adapter: GalleryAdapter
  private lateinit var menu: Menu
  private lateinit var galleryPresenter: GalleryContract.PresenterInterface

  private val onItemClickListener = object : GalleryAdapter.OnItemClickListener {
    override fun onItemClick(view: View, position: Int) {
      val transitionIntent = GalleryDetailActivity.newIntent(this@GalleryActivity, position)

      val navigationBar = findViewById<View>(android.R.id.navigationBarBackground)
      val statusBar = findViewById<View>(android.R.id.statusBarBackground)

      val imagePair = Pair.create(placeImage as View, "tImage")
      val holderPair = Pair.create(placeNameHolder as View, "tNameHolder")

      val navPair = Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME)
      val statusPair = Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME)
      val toolbarPair = Pair.create(toolbar as View, "tActionBar")

      val pairs = mutableListOf(imagePair, holderPair, statusPair, toolbarPair)
      if (navigationBar != null) {
        pairs += navPair
      }

      val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@GalleryActivity,
        *pairs.toTypedArray())
      ActivityCompat.startActivity(this@GalleryActivity, transitionIntent, options.toBundle())
    }
  }

  override fun onStart() {
    super.onStart()
    galleryPresenter.getProfileUser()
  }

  override fun onStop() {
    super.onStop()
    galleryPresenter.stop()
  }

  private fun setupPresenter() {
    val dataSource = LocalDataSource(application)
    galleryPresenter = GalleryPresenter(this, dataSource)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setupPresenter()

    staggeredLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
    list.layoutManager = staggeredLayoutManager

    adapter = GalleryAdapter(this)
    adapter.setOnItemClickListener(onItemClickListener)
    list.adapter = adapter

    setUpActionBar()
  }

  private fun setUpActionBar() {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(false)
    supportActionBar?.setDisplayShowTitleEnabled(true)
    supportActionBar?.elevation = 7.0f

    val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
    val navView: NavigationView = findViewById(R.id.nav_view)
    val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
    )
    drawerLayout.addDrawerListener(toggle)
    toggle.syncState()

    navView.setNavigationItemSelectedListener(this)
    val headerView = navView.getHeaderView(0)
    val imageViewNavHeader = headerView.findViewById<ImageView>(R.id.imageViewNavHeader)
    Picasso.get().load(R.mipmap.ic_perfil_dev).transform(CircleTransform()).into(imageViewNavHeader)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    this.menu = menu
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val id = item.itemId
    if (id == R.id.action_toggle) {
      toggle()
      return true
    }
    return super.onOptionsItemSelected(item)
  }

  private fun toggle() {
    navigationToGalleryDetail()
  }

  override fun onBackPressed() {
    val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }


  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.nav_share -> {
        val pm = packageManager
        try {
          val waIntent = Intent(Intent.ACTION_SEND)
          waIntent.type = "text/plain"
          val text = "https://github.com/fercho1349/OselotTest"

          val info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA)
          waIntent.setPackage("com.whatsapp")

          waIntent.putExtra(Intent.EXTRA_TEXT, text)
          startActivity(Intent.createChooser(waIntent, "Share with"))

        } catch (e: PackageManager.NameNotFoundException) {
          Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                  .show()
        }

      }
      R.id.nav_send -> {
        try {
          packageManager.getPackageInfo("com.linkedin.android", 0)
          intent = Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://profile/fernando-gálvez-castillo-a8aa21a4"))
        } catch (e: Exception) {
          Toast.makeText(this, "LinkedIn not Installed", Toast.LENGTH_SHORT)
                  .show()
        } finally {
          startActivity(intent)
        }
      }
    }
    val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
    drawerLayout.closeDrawer(GravityCompat.START)
    return true
  }

  override fun displayProfileUser(profileUser: ProfileUser?) {
    txt_username.text = profileUser!!.username
    txt_name.text = profileUser.name+" "+profileUser.lastname
    txt_description.text = profileUser.description
  }

  override fun navigationToGalleryDetail() {
    startActivity(Intent(this, MusicPlayerActivity::class.java))
  }

}
