package com.tbm.dogs.activities.main

import com.tbm.dogs.model.obj.Job

interface Results {
    fun tapAgain()

    fun finishApp()
    fun showError()
    fun returnJobs(jobs: ArrayList<Job>)
    fun returnJobsWaiting(jobs: ArrayList<Job>)
}
