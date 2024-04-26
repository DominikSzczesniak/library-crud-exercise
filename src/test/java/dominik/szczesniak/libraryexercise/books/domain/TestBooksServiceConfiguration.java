package dominik.szczesniak.libraryexercise.books.domain;

class TestBooksServiceConfiguration {

	static BooksService booksService() {
		return new BooksService(new InMemoryBooksRepository());
	}

}
