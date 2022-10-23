package dev.aungkyawpaing.ccdroidx.feature.add.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.composethemeadapter3.Mdc3Theme
import dev.aungkyawpaing.ccdroidx._testhelper_.ProjectBuilder
import dev.aungkyawpaing.ccdroidx.common.Project
import dev.aungkyawpaing.ccdroidx.roboeletric.FakeAndroidKeyStore
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(
  instrumentedPackages = ["androidx.loader.content"]
)
class SelectProjectListTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  companion object {
    @JvmStatic
    @BeforeClass
    fun beforeClass() {
      FakeAndroidKeyStore.setup
    }
  }

  @Test
  fun `render Project List`() {

    val projectList = listOf(
      ProjectBuilder.buildProject(id = 0L, name = "project 1"),
      ProjectBuilder.buildProject(id = 1L, name = "project 2")
    )
    composeTestRule.setContent {
      Mdc3Theme {
        SelectProjectList(projectList = projectList, onProjectSelect = {})
      }
    }

    composeTestRule.onNodeWithText("project 1").assertIsDisplayed()
    composeTestRule.onNodeWithText("project 2").assertIsDisplayed()
  }

  @Test
  fun `invoke onProjectSelect on clicking an item`() {

    val project = ProjectBuilder.buildProject(id = 0L, name = "project 1")

    val projectList = listOf(
      project,
      ProjectBuilder.buildProject(id = 1L, name = "project 2")
    )

    var captureProject: Project? = null

    composeTestRule.setContent {
      Mdc3Theme {
        SelectProjectList(projectList = projectList, onProjectSelect = { project ->
          captureProject = project
        })
      }
    }

    composeTestRule.onNodeWithText("project 1").assertIsDisplayed()
    composeTestRule.onNodeWithText("project 1").performClick()

    Assert.assertEquals(project, captureProject)
  }
}