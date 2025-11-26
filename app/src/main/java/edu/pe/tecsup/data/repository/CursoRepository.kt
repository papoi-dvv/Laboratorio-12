package edu.pe.tecsup.data.repository

import edu.pe.tecsup.data.firebase.FirestoreService
import edu.pe.tecsup.data.model.Curso
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CursoRepository {

    private val firestoreService = FirestoreService()

    fun getCursos(): Flow<Result<List<Curso>>> = flow {
        try {
            val cursos = firestoreService.getCursos()
            emit(Result.success(cursos))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun getCursosByUser(userId: String): Flow<Result<List<Curso>>> = flow {
        try {
            val cursos = firestoreService.getCursosByUser(userId)
            emit(Result.success(cursos))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun addCurso(curso: Curso): Result<String> {
        return try {
            val id = firestoreService.addCurso(curso)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateCurso(id: String, curso: Curso): Result<Unit> {
        return try {
            firestoreService.updateCurso(id, curso)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteCurso(id: String): Result<Unit> {
        return try {
            firestoreService.deleteCurso(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCursoById(id: String): Result<Curso?> {
        return try {
            val curso = firestoreService.getCursoById(id)
            Result.success(curso)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}