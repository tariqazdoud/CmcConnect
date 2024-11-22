package com.example.cmcconnect.repository.teacherRepository

import android.net.Uri
import com.example.cmcconnect.model.CoursDto
import com.example.cmcconnect.model.GroupeDto
import com.example.cmcconnect.model.RequestDto
import com.example.cmcconnect.model.RequestWithStudent
import com.example.cmcconnect.model.ResourceToPost
import com.example.cmcconnect.model.RessourceDto
import com.example.cmcconnect.model.StudentDto
import com.example.cmcconnect.model.StudentRequestReplyToPost

interface TeacherRepository {
    suspend fun loadGroupsOfTeacher(idTeacher: Int): List<CoursDto>
    suspend fun loadModulesOfGroup(idTeacher: Int, idGroup: Int): List<CoursDto>
    suspend fun uploadResourceToBucket(uri: Uri, fileName: String): String?
    suspend fun postResource(resource: ResourceToPost): Boolean
    suspend fun getCourByTeacherId(idTeacher : Int) : List<CoursDto>
    suspend fun getModuleByTeacherAndGroupId(idTeacher : Int, idGroup:Int): List<CoursDto>
    suspend fun getResourceTeacherAndModuleId(idTeacher : Int,idModule:Int) : List<RessourceDto>
    suspend fun loadStudentRequestsForTeacher(idTeacher: Int): List<RequestWithStudent>
    suspend fun teacherReplyToStudent(reply: StudentRequestReplyToPost): Boolean

    suspend fun getStudentsByGroupId(idGroup: Int): List<StudentDto>
}