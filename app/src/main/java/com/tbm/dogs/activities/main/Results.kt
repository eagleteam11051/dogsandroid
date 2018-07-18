package com.tbm.dogs.activities.main

import com.tbm.dogs.model.obj.Job

interface Results {
    fun tapAgain()

    fun finishApp()
    fun showError()
    fun returnJobs(jobs: ArrayList<Job>)
    fun returnJobsWaiting(jobs: ArrayList<Job>)
    fun returnJobsWorking(jobs: ArrayList<Job>)
    fun returnJobsDone(jobs: ArrayList<Job>)
    fun showErrorJobs()
    fun showErrorJobsWaiting()
    fun showErrorJobsWorking()
    fun showErrorJobsDone()
    fun requestUpdate()
    fun showEnableLocation()
}
