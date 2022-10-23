package dev.aungkyawpaing.wear.datalayer

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dev.aungkyawpaing.ccdroidx.common.BuildStatus
import dev.aungkyawpaing.ccdroidx.common.Project
import javax.inject.Inject

class MiniProjectSerializer @Inject constructor() {

  private val moshi = Moshi.Builder().build()
  private val adapter: JsonAdapter<List<MiniProject>> = moshi.adapter(
    Types.newParameterizedType(
      MutableList::class.java, MiniProject::class.java
    )
  )

  fun serializeProjects(project: List<Project>): String {

    return serializeMiniProjects(project.map {
      MiniProject(
        name = it.name, isSuccess = it.lastBuildStatus == BuildStatus.SUCCESS
      )
    })
  }

  fun serializeMiniProjects(projects: List<MiniProject>): String {
    return adapter.toJson(projects)
  }

  fun deserializeProjects(jsonString: String): List<MiniProject> {
    return adapter.fromJson(jsonString) ?: emptyList()
  }

}