package dev.aungkyawpaing.ccdroidx.db

import android.content.Context
import com.squareup.sqldelight.EnumColumnAdapter
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.aungkyawpaing.ccdroidx.CCDroidXDb
import dev.aungkyawpaing.ccdroidx.ProjectTable
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DbModule {

  @Provides
  @Singleton
  fun provideSqlDriver(@ApplicationContext context: Context): SqlDriver {
    return AndroidSqliteDriver(CCDroidXDb.Schema, context, "ccdroidx.db")
  }

  @Provides
  @Singleton
  fun database(driver: SqlDriver): CCDroidXDb {
    return CCDroidXDb(
      driver = driver,
      ProjectTableAdapter = ProjectTable.Adapter(
        activityAdapter = EnumColumnAdapter(),
        lastBuildStatusAdapter = EnumColumnAdapter(),
        lastBuildTimeAdapter = ZonedDateTimeColumnAdapter,
        nextBuildTimeAdapter = ZonedDateTimeColumnAdapter
      )
    )
  }
}