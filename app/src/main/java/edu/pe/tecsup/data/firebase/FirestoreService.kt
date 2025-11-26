package edu.pe.tecsup.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import edu.pe.tecsup.data.model.Curso
import kotlinx.coroutines.tasks.await
class FirestoreService {

    private val db = FirebaseFirestore.getInstance()
    private val cursosRef = db.collection("cursos")

    suspend fun addCurso(curso: Curso): String {
        val docRef = cursosRef.add(curso).await()
        return docRef.id
    }

    suspend fun getCursos(): List<Curso> {
        val snapshot = cursosRef.get().await()
        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(Curso::class.java)?.copy(id = doc.id)
        }
    }

    suspend fun getCursosByUser(userId: String): List<Curso> {
        val snapshot = cursosRef
            .whereEqualTo("userId", userId)
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(Curso::class.java)?.copy(id = doc.id)
        }
    }

    suspend fun getCursoById(id: String): Curso? {
        val snapshot = cursosRef.document(id).get().await()
        return snapshot.toObject(Curso::class.java)?.copy(id = snapshot.id)
    }

    suspend fun updateCurso(id: String, curso: Curso) {
        cursosRef.document(id).set(curso).await()
    }

    suspend fun deleteCurso(id: String) {
        cursosRef.document(id).delete().await()
    }
}
