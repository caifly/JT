package caiflyy.pjy.today

import android.Manifest
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import caiflyy.pjy.today.data.TodayUser
import caiflyy.pjy.today.utils.RC_CALL_PHONE_PERM
import caiflyy.pjy.today.utils.TAG
import cn.bmob.v3.BmobUser
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today
 * 日期：2019/4/11
 * 描述：主Activity类
 * 完成功能导航
 * @author 蔡葳
 */
class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(this, R.id.navHostFragment)
        setSupportActionBar(toolbar)
        supportActionBar!!.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener {
            if (drawerLayout.getDrawerLockMode(GravityCompat.START) == DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
                toast("只在主界面打开")
            } else {
                if (drawerLayout.isDrawerOpen(navView)) {
                    drawerLayout.closeDrawer(navView)
                } else {
                    drawerLayout.openDrawer(navView)
                }
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
        navController.addOnDestinationChangedListener { navController, destination, _ ->
            AnkoLogger(TAG).error("====目标转换${destination.label}")
            tvToolbarTitle.text = destination.label
            when (destination.id) {
                R.id.launcherFragment -> {
                }
                R.id.homeFragment -> {
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
                R.id.loginFragment -> {
                }
                R.id.demo01Fragment,
                R.id.demo02Fragment,
                R.id.demo03Fragment,
                R.id.demo04Fragment,
                R.id.demo05Fragment,
                R.id.demo06Fragment,
                R.id.demo07Fragment,
                R.id.demo08FragmentMenu,
                R.id.demo08FragmentGame,
                R.id.demo09FragmentList,
                R.id.demo09FragmentAdd,
                R.id.demo10ExamStartFragment,
                R.id.demo10ExamPassFragment,
                R.id.demo10ExamWinFragment,
                R.id.demo10ExamFailFragment,
                R.id.demo11Fragment,
                R.id.demo12Fragment,
                R.id.commentFragment,
                R.id.studyCourseFragment -> {
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                else -> {
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
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
}

