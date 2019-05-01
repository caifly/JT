package caiflyy.pjy.today.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import caiflyy.pjy.today.R
import caiflyy.pjy.today.utils.RC_CALL_PHONE_PERM
import org.jetbrains.anko.support.v4.toast
import pub.devrel.easypermissions.EasyPermissions

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.base
 * 日期：2019/4/11
 * 描述：Fragment的通用基类
 * @author 蔡葳
 */
abstract class BaseFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(setLayout(), container, false)
    }

    /**
     * 这只布局文件
     */
    abstract fun setLayout():Int


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        when(requestCode){
            RC_CALL_PHONE_PERM -> toast(getString(R.string.toast_permit_call_phone))
        }

    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        when(requestCode){
            RC_CALL_PHONE_PERM -> toast(getString(R.string.toast_deny_call_phone))
        }
    }
}
