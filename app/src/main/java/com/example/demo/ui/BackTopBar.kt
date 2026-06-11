package com.example.demo.ui

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopBar(title: String, showBack: Boolean = true) {
    val activity = LocalActivity.current
    TopAppBar(
        title = {
            Column {
                Text(title)
                Text(
                    text = activity?.let {
                        "${it.javaClass.simpleName}@${Integer.toHexString(System.identityHashCode(it))}"
                    }.orEmpty(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        navigationIcon = {
            if (showBack) {
                IconButton(onClick = { activity?.finish() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}
