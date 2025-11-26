package edu.pe.tecsup.ui.home

import androidx.lifecycle.*
import edu.pe.tecsup.data.model.Curso
import edu.pe.tecsup.data.repository.CursoRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CursoViewModel : ViewModel() {
    private val repository = CursoRepository()

    val cursos = repository.getCursos()
        .stateIn(viewModelScope, SharingStarted.Lazily, Result.success(emptyList()))

    fun addCurso(curso: Curso) {
        viewModelScope.launch {
            repository.addCurso(curso)
        }
    }
}