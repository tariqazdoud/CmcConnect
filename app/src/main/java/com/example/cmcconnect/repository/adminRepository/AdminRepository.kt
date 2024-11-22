package com.example.cmcconnect.repository.adminRepository

import com.example.cmcconnect.model.AnsweredRequestsWithRequestDetails
import com.example.cmcconnect.model.FiliereDto
import com.example.cmcconnect.model.GroupToPost
import com.example.cmcconnect.model.GroupeDto
import com.example.cmcconnect.model.JustifWithStudent
import com.example.cmcconnect.model.RequestWithStudent
import com.example.cmcconnect.model.PoleTeacherDto
import com.example.cmcconnect.model.RequestDto
import com.example.cmcconnect.model.StudentDto
import com.example.cmcconnect.model.StudentRequestForAdminReplyToPost
import com.example.cmcconnect.model.StudentToSend

interface AdminRepository {
    suspend fun getFilieresByPoleId(idPole: Int): List<FiliereDto>
    suspend fun getGroupsByFiliereId(idFiliere: Int): List<GroupeDto>
    suspend fun getStudentsByGroupId(idGroup: Int): List<StudentDto>
    suspend fun loadJustifsForAdmin(idAdmin: Int): List<JustifWithStudent>
    suspend fun loadStudentRequestsForAdmin(idAdmin: Int): List<RequestWithStudent>
    suspend fun adminReplyToStudent(reply: StudentRequestForAdminReplyToPost): Boolean
    suspend fun getFormateursByPoleId(idPole: Int): List<PoleTeacherDto>
    suspend fun addGroup(group: GroupToPost): Boolean
    suspend fun getAnsweredRequests(idAdmin: Int): List<AnsweredRequestsWithRequestDetails>
    suspend fun addStudent(student : StudentToSend) : Boolean

}