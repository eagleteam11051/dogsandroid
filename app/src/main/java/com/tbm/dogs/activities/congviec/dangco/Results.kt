package com.tbm.dogs.activities.congviec.dangco

import com.tbm.dogs.model.obj.Job

interface Results {
    fun showError()
    fun returnJobs(jobs: ArrayList<Job>)
    fun showSuccess()
}
