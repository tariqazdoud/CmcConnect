package com.example.cmcconnect.formateur.formateurSeeRequests

interface OnStudentRequestReplyListener {
    fun onStudentRequestReply(response: String, teacher: Int, student: Int, request: Int)
}