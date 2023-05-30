package br.senai.sp.jandira.lionschool.service

import br.senai.sp.jandira.lionschool.model.CourseList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CourseService {
    @GET("cursos")
    fun getCourse(): Call<CourseList>

    //@GET("alunos?curso={sigla}")
    //fun getStudentsCourse(@Query("sigla") sigla: String)

    //alunos?curso=rds

}