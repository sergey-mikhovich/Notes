package com.sergeymikhovich.notes.feature.data.note.impl

//class FakeLocalNoteDataSource(initialNotes: List<Note>? = emptyList()): LocalNoteDataSource {
//
//    private val _notes = MutableStateFlow(LinkedHashMap<String, Note>())
//
//    init {
//        _notes.update { initialNotes?.associateBy { it.id }?.toMutableMap() as LinkedHashMap<String, Note> }
//    }
//
//    override suspend fun getAll(): List<Note> {
//        return _notes.value.values.toList()
//    }
//
//    override fun observeAll(): Flow<List<Note>> {
//        return flow { emit(_notes.value.values.toList()) }
//    }
//
//    override suspend fun getById(id: String): Note? {
//        return _notes.value[id]
//    }
//
//    override suspend fun delete(id: String) {
//        _notes.update { notes ->
//            notes.remove(id)
//            notes
//        }
//    }
//
//    override suspend fun add(note: Note): Note? {
//        _notes.update { notes ->
//            notes[note.id] = note
//            notes
//        }
//        return getById(note.id)
//    }
//
//    override suspend fun update(note: Note) {
//        val updatedNote = _notes.value[note.id]?.copy(
//            title = note.title,
//            description = note.description
//        ) ?: throw Exception("Note with id = ${note.id} not found")
//
//        _notes.update { notes ->
//            notes[note.id] = updatedNote
//            notes
//        }
//    }
//}