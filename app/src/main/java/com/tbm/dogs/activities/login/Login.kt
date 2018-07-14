package com.tbm.dogs.activities.login

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.Gson
import com.tbm.dogs.Helper.Action
import com.tbm.dogs.Helper.Shared
import com.tbm.dogs.Helper.Var
import com.tbm.dogs.R
import com.tbm.dogs.activities.main.Main
import com.tbm.dogs.model.obj.Shiper
import kotlinx.android.synthetic.main.login.*

class Login : AppCompatActivity(), Results {


    private lateinit var shared: Shared
    private lateinit var handlerP: HandlerP
    private lateinit var action: Action
    private lateinit var progressDialog: ProgressDialog

    internal fun init() {
        bLogin.setOnClickListener { handlerP.actionLogin(eUserName, ePassWord) }
        tForgot.setOnClickListener { showForGot() }
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Đang xử lý, xin chờ giây lát!")
        progressDialog.setCancelable(false)
        shared = Shared(this)
        handlerP = HandlerP(this)
        action = Action()
    }

    @SuppressLint("MissingPermission")
    private fun showForGot() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Thông báo!")
        builder.setMessage("Bạn có muốn gọi tới tổng đài để yêu cầu cấp lại mật khẩu không?")
        builder.setPositiveButton("OK") {
            dialogInterface, i ->
                dialogInterface.cancel()
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:1800585852")
            startActivity(callIntent)
        }
        builder.create().show()    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.login)
        init()
        action.requestPermission(this, Var.permissions, Var.PermissionAll)
        Var.currentTokenFCM = shared.tokenFCM
        Log.e("currentToken:", Var.currentTokenFCM)
        initLogin()
    }

    private fun initLogin(){
        val user = shared.user
        val pass = shared.pass
        if(!user.isEmpty() && ! pass.isEmpty()){
            handlerP.actionLogin(user,pass)
        }
    }

    override fun saveInfoUser(response1: String) {
        shared.saveInfoShiper(response1)
        shared.saveUserPass(/*eUserName.text.toString()*/"0945333445",/*ePassWord.text.toString()*/"123456")
    }

    override fun startMain(user: Bundle) {
        startActivity(Intent(this, Main::class.java).putExtras(user))
        finish()
    }

    override fun showError() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Lỗi!")
        builder.setMessage("Số điện thoại hoặc mật khẩu của bạn không chính xác!")
        builder.setPositiveButton("OK") { dialogInterface, i -> dialogInterface.cancel() }
        builder.create().show()
    }

    override fun dismisDialog() {
        progressDialog.dismiss()
    }

    override fun showDialog() {
        progressDialog.show()
    }

    override fun showConnectError() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Lỗi!")
        builder.setMessage("Vui lòng kiểm tra kết nối mạng của bạn và thử lại!")
        builder.setPositiveButton("OK") { dialogInterface, i -> dialogInterface.cancel() }
        builder.create().show()
    }
}
