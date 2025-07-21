package com.example.baze.screens

import android.content.res.Configuration
import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.baze.R
import com.example.baze.data.FirestoreRepository
import com.example.baze.data.courseList


@Composable
fun CourseScreen(navHostController: NavHostController) {
    val scrollState = rememberScrollState()
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val context = LocalContext.current
    val repo = FirestoreRepository()

    // Firebase-backed state
    var selectedCourses by remember { mutableStateOf<List<String>>(emptyList()) }
    val selectedStates = remember {
        courseList.map { mutableStateOf(false) }
    }

    // Load from Firestore on first launch
    LaunchedEffect(Unit) {
        repo.getUserCourses(
            onSuccess = { userSelected ->
                selectedCourses = userSelected
                // Sync checkboxes with Firestore
                courseList.forEachIndexed { index, course ->
                    selectedStates[index].value = userSelected.contains(course)
                }
            },
            onFailure = {
                Toast.makeText(context, "Failed to load courses", Toast.LENGTH_SHORT).show()
            }
        )
    }

    Scaffold{ innerPadding ->

        Column(modifier = Modifier
            .padding(innerPadding)
            .background(Color.White).fillMaxSize()
            .verticalScroll(scrollState, enabled = isLandscape)) {

            Row(modifier = Modifier.padding(top = 10.dp)) {

                Image(
                    painter = painterResource(R.drawable.backbuttonselected),
                    contentDescription = "back button",
                    modifier = Modifier
                        .size(70.dp)
                        .clickable(onClick = { navHostController.navigate(route = "home")})
                )

                Spacer(modifier = Modifier.width(60.dp))

                Text(
                    text = "BAZE",
                    fontSize = 50.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.size(10.dp))


            Text(
                text = "Courses",
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp,
                color = Color.Blue,
                modifier = Modifier.padding(20.dp)
            )

            Spacer(modifier = Modifier.size(10.dp))


            Column {

                // Dynamic course rows
                courseList.forEachIndexed { index, courseName ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = courseName,
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(horizontal = 25.dp)
                                .padding(top = 10.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        CourseCheckbox(
                            isSelected = selectedStates[index].value,
                            onSelect = {
                                selectedStates[index].value = !selectedStates[index].value
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            Button(onClick = {
                val selected = courseList.filterIndexed { index, _ ->
                    selectedStates[index].value
                }

                repo.saveUserCourses(
                    selected,
                    onSuccess = {
                        Toast.makeText(context, "Courses saved", Toast.LENGTH_SHORT).show()
                        navHostController.navigate(route = "home")
                    },
                    onFailure = {
                        Toast.makeText(context, "Failed to save courses", Toast.LENGTH_SHORT).show()
                    }
                )
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(Color.Blue,
                    contentColor = Color.White)
            ) {
                Text(text = "Submit",
                    fontSize = 20.sp)
            }

        }
    }
}


@Composable
fun CourseCheckbox(
    isSelected: Boolean,
    onSelect: () -> Unit
){
    Checkbox(checked = isSelected,
        onCheckedChange = { onSelect()},
        colors = CheckboxDefaults.colors(
            checkedColor = Color.Black,
            checkmarkColor = Color.White,
            uncheckedColor = Color. Black))
}