package br.senai.sp.jandira.lionschool

import android.content.Intent
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.model.Course
import br.senai.sp.jandira.lionschool.model.CourseList
import br.senai.sp.jandira.lionschool.service.RetrofitFactory
import br.senai.sp.jandira.lionschool.ui.theme.ui.theme.LionSchoolTheme
import coil.compose.AsyncImage
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

    val context = LocalContext.current

    var searchState by remember {
        mutableStateOf("")
    }

    var courseState by remember {
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
            listcourse = response.body()!!.cursos
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
                    Button(
                        onClick = {
                            val openStudentsActivity = Intent(context, StudentsActivity::class.java)
                            openStudentsActivity.putExtra("Curso", "${it.sigla}")
                            openStudentsActivity.putExtra("Nome_Curso", "${it.nome}")
                            context.startActivity(openStudentsActivity)
                        },
                        modifier = Modifier
                            .height(190.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(Color(51, 71, 176))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 20.dp),
                            verticalArrangement = Arrangement.SpaceEvenly

                        ) {
                            Row(
                                modifier = Modifier
                                    .width(250.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = it.icone,
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(Color.White),
                                    modifier = Modifier.height(70.dp)
                                )

                                Spacer(modifier = Modifier.width(20.dp))

                                Text(
                                    text = "${it.sigla}",
                                    fontSize = 60.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraBold,
                                    textAlign = TextAlign.Left
                                )
                            }

                            Text(
                                text = "${it.nome}".substring(6),
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold

                            )
                        }
                    }

                    
                    Spacer(modifier = Modifier.height(20.dp))
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