package com.example.cmcconnect.repository.adminRepository

import android.util.Log
import com.example.cmcconnect.model.AnsweredRequestsWithRequestDetails
import com.example.cmcconnect.model.FiliereDto
import com.example.cmcconnect.model.GroupToPost
import com.example.cmcconnect.model.GroupeDto
import com.example.cmcconnect.model.JustifDto
import com.example.cmcconnect.model.JustifWithStudent
import com.example.cmcconnect.model.PoleDto
import com.example.cmcconnect.model.RequestWithStudent
import com.example.cmcconnect.model.PoleTeacherDto
import com.example.cmcconnect.model.RequestDto
import com.example.cmcconnect.model.StudentDto
import com.example.cmcconnect.model.StudentRequestForAdminReplyToPost
import com.example.cmcconnect.model.StudentRequestReplyToPost
import com.example.cmcconnect.model.StudentToSend
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(private val postgrest: Postgrest) : AdminRepository {
    override suspend fun getFilieresByPoleId(idPole: Int): List<FiliereDto> {
        return withContext(Dispatchers.IO) {
            val listFilieres = postgrest.from("filiere").select {
                filter {
                    eq("id_pole_fk", idPole)
                }
            }.decodeList<FiliereDto>()
            listFilieres
        }
    }

    override suspend fun getGroupsByFiliereId(idFiliere: Int): List<GroupeDto> {
        return withContext(Dispatchers.IO) {
            val listFilieres = postgrest.from("groupe").select {
                filter {
                    eq("id_filiere_fk", idFiliere)
                }
            }.decodeList<GroupeDto>()
            listFilieres

        }
    }

    override suspend fun getStudentsByGroupId(idGroup: Int): List<StudentDto> {
        return with(Dispatchers.IO) {
            val lisStud = postgrest.from("student").select {
                filter {
                    eq("id_groupe_fk", idGroup)
                }
            }.decodeList<StudentDto>()
            lisStud
        }
    }

    override suspend fun loadJustifsForAdmin(idAdmin: Int): List<JustifWithStudent> {
        return withContext(Dispatchers.IO) {
            val response = postgrest.from("justif")
                .select(Columns.raw("id, motif, file, id_student_fk, id_admin_fk, student(id, name, email, phone, image, id_groupe_fk, id_type_user_fk, groupe(id, name, id_year_fk, id_filiere_fk))")) {
                    filter {
                        eq("id_admin_fk", idAdmin)
                    }
                    order("id", order = Order.DESCENDING)
                }
            Log.d("SupabaseResponse", response.data)
            val justifs = response.decodeList<JustifWithStudent>()
            justifs
        }
    }

    override suspend fun loadStudentRequestsForAdmin(idAdmin: Int): List<RequestWithStudent> {
        return withContext(Dispatchers.IO) {
            try {
                val response = postgrest.from("request")
                    .select(Columns.raw("id, motif, id_student_fk, id_type_request_fk, type_request(id, name), id_teacher_fk, id_admin_fk, student(id, name, email, phone, image, id_groupe_fk, id_type_user_fk, groupe(id, name, id_year_fk, id_filiere_fk)), answered")) {
                        filter {
                            eq("id_admin_fk", idAdmin)
                            eq("answered", false)
                        }
                        order("id", order = Order.DESCENDING)
                    }

                Log.d("SupabaseResponse", response.data)

                val requests = response.decodeList<RequestWithStudent>()
                requests
            } catch (e: Exception) {
                Log.e("RepositoryError", "Error loading student requests", e)
                emptyList()
            }
        }
    }

    override suspend fun adminReplyToStudent(reply: StudentRequestForAdminReplyToPost): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                postgrest.from("admin_response").insert(reply)
                postgrest.from("request").update(mapOf("answered" to true)) {
                    filter {
                        eq("id", reply.id_request_fk)
                    }
                }
                true
            }
        } catch (e: Exception) {
            Log.e("post ressource", "Error posting ressource", e)
            false
        }
    }

    override suspend fun getFormateursByPoleId(idPole: Int): List<PoleTeacherDto> {
        return withContext(Dispatchers.IO) {
            val listPoleTeacher =
                postgrest.from("pole_teacher")
                    .select(columns = Columns.list("id,pole(id),teacher(id,name,email,phone,image,id_type_user_fk)")) {
                        filter {
                            eq("id_pole_fk", idPole)
                        }
                    }.decodeList<PoleTeacherDto>()
            listPoleTeacher
        }
    }

    override suspend fun addGroup(group: GroupToPost): Boolean {
        return try {
            withContext(Dispatchers.IO){
                postgrest.from("groupe").insert(group)
                true
            }
        }catch (e:Exception){
            Log.e("add group", "Error adding group", e)
            false
        }
    }

    override suspend fun addStudent(student: StudentToSend): Boolean {
        return try {
            withContext(Dispatchers.IO){
                postgrest.from("student").insert(student)
                true
            }
        }catch (e:Exception){
            Log.e("add group", "Error adding group", e)
            false
        }
    }

    override suspend fun getAnsweredRequests(idAdmin: Int): List<AnsweredRequestsWithRequestDetails> {
        return withContext(Dispatchers.IO) {
                val answeredRequests = postgrest.from("admin_response").select(columns = Columns.list("id, response, admin(id, name, email, phone, image, id_pole_fk, id_type_user_fk), student(id, name, email, phone, image, id_type_user_fk, id_groupe_fk, groupe(id, name)), request(id, motif, id_student_fk, id_teacher_fk, id_admin_fk, answered)")) {
                    filter {
                        eq("id_admin_fk", idAdmin)
                    }
                }
            val requests = answeredRequests.decodeList<AnsweredRequestsWithRequestDetails>()
            requests
        }
    }
}

