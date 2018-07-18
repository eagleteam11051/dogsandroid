package com.tbm.dogs.activities.login

import android.os.Bundle

interface Results {
    fun saveInfoUser(response1: String)

    fun startMain(user: Bundle)

    fun showError()

    fun dismisDialog()

    fun showDialog()

    fun showConnectError()
}
