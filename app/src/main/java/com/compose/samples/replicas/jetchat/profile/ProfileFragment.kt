package com.compose.samples.replicas.jetchat.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.compose.samples.replicas.jetchat.FunctionalityNotAvailablePopup
import com.compose.samples.replicas.jetchat.MainViewModel

import com.compose.samples.replicas.jetchat.R
import com.compose.samples.replicas.jetchat.components.JetchatAppBar
import com.compose.samples.replicas.jetchat.ui.theme.JetchatTheme

class ProfileFragment: Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Consider using safe args plugin
        val userId = arguments?.getString("userId")
        viewModel.setUserId(userId)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val rootView: View = inflater.inflate(R.layout.fragment_profile, container, false)

        rootView.findViewById<ComposeView>(R.id.toolbar_compose_view).apply {
            setContent {
                var functionalityNotAvailablePopupShown by remember { mutableStateOf(false) }
                if(functionalityNotAvailablePopupShown) {
                    FunctionalityNotAvailablePopup {
                        functionalityNotAvailablePopupShown = false
                    }
                }

                JetchatTheme {
                    JetchatAppBar(
                        // Reset the minimum bounds that are passed to the root of a compose tree
                        modifier = Modifier
                            .wrapContentSize(),
                        onNavIconPressed = { activityViewModel.openDrawer() },
                        title = {},
                        actions = {
                            // More Icon
                            Icon(
                                imageVector = Icons.Outlined.MoreVert,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                contentDescription = stringResource(id = R.string.more_options),
                                modifier = Modifier
                                    .clickable(onClick = {
                                        functionalityNotAvailablePopupShown = true
                                    })
                                    .padding(horizontal = 12.dp, vertical = 16.dp)
                                    .height(24.dp)
                            )
                        }
                    )
                }
            }
        }

        rootView.findViewById<ComposeView>(R.id.profile_compose_view).apply {
            setContent {
                val userData by viewModel.userData.observeAsState()
                val nestedScrollInteropConnection = rememberNestedScrollInteropConnection()

                JetchatTheme {
                    if(userData == null) {
                        ProfileError()
                    } else {
                        ProfileScreen(
                            userData = userData!!,
                            nestedScrollInteropConnection = nestedScrollInteropConnection
                        )
                    }
                }
            }
        }

        return rootView
    }
}

@Composable
fun ProfileError() {
    Text(stringResource(R.string.profile_error))
}