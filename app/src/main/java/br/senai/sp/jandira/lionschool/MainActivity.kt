package br.senai.sp.jandira.lionschool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 130.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_image),
                    contentDescription = "",
                    modifier = Modifier
                        .size(250.dp)
                )

                Box(
                    modifier = Modifier
                        .padding(30.dp)
                        .width(100.dp)
                        .height(3.dp)
                        .background(Color(255, 194, 62))
                )

                Text(
                    text = stringResource(id = R.string.lion_school),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(51, 71, 176)
                )
            }


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(215.dp),
                backgroundColor = Color(51, 71, 176),
                shape = RoundedCornerShape(
                    topEnd = 20.dp, topStart = 20.dp
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = stringResource(id = R.string.manage_courses_students),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(300.dp)
                    )

                    Button(onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(Color(255, 194, 62)),
                        modifier = Modifier
                            .width(260.dp)
                            .height(50.dp),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.get_started).uppercase(),
                            fontSize = 22.sp,
                            color = Color.White
                        )
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    LionSchoolTheme {
        HomeScreen()
    }
}