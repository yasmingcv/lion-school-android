package br.senai.sp.jandira.lionschool

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.model.Course
import br.senai.sp.jandira.lionschool.model.CourseList
import br.senai.sp.jandira.lionschool.model.Student
import br.senai.sp.jandira.lionschool.service.RetrofitFactory
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentDescription : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                var nomeAluno = intent.getStringExtra("Aluno")
                var matriculaAluno = intent.getStringExtra("Aluno_matricula")
                StudentDescriptionScreen(nomeAluno.toString(), matriculaAluno.toString())

            }
        }
    }
}

@Composable
fun StudentDescriptionScreen(nomeAluno: String, matricula: String) {

    var studentResponse by remember {
        mutableStateOf(listOf<Student?>())
    }

    var student by remember {
        mutableStateOf(Student("", "", "", "", null))
    }

    val call = RetrofitFactory().getStudentService().getStudentByMatricula(matricula)


    call.enqueue(object : Callback<Student> {
        override fun onResponse(call: Call<Student>, response: Response<Student>) {
            if (response.isSuccessful) {
                val studentResponse = response.body()
                if (studentResponse != null) {
                    student = studentResponse
                }
            } else {
                Log.e("teste", "Erro na resposta da API: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<Student>, t: Throwable) {
            Log.i("teste", "onFailure: ${t.message} ")
        }
    })

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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

            AsyncImage(
                model = student.foto,
                contentDescription = null,
                modifier = Modifier.height(200.dp)
            )
            Text(
                text = "${student.nome}".uppercase(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(51, 71, 176)
            )

            Text(
                text = "${stringResource(id = R.string.registration)}: ${student.matricula}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )

            Box(
                modifier = Modifier
                    .padding(30.dp)
                    .width(150.dp)
                    .height(2.dp)
                    .background(Color(255, 194, 62))
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview4() {
    LionSchoolTheme {
        StudentDescriptionScreen("nome", "matricula")
    }
}