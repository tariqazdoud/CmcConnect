package com.example.cmcconnect.dbConnection

import android.content.Context
import com.example.cmcconnect.repository.adminRepository.AdminRepository
import com.example.cmcconnect.repository.adminRepository.AdminRepositoryImpl
import com.example.cmcconnect.repository.sharedRepository.AuthenticationRepository
import com.example.cmcconnect.repository.sharedRepository.AuthenticationRepositoryImp
import com.example.cmcconnect.repository.sharedRepository.EventRepository
import com.example.cmcconnect.repository.sharedRepository.EventRepositoryImp
import com.example.cmcconnect.repository.sharedRepository.UserRepository
import com.example.cmcconnect.repository.sharedRepository.UserRepositoryImp
import com.example.cmcconnect.repository.studentRepository.StudentRepository
import com.example.cmcconnect.repository.studentRepository.StudentRepositoryImp
import com.example.cmcconnect.repository.teacherRepository.TeacherRepository
import com.example.cmcconnect.repository.teacherRepository.TeacherRepositoryImpl
import com.example.cmcconnect.repository.testRepo
import com.example.cmcconnect.repository.testRepoImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
    @Provides
    fun provideTestRepo(postgrest: Postgrest): testRepo = testRepoImp(postgrest)
    @Provides
    fun provideSignInRepository(auth: Auth): AuthenticationRepository = AuthenticationRepositoryImp(auth)
    @Provides
    fun provideUserRepository(auth: Auth, postgrest: Postgrest) : UserRepository = UserRepositoryImp(auth, postgrest)
    @Provides
    fun provideEventRepository(postgrest: Postgrest): EventRepository = EventRepositoryImp(postgrest)
    @Provides
    fun provideStudentRepository(postgrest: Postgrest, @ApplicationContext context: Context, supabase: SupabaseClient): StudentRepository = StudentRepositoryImp(context, postgrest, supabase)

    @Provides
    fun providesTeacherRepository(postgrest: Postgrest, @ApplicationContext context: Context, supabase: SupabaseClient): TeacherRepository = TeacherRepositoryImpl(context, postgrest, supabase)
    @Provides
    fun provideAdminRepository(postgrest: Postgrest):AdminRepository = AdminRepositoryImpl(postgrest)
}