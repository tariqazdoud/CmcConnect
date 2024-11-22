package com.example.cmcconnect.repository.studentRepository

import android.net.Uri
import com.example.cmcconnect.model.AdminDto
import com.example.cmcconnect.model.JustifToSend
import com.example.cmcconnect.model.RequestToSend
import com.example.cmcconnect.model.RequestTypeDto
import com.example.cmcconnect.model.CoursDto
import com.example.cmcconnect.model.ModuleDto
import com.example.cmcconnect.model.RessourceDto
import com.example.cmcconnect.model.TeacherDto

interface StudentRepository {

    suspend fun loadRequests(): List<RequestTypeDto>
    suspend fun loadTeachersForStudent(idStudent: Int): List<TeacherDto>
    suspend fun getStudentAdmin(idStudent: Int): AdminDto
    suspend fun sendRequest(request: RequestToSend): Boolean
    suspend fun uploadFileToBucket(uri: Uri, fileName: String): String?
    suspend fun sendJustif(justif: JustifToSend): Boolean

    suspend fun getModulesByGroupId(idGroup : Int) : List<CoursDto>
    suspend fun getResourcesByModuleId(idModule: Int) : List<RessourceDto>

    suspend fun getRecentResourcesByModuleId(idModule: Int): List<RessourceDto>
    suspend fun getRecentModulesByGroupId(idGroup: Int): List<CoursDto>
}