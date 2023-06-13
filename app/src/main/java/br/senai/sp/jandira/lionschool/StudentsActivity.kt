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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.R.*
import br.senai.sp.jandira.lionschool.model.Student
import br.senai.sp.jandira.lionschool.model.StudentList
import br.senai.sp.jandira.lionschool.service.RetrofitFactory
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme
import br.senai.sp.jandira.lionschool.ui.theme.Shapes
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                val siglaCurso = intent.getStringExtra("Curso")
                val nomeCurso = intent.getStringExtra("Nome_Curso")
                StudentsScreen(siglaCurso.toString(), nomeCurso.toString())
            }
        }
    }
}

@Composable
fun StudentsScreen(sigla: String, nomeCurso: String) {

    var context = LocalContext.current

    var listStudents by remember {
        mutableStateOf(listOf<Student>())
    }

    val call = RetrofitFactory().getStudentService().getStudents(sigla)


    call.enqueue(object : Callback<StudentList> {
        override fun onResponse(call: Call<StudentList>, response: Response<StudentList>) {
            listStudents = response.body()!!.alunos
        }

        override fun onFailure(call: Call<StudentList>, t: Throwable) {
            Log.i("Requisição falhou ao buscar alunos", "${t.message}")
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
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Image(
                    painter = painterResource(id = drawable.logo_image),
                    contentDescription = null,
                    modifier = Modifier.width(60.dp)
                )
                Column() {
                    Text(
                        text = stringResource(id = string.lion_school),
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
//
            Text(
                text = "${nomeCurso}".substring(6),
                color = Color(52, 71, 176),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly

            ) {
                Text(text = stringResource(id = R.string.all))

                Card(
                    modifier = Modifier
                        .height(10.dp)
                        .width(2.dp)
                        .fillMaxHeight()
                        .align(Alignment.CenterVertically),
                    backgroundColor = Color(255, 194, 62)
                ) {}

                Text(text = stringResource(id = R.string.finished))

                Card(
                    modifier = Modifier
                        .height(10.dp)
                        .width(2.dp)
                        .fillMaxHeight()
                        .align(Alignment.CenterVertically),
                    backgroundColor = Color(255, 194, 62)
                ) {}

                Text(text = stringResource(id = R.string.studying))
            }

            LazyColumn() {
                items(listStudents) {

                    Button(
                        onClick = {

                            val openStudentsDescriptionActivity = Intent(context, StudentDescription::class.java)
                            openStudentsDescriptionActivity.putExtra("Aluno", "${it.nome}")
                            openStudentsDescriptionActivity.putExtra("Aluno_matricula", "${it.matricula}")
                            context.startActivity(openStudentsDescriptionActivity)

                        },
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                            .padding(10.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(Color(51, 71, 176))

                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            AsyncImage(
                                model = it.foto,
                                contentDescription = null,
                                modifier = Modifier.height(100.dp)
                            )

                            Text(
                                text = "${it.nome}".uppercase(),
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = "${stringResource(id = R.string.registration)}: ${it.matricula}",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }


        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview3() {
    LionSchoolTheme {
        StudentsScreen("ds", "Desenvolvimento de Sistemas")
    }
}