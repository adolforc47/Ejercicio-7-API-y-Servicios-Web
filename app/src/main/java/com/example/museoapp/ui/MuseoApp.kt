
package com.example.museoapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.museoapp.ui.screens.HomeScreen
import com.example.museoapp.viewmodel.MuseoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MuseoApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val museoViewModel: MuseoViewModel = viewModel()

    Scaffold(
        modifier = Modifier.nestedScroll(
            scrollBehavior.nestedScrollConnection
        ),
        topBar = {
            MuseoTopBar(scrollBehavior = scrollBehavior)
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            HomeScreen(
                museoUiState = museoViewModel.museoUiState,
                contentPadding = innerPadding
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MuseoTopBar(
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "MuseoApp")
        },
        scrollBehavior = scrollBehavior
    )
}
