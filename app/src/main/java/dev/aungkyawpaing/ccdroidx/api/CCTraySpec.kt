package dev.aungkyawpaing.ccdroidx.api

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(strict = false, name = "Projects")
class CCTrayProjects {
  @field:ElementList(name = "Project", inline = true, required = false)
  var project: List<CCTrayProject>? = null
}

@Root(strict = false, name = "Project")
class CCTrayProject {
  @field:Attribute(name = "name", required = true)
  var name: String? = null

  @field:Attribute(name = "activity", required = true)
  var activity: String? = null

  @field:Attribute(name = "lastBuildStatus", required = true)
  var lastBuildStatus: String? = null

  @field:Attribute(name = "lastBuildLabel", required = false)
  var lastBuildLabel: String? = null

  @field:Attribute(name = "lastBuildTime", required = true)
  var lastBuildTime: String? = null

  @field:Attribute(name = "nextBuildTime", required = false)
  var nextBuildTime: String? = null

  @field:Attribute(name = "webUrl", required = true)
  var webUrl: String? = null

}

