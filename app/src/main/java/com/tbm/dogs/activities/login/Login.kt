package com.tbm.dogs.activities.login

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.tbm.dogs.Helper.Action
import com.tbm.dogs.Helper.Shared
import com.tbm.dogs.Helper.Var
import com.tbm.dogs.R
import com.tbm.dogs.activities.main.Main

class Login : AppCompatActivity(), Results {

    lateinit var eUserName: EditText
    lateinit var ePassWord: EditText
    lateinit var bLogin: Button
    lateinit var tForgot: TextView
    lateinit var shared: Shared
    lateinit var handlerP: HandlerP
    lateinit var action: Action
    lateinit var progressDialog: ProgressDialog

    internal fun init() {

        eUserName = findViewById(R.id.eUserName)
        ePassWord = findViewById(R.id.ePassWord)
        bLogin = findViewById(R.id.bLogin)
        bLogin.setOnClickListener { handlerP.actionLogin(eUserName, ePassWord) }
        tForgot = findViewById(R.id.tForgot)
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Đang xử lý, xin chờ giây lát!")
        progressDialog.setCancelable(false)
        shared = Shared(this)
        handlerP = HandlerP(this)
        action = Action()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.login)
        init()
        action.requestPermission(this, Var.permissions, Var.PermissionAll)
        Var.currentTokenFCM = shared.tokenFCM
        Log.e("currentToken:", Var.currentTokenFCM)
    }

    override fun saveInfoUser(response1: String) {
        shared.saveInfoUser(response1)
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
