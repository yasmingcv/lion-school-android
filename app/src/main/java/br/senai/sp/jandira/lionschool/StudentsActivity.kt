package br.senai.sp.jandira.lionschool

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.model.Student
import br.senai.sp.jandira.lionschool.model.StudentList
import br.senai.sp.jandira.lionschool.service.RetrofitFactory
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                val siglaCurso = intent.getStringExtra("Curso")
                StudentsScreen(siglaCurso.toString())
            }
        }
    }
}

@Composable
fun StudentsScreen(sigla: String) {

    var listStudent by remember {
        mutableStateOf(listOf<Student>())
    }

    val call = RetrofitFactory().getStudentService().getStudents(sigla)

    call.enqueue(object : Callback<StudentList>{
        override fun onResponse(
            call: Call<StudentList>,
            response: Response<StudentList>
        ) {
            listStudent = response.body()!!.alunos
        }

        override fun onFailure(call: Call<StudentList>, t: Throwable) {
            Log.i(
                "ds2t",
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


        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview3() {
    LionSchoolTheme {
        StudentsScreen("ds")
    }
}