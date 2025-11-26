package edu.pe.tecsup.ui.home

import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import edu.pe.tecsup.data.model.Curso
import edu.pe.tecsup.data.repository.CursoRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CursoUiState(
    val cursos: List<Curso> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class CursoViewModel : ViewModel() {
    private val repository = CursoRepository()
    private val auth = FirebaseAuth.getInstance()
    
    private val _uiState = MutableStateFlow(CursoUiState())
    val uiState: StateFlow<CursoUiState> = _uiState
    
    init {
        loadCursos()
    }
    
    private fun loadCursos() {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            repository.getCursosByUser(userId).collect { result ->
                result.fold(
                    onSuccess = { cursos ->
                        _uiState.value = CursoUiState(cursos = cursos)
                    },
                    onFailure = { error ->
                        _uiState.value = CursoUiState(error = error.message)
                    }
                )
            }
        }
    }
    
    fun addCurso(titulo: String, descripcion: String) {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            val curso = Curso(titulo = titulo, descripcion = descripcion, userId = userId)
            repository.addCurso(curso)
            loadCursos()
        }
    }
    
    fun updateCurso(id: String, titulo: String, descripcion: String) {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            val curso = Curso(id = id, titulo = titulo, descripcion = descripcion, userId = userId)
            repository.updateCurso(id, curso)
            loadCursos()
        }
    }
    
    fun deleteCurso(id: String) {
        viewModelScope.launch {
            repository.deleteCurso(id)
            loadCursos()
        }
    }
}