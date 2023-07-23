package com.github.kitakkun.mirrorcomment.ui.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.useResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.mikepenz.aboutlibraries.ui.compose.LibrariesContainer

class LicenseScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "OSSライセンス")
                    },
                    navigationIcon = {
                        IconButton(onClick = { navigator?.pop() }) {
                            Icon(imageVector = Icons.Default.ArrowBackIos, contentDescription = null)
                        }
                    }
                )
            },
        ) { innerPadding ->
            LibrariesContainer(
                aboutLibsJson = useResource("aboutlibraries.json") {
                    it.bufferedReader().readText()
                },
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            )
        }
    }
}
