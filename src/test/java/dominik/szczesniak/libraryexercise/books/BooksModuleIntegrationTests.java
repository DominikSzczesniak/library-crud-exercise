package dominik.szczesniak.libraryexercise.books;

import dominik.szczesniak.libraryexercise.books.infrastructure.adapters.incoming.rest.books.AddBookRestInvoker;
import dominik.szczesniak.libraryexercise.books.infrastructure.adapters.incoming.rest.books.DeleteBookRestInvoker;
import dominik.szczesniak.libraryexercise.books.infrastructure.adapters.incoming.rest.books.FindAllBooksRestInvoker;
import dominik.szczesniak.libraryexercise.books.infrastructure.adapters.incoming.rest.books.FindAllBooksRestInvoker.BookInListDto;
import dominik.szczesniak.libraryexercise.books.infrastructure.adapters.incoming.rest.books.GetBookRestInvoker;
import dominik.szczesniak.libraryexercise.books.infrastructure.adapters.incoming.rest.books.GetBookRestInvoker.BookDto;
import dominik.szczesniak.libraryexercise.books.infrastructure.adapters.incoming.rest.books.UpdateBookRestInvoker;
import dominik.szczesniak.libraryexercise.books.infrastructure.adapters.incoming.rest.books.UpdateBookRestInvoker.UpdateBookDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static dominik.szczesniak.libraryexercise.books.domain.model.AuthorSample.createAnyAuthor;
import static dominik.szczesniak.libraryexercise.books.domain.model.TitleSample.createAnyTitle;
import static dominik.szczesniak.libraryexercise.books.domain.model.YearSample.createAnyValidYearForBook;
import static dominik.szczesniak.libraryexercise.books.infrastructure.adapters.incoming.rest.books.AddBookRestInvoker.AddBookDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class BooksModuleIntegrationTests {

	@Autowired
	private AddBookRestInvoker addBookRest;

	@Autowired
	private UpdateBookRestInvoker updateBookRest;

	@Autowired
	private FindAllBooksRestInvoker getBooksRest;

	@Autowired
	private GetBookRestInvoker getBookRest;

	@Autowired
	private DeleteBookRestInvoker deleteBookRest;

	@Test
	void should_add_books_and_retrieve_them() {
		// when
		final ResponseEntity<Long> addBookResponse_1 = addBookRest.addBook(AddBookDto.builder().build());
		final ResponseEntity<Long> addBookResponse_2 = addBookRest.addBook(AddBookDto.builder().build());

		// then
		assertThat(addBookResponse_1.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(addBookResponse_2.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		// when
		final ResponseEntity<List<BookInListDto>> getBooksResponse = getBooksRest.findAll();

		// then
		assertThat(getBooksResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(getBooksResponse.getBody()).hasSize(2);
	}

	@Test
	void should_delete_book() {
		// given
		final String title = createAnyTitle();
		final String author = createAnyAuthor();
		final int year = createAnyValidYearForBook();

		final ResponseEntity<Long> addBookResponse_1 = addBookRest.addBook(AddBookDto.builder().build());
		final ResponseEntity<Long> addBookResponse_2 = addBookRest.addBook(AddBookDto.builder().title(title).author(author).year(year).build());

		// when
		final ResponseEntity<Void> deleteBookResponse = deleteBookRest.deleteBook(addBookResponse_1.getBody());

		// then
		assertThat(deleteBookResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

		// when
		final ResponseEntity<List<BookInListDto>> getBooksResponse = getBooksRest.findAll();

		// then
		assertThat(getBooksResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(getBooksResponse.getBody()).hasSize(1);
		assertThat(getBooksResponse.getBody())
				.extracting(BookInListDto::getId, BookInListDto::getAuthor, BookInListDto::getTitle, BookInListDto::getYear)
				.containsExactly(tuple(addBookResponse_2.getBody(), author, title, year));
	}

	@Test
	void should_find_book_after_updating_it() {
		//given
		final String title = createAnyTitle();
		final String author = createAnyAuthor();
		final int year = createAnyValidYearForBook();

		// when
		final ResponseEntity<Long> addBookResponse = addBookRest.addBook(AddBookDto.builder().year(year).title(title).author(author).build());

		// then
		assertThat(addBookResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		// when
		final ResponseEntity<BookDto> getBookResponse = getBookRest.getBook(addBookResponse.getBody());

		// then
		assertThat(getBookResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(getBookResponse.getBody().getId()).isEqualTo(addBookResponse.getBody());
		assertThat(getBookResponse.getBody().getAuthor()).isEqualTo(author);
		assertThat(getBookResponse.getBody().getTitle()).isEqualTo(title);
		assertThat(getBookResponse.getBody().getYear()).isEqualTo(year);

		// when
		final String updatedTitle = "differentTitle";
		final String updatedAuthor = "differentAuthor";
		final ResponseEntity<Void> updateBookResponse = updateBookRest.updateBook(
				UpdateBookDto.builder().title(updatedTitle).author(updatedAuthor).year(year).build(), addBookResponse.getBody());

		// then
		assertThat(updateBookResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		// when
		final ResponseEntity<BookDto> getBookResponseAfterUpdate = getBookRest.getBook(addBookResponse.getBody());

		// then
		assertThat(getBookResponseAfterUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(getBookResponseAfterUpdate.getBody().getId()).isEqualTo(addBookResponse.getBody());
		assertThat(getBookResponseAfterUpdate.getBody().getYear()).isEqualTo(year);
		assertThat(getBookResponseAfterUpdate.getBody().getAuthor()).isEqualTo(updatedAuthor);
		assertThat(getBookResponseAfterUpdate.getBody().getTitle()).isEqualTo(updatedTitle);
	}

}
