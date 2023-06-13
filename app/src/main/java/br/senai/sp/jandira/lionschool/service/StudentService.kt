package br.senai.sp.jandira.lionschool.service

import br.senai.sp.jandira.lionschool.model.Student
import br.senai.sp.jandira.lionschool.model.StudentList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StudentService {
    @GET("alunos")
    fun getStudents(@Query("curso") curso: String): Call<StudentList>

    @GET("alunos/{matricula}")
    fun getStudentByMatricula(@Path("matricula") matricula : String): Call<Student>
}