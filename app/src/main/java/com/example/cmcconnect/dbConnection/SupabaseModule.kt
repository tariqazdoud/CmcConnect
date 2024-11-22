package com.example.cmcconnect.dbConnection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.FlowType
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SupabaseModule {
    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl  = "",
            supabaseKey  = ""
        ){
            install(Postgrest)
            install(Storage)
            install(Auth){
                flowType = FlowType.PKCE
                scheme="app"
                host="supabase.com"

            }

        }
    }
    @Provides
    @Singleton
    fun provideSupabaseDatabase(client: SupabaseClient):Postgrest{
        return client.postgrest
    }
    @Provides
    @Singleton
    fun provideSupabaseAuth(client: SupabaseClient):Auth{
        return client.auth
    }
    @Provides
    @Singleton
    fun provideSupabaseStorage(client: SupabaseClient):Storage{
        return client.storage
    }
}