package com.compose.samples.replicas.jetchat.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min

import com.compose.samples.replicas.jetchat.R
import com.compose.samples.replicas.jetchat.data.colleagueProfile
import com.compose.samples.replicas.jetchat.data.meProfile
import com.compose.samples.replicas.jetchat.ui.theme.JetchatTheme

@Composable
fun JetchatDrawerContent(
    onProfileClicked: (String) -> Unit,
    onChatClicked: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        DrawerHeader()
        JetchatDivider()
        DrawerItemHeader("Chats")
        ChatItem(text = "composers", selected = true, onChatClicked = { onChatClicked("composers") })
        ChatItem(text = "droidcon-nyc", selected = false, onChatClicked = { onChatClicked("droidcon-nyc") })
        JetchatDivider(modifier = Modifier.padding(horizontal = 28.dp))
        DrawerItemHeader(
            text = "Recent Profiles"
        )
        ProfileItem("Ali Conors (you)", meProfile.photo) { onProfileClicked(meProfile.userId) }
        ProfileItem("Taylor Brooks", colleagueProfile.photo) {
            onProfileClicked(colleagueProfile.userId)
        }
    }
}

@Composable
private fun DrawerHeader() {
    Row(
        modifier = Modifier
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        JetchatIcon(
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.jetchat_logo),
            contentDescription = null,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
private fun DrawerItemHeader(
    text: String
) {
    Box(
        modifier = Modifier
            .heightIn(min = 52.dp)
            .padding(28.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ChatItem(
    text: String,
    selected: Boolean,
    onChatClicked: () -> Unit
) {
    val background = if(selected) {
        Modifier.background(MaterialTheme.colorScheme.primaryContainer)
    } else {
        Modifier
    }

    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clip(CircleShape)
            .then(background)
            .clickable(onClick = onChatClicked),
        verticalAlignment = Alignment.CenterVertically
    ) {

        val iconTint = if(selected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_jetchat),
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = if(selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface
            },
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
private fun ProfileItem(
    text: String,
    @DrawableRes profilePic: Int?,
    onProfileClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clip(CircleShape)
            .clickable(onClick = onProfileClicked),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val paddingSizeModifier = Modifier
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
            .size(24.dp)

        if(profilePic != null) {
            Image(
                painter = painterResource(id = profilePic),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = paddingSizeModifier.then(Modifier.clip(CircleShape))
            )
        } else {
            Spacer(modifier = paddingSizeModifier)
        }

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
@Preview(showSystemUi = true)
fun DrawerPreviewDark() {
    JetchatTheme(isDarkTheme = false) {
        Surface {
            Column {
                JetchatDrawerContent({}, {})
            }
        }
    }
}