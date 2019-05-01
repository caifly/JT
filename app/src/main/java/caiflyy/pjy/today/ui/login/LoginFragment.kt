package caiflyy.pjy.today.ui.login

import android.Manifest
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import caiflyy.pjy.today.R
import caiflyy.pjy.today.base.BaseFragment
import caiflyy.pjy.today.utils.PASSWORD_LENGTH
import caiflyy.pjy.today.utils.PHONE_LENGTH
import caiflyy.pjy.today.utils.RC_LOCATION_AND_STORAGE_PERM
import caiflyy.pjy.today.utils.TAG
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.support.v4.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.login
 * 日期：2019/4/14
 * 描述：登录注册界面
 * @author 蔡葳
 */
class LoginFragment : BaseFragment() {
    private var isLogin = false
    private val loginConstraintSet = ConstraintSet()
    private val registerConstraintSet = ConstraintSet()

    private val viewModel: LoginViewModel by viewModel()

    override fun setLayout(): Int {
        return R.layout.fragment_login
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(activity!!, R.id.navHostFragment)
        viewModel.isLoginOk.observe(this, Observer {
            AnkoLogger(TAG).error("loginState观察者:$it")
            animation_view.visibility = View.GONE
            if (it) {
                navController.navigate(
                    R.id.actionLoginOk,
                    null,
                    NavOptions.Builder().setPopUpTo(
                        R.id.loginFragment,
                        true
                    ).build()
                )
            } else {
                toast(R.string.toast_login_fail)
            }
        })
        loginConstraintSet.clone(csLogin)
        registerConstraintSet.clone(context, R.layout.fragment_register)
        fabSignIn.setOnClickListener{
            TransitionManager.beginDelayedTransition(csLogin)
            if (isLogin) {
                loginConstraintSet.applyTo(csLogin)
                btnSignGo.text = getString(caiflyy.pjy.today.R.string.sign_in_button_login)
            } else {
                registerConstraintSet.applyTo(csLogin)
                btnSignGo.text = getString(caiflyy.pjy.today.R.string.sign_in_button_register)
            }
            isLogin = !isLogin
        }
        btnSignGo.setOnClickListener{
            locationPermission()
        }
    }

    private fun onLogin() {
        val lUserPhone = edtLoginUserPhone.text.toString()
        val lPassword = edtLoginUserPassword.text.toString()
        if (checkForm(lUserPhone, lPassword)) {
            animation_view.visibility = View.VISIBLE
            viewModel.userLogin(lUserPhone, lPassword, isLogin)
        }
    }


    /**
     * 检查提交表单合法性
     * @param pUserPhone 用户电话
     * @param pPassword 用户密码
     * @return 检查结果
     */
    private fun checkForm(pUserPhone: String, pPassword: String): Boolean {
        val lConfirm = edtRegisterConfirm.text.toString()
        var isPass = true
        if (pUserPhone.isEmpty() || pUserPhone.length != PHONE_LENGTH) {
            edtLoginUserPhone.error = getString(caiflyy.pjy.today.R.string.sign_in_error_phone)
            isPass = false
        } else {
            edtLoginUserPhone.error = null
        }
        if (pPassword.isEmpty() || pPassword.length < PASSWORD_LENGTH) {
            edtLoginUserPassword.error = getString(caiflyy.pjy.today.R.string.sign_in_error_password)
            isPass = false
        } else {
            edtLoginUserPassword.error = null
        }
        if (isLogin) {
            if (lConfirm.isEmpty() || lConfirm != pPassword) {
                edtRegisterConfirm.error = getString(caiflyy.pjy.today.R.string.sign_up_error_confirm)
                isPass = false
            } else {
                edtRegisterConfirm.error = null
            }
        }
        return isPass
    }

    @AfterPermissionGranted(RC_LOCATION_AND_STORAGE_PERM)
    private fun locationPermission() {
        val perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (EasyPermissions.hasPermissions(context!!, *perms)) {
            onLogin()
        } else {
            EasyPermissions.requestPermissions(
                this, getString(R.string.permission_location),
                RC_LOCATION_AND_STORAGE_PERM, *perms
            )
        }
    }
}
