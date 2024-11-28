package com.service.mvisample.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
fun LandingScreen(
    onIntent: (HomeIntent) -> Unit,
    uiState: HomeUiState,
    navController: NavHostController) {
       val context = LocalContext.current

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row {
                            Text(
                                text = "Welcome, Ajaya",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            onIntent(HomeIntent.FetchUsers)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "User Icon",
                                tint = MaterialTheme.colorScheme.onPrimary
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
                                UserListing(userList = users?: arrayListOf() , onUserClick = {

                                }, modifier = Modifier.padding(10.dp))
                            }

                            errorMessage != null -> {
                            }
                        }

                    }
                }
            }
        )
}
@Composable
fun UserListing(userList: List<User>, onUserClick: (String) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(userList.size) { index ->
            userList.get(index).let {user->
                UserItem(userName = user.name, onClick = { onUserClick(user.name) })
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}


@Composable
fun UserItem(userName: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "User Icon",
            modifier = Modifier
                .size(40.dp)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), CircleShape)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = userName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
