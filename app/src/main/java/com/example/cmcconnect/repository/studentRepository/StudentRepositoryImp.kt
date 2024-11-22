package com.example.cmcconnect.repository.studentRepository

import android.net.Uri
import android.util.Log
import android.content.Context
import android.webkit.MimeTypeMap
import io.github.jan.supabase.SupabaseClient
import com.example.cmcconnect.model.AdminDto
import com.example.cmcconnect.model.JustifToSend
import com.example.cmcconnect.model.RequestToSend
import com.example.cmcconnect.model.RequestTypeDto
import com.example.cmcconnect.model.CoursDto
import com.example.cmcconnect.model.ModuleDto
import com.example.cmcconnect.model.RessourceDto
import com.example.cmcconnect.model.TeacherDto
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StudentRepositoryImp @Inject constructor(
    private val context: Context,
    private val postgrest: Postgrest,
    private val supabase: SupabaseClient
): StudentRepository {

    override suspend fun getModulesByGroupId(idGroup: Int): List<CoursDto> {
        return withContext(Dispatchers.IO) {
            val lisModuleRes =
                postgrest.from("cours")
                    .select(columns = Columns.list("id,groupe(id),module(id,name)")) {
                        filter {
                            eq("id_groupe_fk", idGroup)
                        }
                    }.decodeList<CoursDto>()
            lisModuleRes
        }
    }
    override suspend fun getResourcesByModuleId(idModule: Int): List<RessourceDto> {
        return withContext(Dispatchers.IO) {
            val listRes = postgrest.from("ressource").select {
                filter {
                    eq("id_module_fk", idModule)
                }
            }.decodeList<RessourceDto>()
            listRes
        }
    }

    override suspend fun getRecentResourcesByModuleId(idModule: Int): List<RessourceDto> {
        return withContext(Dispatchers.IO) {
            val listRes = postgrest.from("ressource").select {
                filter {
                    eq("id_module_fk", idModule)
                }
                limit(1)
                order("id", order = Order.DESCENDING)
            }.decodeList<RessourceDto>()
            listRes
        }
    }

    override suspend fun getRecentModulesByGroupId(idGroup: Int): List<CoursDto> {
        return withContext(Dispatchers.IO) {
            val lisModuleRes = postgrest.from("cours")
                .select(columns = Columns.list("id,groupe(id),module(id,name)")) {
                    filter {
                        eq("id_groupe_fk", idGroup)
                    }
                    limit(4)
                    order("id", order = Order.DESCENDING)
                }.decodeList<CoursDto>()
            lisModuleRes
        }
    }

    override suspend fun loadRequests(): List<RequestTypeDto> {
        return try {
            withContext(Dispatchers.IO) {
                val requests = postgrest.from("type_request").select(Columns.ALL)
                    .decodeList<RequestTypeDto>()
                requests
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun loadTeachersForStudent(idStudent: Int): List<TeacherDto> {
        return try {
            withContext(Dispatchers.IO) {
                val groupIdResult = postgrest.from("student").select(Columns.raw("id_groupe_fk")) {
                    filter {
                        eq("id", idStudent)
                    }
                }.decodeSingle<Map<String, Int>>()
                val groupId = groupIdResult["id_groupe_fk"]
                Log.d("groupId", groupId.toString())

                if (groupId != null) {
                    val teacherIdResults = postgrest.from("cours").select(Columns.raw("id_teacher_fk")) {
                        filter {
                            eq("id_groupe_fk", groupId)
                        }
                    }.decodeList<Map<String, Int>>()
                    val teacherIds = teacherIdResults.mapNotNull { it["id_teacher_fk"] }
                    Log.d("teacherIds", teacherIds.toString())

                    if (teacherIds.isNotEmpty()) {
                        val teachers = postgrest.from("teacher").select(Columns.ALL) {
                            filter {
                                or {
                                    teacherIds.forEach { teacherId ->
                                        eq("id", teacherId)
                                    }
                                }
                            }
                        }.decodeList<TeacherDto>()

                        Log.d("teachers", teachers.toString())
                        teachers
                    } else {
                        emptyList()
                    }
                } else {
                    emptyList()
                }
            }
        } catch (e: Exception) {
            Log.e("loadTeachersForStudent", "Error loading teachers", e)
            emptyList()
        }
    }



    override suspend fun getStudentAdmin(idStudent: Int): AdminDto {
        return try {
            withContext(Dispatchers.IO) {
                val groupIdResult = postgrest.from("student").select(Columns.raw("id_groupe_fk")) {
                    filter {
                        eq("id", idStudent)
                    }
                }.decodeSingle<Map<String, Int>>()
                val groupId = groupIdResult["id_groupe_fk"]
                Log.d("groupId", groupId.toString())

                val filiereIdResult = postgrest.from("groupe").select(Columns.raw("id_filiere_fk")) {
                    filter {
                        if (groupId != null) {
                            eq("id", groupId)
                        }
                    }
                }.decodeSingle<Map<String, Int>>()
                val filiereId = filiereIdResult["id_filiere_fk"]
                Log.d("filiereId", filiereId.toString())

                val poleIdResult = postgrest.from("filiere").select(Columns.raw("id_pole_fk")) {
                    filter {
                        if (filiereId != null) {
                            eq("id", filiereId)
                        }
                    }
                }.decodeSingle<Map<String, Int>>()
                val poleId = poleIdResult["id_pole_fk"]
                Log.d("poleId", poleId.toString())

                val adminResult = postgrest.from("admin").select(Columns.ALL) {
                    filter {
                        if (poleId != null) {
                            eq("id_pole_fk", poleId)
                        }
                    }
                }.decodeSingle<AdminDto>()
                adminResult
            }
        } catch (e: Exception) {
            throw e
        }
    }


    override suspend fun sendRequest(request: RequestToSend): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                postgrest.from("request").insert(request)
                true
            }
            true
        } catch (e: java.lang.Exception) {
            throw e
        }
    }

    override suspend fun uploadFileToBucket(uri: Uri, fileName: String): String? {
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
                supabase.storage.from("justif_docs").upload(finalFileName, byteArray)
                "https://hvlzrnryaxhaudwlwvhf.supabase.co/storage/v1/object/public/justif_docs/$finalFileName"
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("uploadFileToBucket", "Error uploading file", e)
            null
        }
    }


    override suspend fun sendJustif(justif: JustifToSend): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                postgrest.from("justif").insert(justif)
                true
            }
        } catch (e: Exception) {
            Log.e("sendJustif", "Error sending justification", e)
            false
        }
    }


}