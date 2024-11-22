package com.example.cmcconnect.admin.adminSeeRequests

interface OnStudentRequestsForAdminListener {
    fun onStudentRequestForAdminReply(response: String, admin: Int, student: Int, request: Int)
}