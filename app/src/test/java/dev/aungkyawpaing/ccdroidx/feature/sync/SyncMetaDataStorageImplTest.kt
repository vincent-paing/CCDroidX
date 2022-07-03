package dev.aungkyawpaing.ccdroidx.feature.sync

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.aungkyawpaing.ccdroidx.SyncedStateProto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class SyncMetaDataStorageImplTest {

  lateinit var context: Context

  lateinit var syncMetaDataStorage: SyncMetaDataStorageImpl

  @Before
  fun setUp() {
    context = getApplicationContext<Application>().applicationContext
    syncMetaDataStorage = SyncMetaDataStorageImpl(context)
  }

  @Test
  fun `save correctly`() = runTest {
    val dateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(1000L), ZoneOffset.UTC)

    val inputAndExpectedSyncedState = listOf(
      Pair(LastSyncedState.SYNCING, SyncedStateProto.Status.SYNCING),
      Pair(LastSyncedState.SUCCESS, SyncedStateProto.Status.SUCCESS),
      Pair(LastSyncedState.FAILED, SyncedStateProto.Status.FAILED)
    )
    var assertionCount = 0
    inputAndExpectedSyncedState.forEach { (lastSyncedState, expectedState) ->
      syncMetaDataStorage.saveLastSyncedTime(
        LastSyncedStatus(
          lastSyncedDateTime = dateTime,
          lastSyncedState = lastSyncedState,
          errorCode = 1,
        )
      )

      val expected = SyncedStateProto(
        lastSyncedDateTime = dateTime.toInstant().toEpochMilli(),
        status = expectedState,
        errorCode = 1
      )

      val actual = context.lastSyncedStateDataStore.data.first()

      Assert.assertEquals(expected, actual)
      assertionCount++
    }
    Assert.assertEquals(3, assertionCount)
  }

  @Test
  fun `get correctly`() = runTest {
    val dateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(1000L), ZoneId.systemDefault())

    val inputAndExpectedState = listOf(
      Pair(SyncedStateProto.Status.SYNCING, LastSyncedState.SYNCING),
      Pair(SyncedStateProto.Status.SUCCESS, LastSyncedState.SUCCESS),
      Pair(SyncedStateProto.Status.FAILED, LastSyncedState.FAILED)

    )
    var assertionCount = 0
    inputAndExpectedState.forEach { (syncedState, expectedState) ->

      context.lastSyncedStateDataStore.updateData { _ ->
        SyncedStateProto(
          lastSyncedDateTime = dateTime.toInstant().toEpochMilli(),
          status = syncedState,
          errorCode = 1
        )
      }

      val expected = LastSyncedStatus(
        lastSyncedDateTime = dateTime,
        lastSyncedState = expectedState,
        errorCode = 1
      )

      val actual = syncMetaDataStorage.getLastSyncedTime().first()

      Assert.assertEquals(expected, actual)
      assertionCount++
    }
    Assert.assertEquals(3, assertionCount)
  }
}