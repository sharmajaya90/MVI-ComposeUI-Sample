package com.service.mvisample.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.service.mvisample.intent.HomeIntent
import com.service.mvisample.model.User
import com.service.mvisample.view.viewstate.HomeUiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedScreen(
    uiState: HomeUiState,
    navController: NavHostController) {

        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon =  {
                        IconButton(onClick = {
                            navController.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back Icon",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    title = {
                        Row {
                            Text(
                                text = "Welcome, ${uiState.selectedUser?.name}",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                )
            },
            content = { padding ->
                Box(
                    modifier = Modifier.padding(padding)) {

                    with(uiState){
                        when{
                            loading == true->{
                                Box(modifier = Modifier.fillMaxSize()) {
                                    CircularProgressIndicator(
                                        color = Color.Red,
                                        modifier = Modifier
                                            .size(100.dp)
                                            .fillMaxHeight()
                                            .align(Alignment.Center)
                                            .padding(16.dp)
                                    )
                                }
                            }

                            defaultScreen != null -> {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    Text(
                                        text = "${defaultScreen}",
                                        color = Color.Red
                                    )
                                }
                            }
                            loading == false && errorMessage == null->{
                                Box(modifier = Modifier.fillMaxSize()) {
                                    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "User Icon",
                                            modifier = Modifier
                                                .size(40.dp)
                                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), CircleShape)
                                                .padding(8.dp)
                                        )
                                        Text(
                                            text =  "${selectedUser?.name}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.Red
                                        )
                                        Text(
                                            text =  "${selectedUser?.email}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.Red
                                        )
                                    }
                                }
                            }

                            errorMessage != null -> {
                            }
                        }

                    }
                }
            }
        )
}
