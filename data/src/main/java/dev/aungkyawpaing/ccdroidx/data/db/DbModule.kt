package dev.aungkyawpaing.ccdroidx.data.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DbModule {

  @Provides
  @Singleton
  fun database(@ApplicationContext context: Context): CCDroidXDb {
    return Room.databaseBuilder(
      context,
      CCDroidXDb::class.java, "ccdroidx.db"
    ).addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()
  }

  @Provides
  fun projectTableDao(ccDroidXDb: CCDroidXDb): ProjectTableDao {
    return ccDroidXDb.projectTableDao()
  }

}