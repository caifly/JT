package caiflyy.pjy.today.ui.profile


import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import caiflyy.pjy.today.R
import caiflyy.pjy.today.database.user.CacheUser
import caiflyy.pjy.today.utils.TAG
import caiflyy.pjy.today.utils.image.ImageLoaderManager
import kotlinx.android.synthetic.main.fragment_profile.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.profile
 * 日期：2019/4/25
 * 描述：用户信息界面
 * @author 蔡葳
 */
class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by viewModel()
    private val imageLoaderManager: ImageLoaderManager by inject()

    lateinit var cacheUser: CacheUser

    val CAMERA_REQUEST_CODE = 0
    val PICK_IMAGE_REQUEST_CODE = 1

    lateinit var imageFilePath: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        img_setting_camera.setOnClickListener {
            try {
                val imageFile = createImageFile()
                val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (callCameraIntent.resolveActivity(context?.packageManager) != null) {
                    val authorities = context?.packageName + ".fileprovider"
                    val imageUri = FileProvider.getUriForFile(context!!, authorities, imageFile)
                    callCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                    startActivityForResult(callCameraIntent, CAMERA_REQUEST_CODE)
                }
            } catch (e: IOException) {
                toast("Could not create file!")
            }

        }
        img_setting_picture.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, PICK_IMAGE_REQUEST_CODE)
        }
        sw_setting_can_edit.setOnCheckedChangeListener { _, isChecked ->
            settingEnable(isChecked)
            openPhotoButton(isChecked)
            if (!isChecked) {
                finishSetting()
            }
        }
        rg_setting_sex.setOnCheckedChangeListener { group, checkedId ->

        }
        viewModel.onSuccess.observe(this, Observer {
            if (it != null) {
                cacheUser = it
                imageFilePath = cacheUser.headIcon
                imageLoaderManager.displayImage("file://" + it.headIcon, img_setting_portrait)
                edt_setting_name.setText(it.nickName)
                if (it.email.isEmpty()) {
                    edt_setting_email.setText("未设置邮箱")
                } else {
                    edt_setting_email.setText(it.email)
                }
                if (it.sex) {
                    rd_setting_male.isChecked = true
                } else {
                    rd_setting_female.isChecked = true
                }
                if (sw_setting_can_edit.isChecked) {
                    sw_setting_can_edit.isChecked = false
                }
            }
        })
        viewModel.getCacheUserInfo()
    }

    private fun finishSetting() {
        val realName = edt_setting_name.text.toString().trim()
        cacheUser.nickName = realName
        if (rd_setting_female.isChecked) {
            cacheUser.sex = false
        }
        if (rd_setting_male.isChecked) {
            cacheUser.sex = true
        }
        val email = edt_setting_email.text.toString().trim()
        if (validateEmail(email)) {
            cacheUser.email = email
        } else {
            toast("邮件格式错误")
        }
        cacheUser.headIcon = imageFilePath
        viewModel.updateUser(cacheUser)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    img_setting_portrait.setImageBitmap(setScaledBitmap())
                }
            }
            PICK_IMAGE_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val imageUri = data?.data
                    val cursor = context?.contentResolver?.query(imageUri!!, null, null, null, null);
                    if (cursor != null) {
                        if (cursor.moveToFirst()) {
                            imageFilePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        }
                        cursor.close()
                        img_setting_portrait.setImageBitmap(setScaledBitmap())
                    }
                }
            }
            else -> {
                toast("Unrecognized request code")
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        val emailPatten = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
        val pattern = Pattern.compile(emailPatten)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    @Throws(IOException::class)
    fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName: String = "JPEG_" + timeStamp + "_"
        val storageDir: File = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        if (!storageDir.exists()) storageDir.mkdirs()
        val imageFile = File.createTempFile(imageFileName, ".jpg", storageDir)
        imageFilePath = imageFile.absolutePath
        AnkoLogger(TAG).error(imageFilePath)
        return imageFile
    }

    private fun setScaledBitmap(): Bitmap {
        val imageViewWidth = img_setting_portrait.width
        val imageViewHeight = img_setting_portrait.height

        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(imageFilePath, bmOptions)
        val bitmapWidth = bmOptions.outWidth
        val bitmapHeight = bmOptions.outHeight

        val scaleFactor = Math.min(bitmapWidth / imageViewWidth, bitmapHeight / imageViewHeight)

        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor
        viewModel.uploadImage(imageFilePath)
        return BitmapFactory.decodeFile(imageFilePath, bmOptions)

    }

    private fun settingEnable(pEnable: Boolean) {
        edt_setting_email.isEnabled = pEnable
        edt_setting_name.isEnabled = pEnable
        rd_setting_female.isEnabled = pEnable
        rd_setting_male.isEnabled = pEnable
        img_setting_portrait.isClickable = pEnable
    }

    private fun openPhotoButton(pIsOpen: Boolean) {
        if (!pIsOpen) {
            val _HolderTranslate1 = PropertyValuesHolder
                .ofFloat("translationX", 200f, 0f)
            val _HolderTranslate2 = PropertyValuesHolder
                .ofFloat("translationX", -200f, 0f)
            val _HolderRotation = PropertyValuesHolder
                .ofFloat("rotation", 0f, 720f)
            ObjectAnimator.ofPropertyValuesHolder(img_setting_camera, _HolderTranslate1, _HolderRotation)
                .setDuration(300)
                .start()
            ObjectAnimator.ofPropertyValuesHolder(img_setting_picture, _HolderTranslate2, _HolderRotation)
                .setDuration(300)
                .start()
        } else {
            img_setting_camera.setVisibility(View.VISIBLE)
            img_setting_picture.setVisibility(View.VISIBLE)
            val _HolderTranslate1 = PropertyValuesHolder.ofFloat("translationX", 0f, 200f)
            val _HolderTranslate2 = PropertyValuesHolder.ofFloat("translationX", 0f, -200f)
            val _HolderRotation = PropertyValuesHolder.ofFloat("rotation", 0f, 720f)
            ObjectAnimator.ofPropertyValuesHolder(img_setting_camera, _HolderTranslate1, _HolderRotation)
                .setDuration(300)
                .start()
            ObjectAnimator.ofPropertyValuesHolder(img_setting_picture, _HolderTranslate2, _HolderRotation)
                .setDuration(300)
                .start()
        }
    }
}
