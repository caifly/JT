package caiflyy.pjy.today

import android.Manifest
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI
import caiflyy.pjy.today.data.TodayUser
import caiflyy.pjy.today.utils.RC_CALL_PHONE_PERM
import caiflyy.pjy.today.utils.TAG
import cn.bmob.v3.BmobUser
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener {
            if (drawerLayout.isDrawerOpen(navView)) {
                drawerLayout.closeDrawer(navView)
            } else {
                drawerLayout.openDrawer(navView)
            }
        }
        val drawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.nav_text_menu_open,
            R.string.nav_text_menu_close
        )
        drawerToggle.syncState()
        drawerLayout.addDrawerListener(drawerToggle)

        val headView = navView.getHeaderView(0)
        val navActionPhone = headView.findViewById<RelativeLayout>(R.id.navActionPhone)
        val navActionMail = headView.findViewById<RelativeLayout>(R.id.navActionMail)
        val navActionMessage = headView.findViewById<RelativeLayout>(R.id.navActionMessage)
        navActionPhone.setOnClickListener {
            drawerLayout.closeDrawer(navView)
            phone()
        }
        navActionMail.setOnClickListener {
            drawerLayout.closeDrawer(navView)
            email(getString(R.string.email), getString(R.string.send_message_title))
        }
        navActionMessage.setOnClickListener {
            drawerLayout.closeDrawer(navView)
            sendSMS(getString(R.string.phone_number), getString(R.string.send_message_title))
        }
        navController = findNavController(this, R.id.navHostFragment)
        navController.addOnDestinationChangedListener { navController, destination, _ ->
            AnkoLogger(TAG).error("====目标转换${destination.label}")
            tvToolbarTitle.text = destination.label
            when(destination.id){
                R.id.launcherFragment ->{}
                R.id.homeFragment ->{}
                R.id.loginFragment ->{}
                else ->{
                    if (BmobUser.getCurrentUser(TodayUser::class.java) == null) {
                        navController.navigate(
                            R.id.actionLogin,
                            null,
                            NavOptions.Builder().setPopUpTo(
                                destination.id,
                                true
                            ).build()
                        )
                    }
                }
            }
        }
        navView.setNavigationItemSelectedListener { it ->
            drawerLayout.closeDrawer(navView)
            navController.navigate(it.itemId)
            true
        }
    }

    @AfterPermissionGranted(RC_CALL_PHONE_PERM)
    private fun phone() {
        if (EasyPermissions.hasPermissions(applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            makeCall(getString(R.string.phone_number))
        } else {
            EasyPermissions.requestPermissions(
                this, getString(R.string.permission_phone_call),
                RC_CALL_PHONE_PERM, Manifest.permission.CALL_PHONE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        when (requestCode) {
            RC_CALL_PHONE_PERM -> toast(getString(R.string.toast_permit_call_phone))
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        when (requestCode) {
            RC_CALL_PHONE_PERM -> toast(getString(R.string.toast_deny_call_phone))
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.navHostFragment), drawerLayout
        )
    }
}

