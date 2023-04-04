package dev.aungkyawpaing.ccdroidx.feature.notification.prompt

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CircleNotifications
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.themeadapter.material3.Mdc3Theme
import dev.aungkyawpaing.ccdroidx.R

@Composable
fun NotificationPromptCard(
  onDismissPrompt: () -> Unit,
  onEnableNotification: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    ConstraintLayout(
      modifier = Modifier
        .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
        .fillMaxWidth()
    ) {

      val (closeIcon, notificationIcon, bodyText, enableButton) = createRefs()

      Icon(imageVector = Icons.Filled.Close,
        tint = MaterialTheme.colorScheme.secondary,
        contentDescription = stringResource(id = R.string.notification_prompt_close_content_description),
        modifier = Modifier
          .size(48.dp)
          .clickable {
            onDismissPrompt()
          }
          .padding(12.dp)
          .constrainAs(closeIcon) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
          }
      )

      Icon(imageVector = Icons.Filled.CircleNotifications, contentDescription = null,
        Modifier
          .constrainAs(notificationIcon) {
            start.linkTo(parent.start)
            linkTo(top = closeIcon.bottom, bottom = enableButton.top, bias = 0.0f)
          }
          .size(48.dp)
      )

      Text(
        text = stringResource(id = R.string.notification_prompt_body),
        modifier = Modifier
          .constrainAs(bodyText) {
            start.linkTo(notificationIcon.end)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            linkTo(top = closeIcon.bottom, bottom = enableButton.top, bias = 0.0f)
          }
          .padding(start = 8.dp)
      )

      TextButton(onClick = {
        onEnableNotification()
      }, modifier = Modifier
        .constrainAs(enableButton) {
          bottom.linkTo(parent.bottom)
          end.linkTo(parent.end)
        }) {
        Text(text = stringResource(id = R.string.notification_prompt_enable_notification).uppercase())
      }
    }
  }
}

@Preview
@Composable
fun NotificationPromptCardPreview() {
  Mdc3Theme {
    NotificationPrompt()
  }
}