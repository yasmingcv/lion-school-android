package br.senai.sp.jandira.lionschool.service

import br.senai.sp.jandira.lionschool.model.Student
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StudentService {
    @GET("alunos?curso={curso}")
    fun getStudents(@Path("curso") curso: String): Call<Student>
}