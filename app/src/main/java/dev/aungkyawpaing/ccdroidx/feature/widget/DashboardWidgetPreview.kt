package dev.aungkyawpaing.ccdroidx.feature.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.themeadapter.material3.Mdc3Theme
import dev.aungkyawpaing.ccdroidx.R

/**
 * Glance doesn't support preview for now, so we're writing a minimal preview back in compose ui
 * This preview image used as widget preview image so changing this file require you to update the
 * widget preview image
 */
@Preview
@Composable
fun DashboardWidgetPreview() {
  Mdc3Theme {
    Column(
      modifier = Modifier
        .size(150.dp, 225.dp)
        .background(MaterialTheme.colorScheme.background)
    ) {

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically
      ) {
        val title = "2 Red"
        Image(
          Icons.Filled.Refresh,
          null,
          colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
          modifier = Modifier
            .size(48.dp)
            .padding(12.dp)
        )

        Text(
          "3 Red",
          style = MaterialTheme.typography.titleMedium,
          color = MaterialTheme.colorScheme.onPrimary
        )
      }

      val exampleProjects = listOf(
        "lisan/al-giab",
        "anakin/sky-walker",
        "answer/to-be-rich-is",
        "ccdroidx/ccdroidx-pipeline",
        "you-cant/see-this"
      )

      LazyColumn(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(exampleProjects) { projectName ->
          Column {
            Box(
              modifier = Modifier
                .background(
                  colorResource(id = R.color.build_fail), RoundedCornerShape(8.dp)
                ),
            ) {
              Text(
                projectName,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onError,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(8.dp)
              )
            }
          }
        }
      }
    }
  }
}
