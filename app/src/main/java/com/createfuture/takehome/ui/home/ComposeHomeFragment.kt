package com.createfuture.takehome.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.createfuture.takehome.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

// TODO Refactor to remove fragment and create view models and screen via NavigationStack as we don't require fragments
@AndroidEntryPoint
class ComposeHomeFragment : Fragment() {

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): ComposeView = ComposeView(requireContext()).apply {
        // TODO need to offset the content padding to the activity layout (button overlaps content)
        setContent {
            AppTheme {
                HomeScreen(homeViewModel)
            }
        }
    }
}