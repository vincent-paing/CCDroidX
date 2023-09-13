package dev.aungkyawpaing.ccdroidx.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun DialogScaffold(
  title: @Composable (() -> Unit),
  content: @Composable (() -> Unit),
  confirmButton: @Composable (() -> Unit)?,
  dismissButton: @Composable (() -> Unit)?,
  modifier: Modifier = Modifier
) {
  // Width and padding set according to https://m3.material.io/components/dialogs/specs
  Card(
    shape = RoundedCornerShape(28.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceVariant,
      contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ),
    modifier = modifier
      .widthIn(min = 280.dp, max = 560.dp)
      .wrapContentHeight()
      .zIndex(8.0f)
  ) {
    Column(
      modifier = Modifier.padding(24.dp)
    ) {
      title()

      Box(modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)) {
        content()
      }

      Row(
        modifier = Modifier.align(Alignment.End),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        dismissButton?.let { it() }
        confirmButton?.let { it() }
      }
    }
  }
}