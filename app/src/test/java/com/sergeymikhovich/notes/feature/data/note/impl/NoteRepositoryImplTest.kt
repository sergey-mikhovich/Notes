package com.sergeymikhovich.notes.feature.data.note.impl

//@OptIn(ExperimentalCoroutinesApi::class)
//class NoteRepositoryImplTest {
//
//    private val localNotes = listOf(
//        Note("Title1", "Description1"),
//        Note("Title2", "Description2"),
//        Note("Title3", "Description3")
//    )
//
//    private lateinit var localNoteDataSource: FakeLocalNoteDataSource
//    private lateinit var noteRepository: NoteRepositoryImpl
//
//    private val testDispatcher = UnconfinedTestDispatcher()
//
//    @Before
//    fun createRepository() {
//        localNoteDataSource = FakeLocalNoteDataSource(localNotes)
//        noteRepository = NoteRepositoryImpl(localNoteDataSource, testDispatcher)
//    }
//
//    @Test
//    fun getAll_allNotesFromLocalDataSource() = runTest(testDispatcher) {
//        val notes = noteRepository.getAll()
//        assertThat(notes, IsEqual(localNotes))
//    }
//
//    @Test
//    fun observeAll_allObservedNotesFromLocalDataSource() = runTest(testDispatcher) {
//        val observedNotes = noteRepository.observeAll().first()
//        assertThat(localNotes, IsEqual(observedNotes))
//    }
//
//    @Test
//    fun getById_noteWithMatchingIdFromLocalDataSource() = runTest(testDispatcher) {
//        val noteToFind = localNotes.firstOrNull() ?: throw Exception("Local notes cannot be empty")
//        val foundNote = noteRepository.getById(noteToFind.id)
//        assertThat(foundNote, IsEqual(noteToFind))
//    }
//
//    @Test
//    fun delete_noteWithMatchingIdDeletedFromLocalDataSource() = runTest(testDispatcher) {
//        val initialNotesSize = noteRepository.getAll().size
//        val noteToDelete = localNotes.firstOrNull() ?: throw Exception("Local notes cannot be empty")
//
//        noteRepository.delete(noteToDelete.id)
//
//        val afterDeleteNotes = noteRepository.getAll()
//
//        assertThat(afterDeleteNotes.size, IsEqual(initialNotesSize - 1))
//        assertThat(afterDeleteNotes, not(hasItem(noteToDelete)))
//    }
//
//    @Test
//    fun add_noteWasAddedToLocalDataSource() = runTest(testDispatcher) {
//        val noteToAdd = Note("AddedTitle", "AddedDescription")
//        noteRepository.add(noteToAdd)
//
//        assertThat(localNoteDataSource.getAll(), hasItem(noteToAdd))
//    }
//
//    @Test
//    fun update_noteWasUpdatedInLocalDataSource() = runTest(testDispatcher) {
//        val noteToUpdate = localNotes.firstOrNull()?.copy(
//            title = "UpdatedTitle",
//            description = "UpdatedDescription"
//        ) ?: throw Exception("Local notes cannot be empty")
//
//        noteRepository.update(noteToUpdate)
//        assertThat(localNoteDataSource.getAll(), hasItem(noteToUpdate))
//    }
//}