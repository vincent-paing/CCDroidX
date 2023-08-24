package dev.aungkyawpaing.ccdroidx.feature.add.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dev.aungkyawpaing.ccdroidx._testhelper_.ProjectBuilder
import dev.aungkyawpaing.ccdroidx.common.Project
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.DisplayName

class SelectProjectListTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  @DisplayName("render Project List")
  fun renderProjectList() {

    val projectList = listOf(
      ProjectBuilder.buildProject(id = 0L, name = "project 1"),
      ProjectBuilder.buildProject(id = 1L, name = "project 2")
    )
    composeTestRule.setContent {
      SelectProjectList(projectList = projectList, onProjectSelect = {})
    }

    composeTestRule.onNodeWithText("project 1").assertIsDisplayed()
    composeTestRule.onNodeWithText("project 2").assertIsDisplayed()
  }

  @Test
  @DisplayName("invoke onProjectSelect on clicking an item")
  fun invokeOnProjectSelectOnClickingAnItem() {

    val project = ProjectBuilder.buildProject(id = 0L, name = "project 1")

    val projectList = listOf(
      project,
      ProjectBuilder.buildProject(id = 1L, name = "project 2")
    )

    var captureProject: Project? = null

    composeTestRule.setContent {
      SelectProjectList(projectList = projectList, onProjectSelect = { project ->
        captureProject = project
      })
    }

    composeTestRule.onNodeWithText("project 1").assertIsDisplayed()
    composeTestRule.onNodeWithText("project 1").performClick()

    Assert.assertEquals(project, captureProject)
  }
}