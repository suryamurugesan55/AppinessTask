package com.surya.appinesstask.di

import android.content.Context
import android.content.SharedPreferences
import com.surya.appinesstask.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PrefModule {
    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(context.getString(R.string.app_name), 0)
    }

    @Provides
    @Singleton
    fun provideEditorPreference(preferences: SharedPreferences): SharedPreferences.Editor {
        return preferences.edit()
    }
}