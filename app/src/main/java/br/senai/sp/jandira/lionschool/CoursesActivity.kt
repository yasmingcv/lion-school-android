package br.senai.sp.jandira.lionschool

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.model.Course
import br.senai.sp.jandira.lionschool.model.CourseList
import br.senai.sp.jandira.lionschool.service.RetrofitFactory
import br.senai.sp.jandira.lionschool.ui.theme.ui.theme.LionSchoolTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoursesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                CoursesScreen()
            }
        }
    }
}

@Composable
fun CoursesScreen() {

    var searchState by remember {
        mutableStateOf("")
    }

    var listcourse by remember {
        mutableStateOf(listOf<Course>())
    }

    val call = RetrofitFactory().getCourseService().getCourse()

    call.enqueue(object : Callback<CourseList> {
        override fun onResponse(
            call: Call<CourseList>,
            response: Response<CourseList>
        ) {
            listcourse = response.body()!!.result
        }

        override fun onFailure(call: Call<CourseList>, t: Throwable) {
            Log.i(
                "lista de cursos",
                "${t.message}"
            )
        }
    })



    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.logo_image),
                    contentDescription = null,
                    modifier = Modifier.width(60.dp)
                )
                Column() {
                    Text(
                        text = stringResource(id = R.string.lion_school),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(51, 71, 176),
                        modifier = Modifier.padding(start = 29.dp)
                    )

                    Box(
                        modifier = Modifier
                            .padding(30.dp)
                            .width(250.dp)
                            .height(2.dp)
                            .background(Color(255, 194, 62))
                    )
                }
            }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = searchState,
                onValueChange = { searchState = it },
                shape = RoundedCornerShape(20.dp),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = stringResource(id = R.string.find_course))
                }
            )

            Text(
                text = stringResource(id = R.string.courses),
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color(51, 71, 176),
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
            )

            LazyColumn() {
                items(listcourse) {
                    //Course's card
                    Card(
                        modifier = Modifier
                            .height(190.dp)
                            .fillMaxWidth(),
                        backgroundColor = Color(51, 71, 176),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp),
                            verticalArrangement = Arrangement.SpaceEvenly

                        ) {
                            Row(
                                modifier = Modifier
                                    .width(180.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_code_24),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(90.dp)
                                )
                                Text(
                                    text = "DS",
                                    fontSize = 60.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Text(
                                text = "Técnico em desenvolvimento de sistemas",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold

                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_access_time_filled_24),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )

                                Text(
                                    text = "1200h",
                                    color = Color.White,
                                    fontSize = 22.sp,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }
                }
            }


        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview2() {
    LionSchoolTheme {
        CoursesScreen()
    }
}