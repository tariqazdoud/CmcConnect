package com.example.cmcconnect.repository.teacherRepository

import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import com.example.cmcconnect.model.CoursDto
import com.example.cmcconnect.model.GroupeDto
import com.example.cmcconnect.model.ModuleDto
import com.example.cmcconnect.model.RequestDto
import com.example.cmcconnect.model.RequestWithStudent
import com.example.cmcconnect.model.ResourceToPost
import com.example.cmcconnect.model.RessourceDto
import com.example.cmcconnect.model.StudentDto
import com.example.cmcconnect.model.StudentRequestReplyToPost
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Columns.Companion.ALL
import io.github.jan.supabase.postgrest.query.Columns.Companion.list
import io.github.jan.supabase.postgrest.query.Columns.Companion.raw
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TeacherRepositoryImpl @Inject constructor(
    private val context: Context,
    private val postgrest: Postgrest,
    private val supabase: SupabaseClient
): TeacherRepository {
    override suspend fun loadGroupsOfTeacher(idTeacher: Int): List<CoursDto> {
        return withContext(Dispatchers.IO) {
            val groups = postgrest.from("cours")
                    .select(columns = Columns.list("groupe(id,name,id_filiere_fk)")) {
                        filter {
                            eq("id_teacher_fk", idTeacher)

                        }
                    }.decodeList<CoursDto>()
            val uniqueGroups = groups.distinctBy { it.groupe?.id }
            uniqueGroups
        }
    }

    override suspend fun loadModulesOfGroup(idTeacher: Int, idGroup: Int): List<CoursDto> {
        return withContext(Dispatchers.IO) {
            val modules = postgrest.from("cours").select(columns = Columns.list("module(id,name)")) {
                filter {
                    eq("id_teacher_fk", idTeacher)
                    eq("id_groupe_fk", idGroup)
                }
            }.decodeList<CoursDto>()
            modules
        }
    }

    override suspend fun uploadResourceToBucket(uri: Uri, fileName: String): String? {
        return try {
            val contentResolver = context.contentResolver
            val mimeTypeMap = MimeTypeMap.getSingleton()
            val mimeType = contentResolver.getType(uri)
            val fileExtension = mimeType?.let { mimeTypeMap.getExtensionFromMimeType(it) }

            val finalFileName = if (fileExtension != null) {
                "$fileName.$fileExtension"
            } else {
                fileName
            }

            val inputStream = contentResolver.openInputStream(uri)
            val byteArray = inputStream?.readBytes()
            inputStream?.close()

            if (byteArray != null) {
                supabase.storage.from("ressource_file").upload(finalFileName, byteArray)
                "https://hvlzrnryaxhaudwlwvhf.supabase.co/storage/v1/object/public/ressource_file/$finalFileName"
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("uploadFileToBucket", "Error uploading file", e)
            null
        }
    }

    override suspend fun postResource(resource: ResourceToPost): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                postgrest.from("ressource").insert(resource)
                true
            }
        } catch (e: Exception) {
            Log.e("post ressource", "Error posting ressource", e)
            false
        }
    }

    override suspend fun getCourByTeacherId(idTeacher: Int): List<CoursDto> {
        return withContext(Dispatchers.IO) {
            val lisModuleRes =
                postgrest.from("cours")
                    .select(columns = Columns.list("groupe(id,name,filiere(id,name))")) {
                        filter {
                            eq("id_teacher_fk", idTeacher)

                        }
                    }.decodeList<CoursDto>()
            val uniqueGroups = lisModuleRes.distinctBy { it.groupe?.id }
            uniqueGroups
        }
    }

    override suspend fun getModuleByTeacherAndGroupId(
        idTeacher: Int,
        idGroup: Int
    ): List<CoursDto> {
        return withContext(Dispatchers.IO) {
            val lisModuleRes =
                postgrest.from("cours")
                    .select(columns = Columns.list("module(id,name)")) {
                        filter {
                            eq("id_teacher_fk", idTeacher)
                            eq("id_groupe_fk", idGroup)

                        }
                    }.decodeList<CoursDto>()
            lisModuleRes
        }
    }

    override suspend fun getResourceTeacherAndModuleId(
        idTeacher: Int,
        idModule: Int
    ): List<RessourceDto> {
        return withContext(Dispatchers.IO) {
            val listRes = postgrest.from("ressource").select {
                filter {
                    eq("id_teacher_fk",idTeacher)
                    eq("id_module_fk", idModule)
                }
            }.decodeList<RessourceDto>()
            listRes
        }

    }

    override suspend fun getStudentsByGroupId(idGroup: Int): List<StudentDto> {
        return with(Dispatchers.IO){
            val lisStud = postgrest.from("student").select {
                filter {
                    eq("id_groupe_fk",idGroup)
                }
            }.decodeList<StudentDto>()
            lisStud
        }
    }

    override suspend fun loadStudentRequestsForTeacher(idTeacher: Int): List<RequestWithStudent> {
        return withContext(Dispatchers.IO) {
            try {
                val response = postgrest.from("request")
                    .select(Columns.raw("id, motif, id_student_fk, id_type_request_fk, type_request(id, name), id_teacher_fk, id_admin_fk, student(id, name, email, phone, image, id_groupe_fk, id_type_user_fk, groupe(id, name, id_year_fk, id_filiere_fk)), answered")) {
                        filter {
                            eq("id_teacher_fk", idTeacher)
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

    override suspend fun teacherReplyToStudent(reply: StudentRequestReplyToPost): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                postgrest.from("teacher_response").insert(reply)
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

}
