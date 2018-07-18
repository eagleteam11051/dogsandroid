package com.tbm.dogs.activities.congviec.danglam

import com.tbm.dogs.model.obj.Job

interface Results {
    fun showEnableLocation()
    fun requestUpdate(job: Job, i: Int)
    fun showError(response: String)
    fun showSuccess(mode: Int)
    fun showDialog()
    fun dismisDialog()
    fun returnJobsWorking(jobs: ArrayList<Job>)
    fun showErrorJobsWorking()
    fun update(b: Boolean)
}