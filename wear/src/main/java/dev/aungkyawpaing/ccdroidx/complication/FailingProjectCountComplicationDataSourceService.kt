/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.aungkyawpaing.ccdroidx.complication


import androidx.wear.watchface.complications.data.ComplicationData
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.data.PlainComplicationText
import androidx.wear.watchface.complications.data.ShortTextComplicationData
import androidx.wear.watchface.complications.datasource.ComplicationRequest
import androidx.wear.watchface.complications.datasource.SuspendingComplicationDataSourceService
import dagger.hilt.android.AndroidEntryPoint
import dev.aungkyawpaing.ccdroidx.ProjectDataStore
import dev.aungkyawpaing.ccdroidx.R
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class FailingProjectCountComplicationDataSourceService : SuspendingComplicationDataSourceService() {

  @Inject
  lateinit var projectDataStore: ProjectDataStore

  private val title by lazy {
    getString(R.string.failing_project_count_complication_title)
  }

  private val contentDescription by lazy {
    getString(R.string.failing_project_count_complication_content_description)
  }

  override fun getPreviewData(type: ComplicationType): ComplicationData? {
    return ShortTextComplicationData.Builder(
      text = PlainComplicationText.Builder(text = "5").build(),
      contentDescription = PlainComplicationText.Builder(text = contentDescription).build()
    ).setTitle(
      PlainComplicationText.Builder(title).build()
    ).setTapAction(null).build()
  }

  override suspend fun onComplicationRequest(request: ComplicationRequest): ComplicationData? {
    val failingCount = projectDataStore.getFailingProjectCount().first()

    return when (request.complicationType) {
      ComplicationType.SHORT_TEXT -> ShortTextComplicationData.Builder(
        text = PlainComplicationText.Builder(text = failingCount.toString()).build(),
        contentDescription = PlainComplicationText.Builder(text = contentDescription).build()
      ).setTitle(PlainComplicationText.Builder(title).build()).setTapAction(null).build()
      else -> {
        return null
      }
    }
  }


}
