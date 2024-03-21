package dev.aungkyawpaing.ccdroidx.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
  override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("ALTER TABLE ProjectTable ADD isMuted INTEGER NOT NULL DEFAULT 0;")
    database.execSQL("ALTER TABLE ProjectTable ADD mutedUntil INTEGER")
  }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
  override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("ALTER TABLE ProjectTable ADD username TEXT DEFAULT NULL;")
    database.execSQL("ALTER TABLE ProjectTable ADD password TEXT DEFAULT NULL;")
  }
}


@Database(entities = [ProjectTable::class], version = 3)
@TypeConverters(Converters::class)
abstract class CCDroidXDb : RoomDatabase() {
  abstract fun projectTableDao(): ProjectTableDao
}