package com.example.baze.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.baze.R
import com.example.baze.viewModel.AnnouncementViewModel
import com.example.baze.viewModel.AuthViewModel


@Composable
fun Announcements(authViewModel: AuthViewModel, navHostController: NavHostController){
    val scrollState = rememberScrollState()
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    val announcementViewModel : AnnouncementViewModel = remember { AnnouncementViewModel() }
    val announcementList = announcementViewModel.announcements.observeAsState(emptyList())
    val userProfile = authViewModel.userProfile.observeAsState()

    userProfile.value?.let { profile ->
        LaunchedEffect(profile.selectedCourses) {
            announcementViewModel.fetchAnnouncements(profile.selectedCourses)
        }
    }

    Scaffold { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(scrollState, enabled = isLandscape)

        ) {

            Row(modifier = Modifier.padding(top = 10.dp)) {

                Image(
                    painter = painterResource(R.drawable.backbuttonselected),
                    contentDescription = "back button",
                    modifier = Modifier.size(70.dp)
                        .clickable(onClick = { navHostController.navigate(route = "home")})
                )

                Spacer(modifier = Modifier.width(40.dp))

                Text(
                    text = "BAZE",
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(40.dp))

                Column(horizontalAlignment = Alignment.End) {

                    if(userProfile.value?.role == "Faculty"){
                        Icon(imageVector = Icons.Default.Add,
                            contentDescription = "Add Announcement",
                            tint = Color.Black,
                            modifier = Modifier.padding(top = 5.dp)
                                .size(40.dp)
                                .clickable(onClick = {navHostController.navigate(route = "makeAnnouncement")}))

                        Text(text = "Add",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                    }

                }
            }


            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = "Announcement",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color.Blue,
                modifier = Modifier.padding(20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = Color.Gray, thickness = 2.dp)

            announcementList.value
                .filter { ann -> userProfile.value?.selectedCourses?.contains(ann.courseId) == true }
                .forEach { announcement ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "📘 Course: ${announcement.courseId}",
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "👨‍🏫 Faculty: ${announcement.facultyName}",
                            fontSize = 14.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(
                            text = "📢 Message: ${announcement.message}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                        )
                        HorizontalDivider(color = Color.Gray, thickness = 2.dp)
                    }
                }
        }
    }
}