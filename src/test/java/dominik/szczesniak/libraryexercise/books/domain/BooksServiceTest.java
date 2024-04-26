package dominik.szczesniak.libraryexercise.books.domain;

import dominik.szczesniak.libraryexercise.books.domain.model.commands.AddBookSample;
import dominik.szczesniak.libraryexercise.books.domain.model.commands.UpdateBookSample;
import dominik.szczesniak.libraryexercise.books.domain.model.exceptions.BookAlreadyExistsException;
import dominik.szczesniak.libraryexercise.books.domain.model.exceptions.BookDoesNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static dominik.szczesniak.libraryexercise.books.domain.TestBooksServiceConfiguration.booksService;
import static dominik.szczesniak.libraryexercise.books.domain.model.AuthorSample.createAnyAuthor;
import static dominik.szczesniak.libraryexercise.books.domain.model.BookIdSample.createAnyBookId;
import static dominik.szczesniak.libraryexercise.books.domain.model.TitleSample.createAnyTitle;
import static dominik.szczesniak.libraryexercise.books.domain.model.YearSample.createAnyValidYearForBook;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class BooksServiceTest {

	private BooksService tut;

	@BeforeEach
	void setUp() {
		tut = booksService();
	}

	@Test
	void should_create_book_and_fetch_it() {
		// given
		final String title = createAnyTitle();
		final String author = createAnyAuthor();
		final Integer year = createAnyValidYearForBook();

		// when
		final Long id = tut.add(AddBookSample.builder().author(author).title(title).year(year).build());

		// then
		final Book book = tut.getBook(id);
		assertThat(book.getId()).isEqualTo(id);
		assertThat(book.getYear()).isEqualTo(year);
		assertThat(book.getAuthor()).isEqualTo(author);
		assertThat(book.getTitle()).isEqualTo(title);
	}

	@Test
	void should_not_add_books_with_invalid_data() {
		// when
		final Throwable thrown_1 = catchThrowable(() -> tut.add(AddBookSample.builder().author("Domi$#@nik Patryk").build()));
		final Throwable thrown_2 = catchThrowable(() -> tut.add(AddBookSample.builder().title(" ").build()));
		final Throwable thrown_3 = catchThrowable(() -> tut.add(AddBookSample.builder().year(2400).build()));

		// then
		assertThat(thrown_1).isInstanceOf(IllegalArgumentException.class);
		assertThat(thrown_2).isInstanceOf(IllegalArgumentException.class);
		assertThat(thrown_3).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void should_throw_exception_when_book_doesnt_exist() {
		// when
		final Throwable thrown = catchThrowable(() -> tut.getBook(createAnyBookId()));

		// then
		assertThat(thrown).isInstanceOf(BookDoesNotExistException.class);
	}

	@Test
	void should_have_no_books_when_none_added() {
		// when
		final List<Book> books = tut.findAll();

		// then
		assertThat(books).hasSize(0);
	}

	@Test
	void should_find_all_books() {
		// given
		tut.add(AddBookSample.builder().build());
		tut.add(AddBookSample.builder().build());
		tut.add(AddBookSample.builder().build());
		tut.add(AddBookSample.builder().build());

		// when
		final List<Book> books = tut.findAll();

		// then
		assertThat(books).hasSize(4);
	}

	@Test
	void should_delete_book() {
		// given
		final Long addedBook = tut.add(AddBookSample.builder().build());
		final Long bookToRemove = tut.add(AddBookSample.builder().build());

		// when
		tut.remove(bookToRemove);

		// then
		assertThat(tut.findAll()).hasSize(1)
				.extracting(Book::getId)
				.containsExactlyInAnyOrder(addedBook);
	}

	@Test
	void should_not_be_able_to_add_exactly_same_book() {
		// given
		final String title = createAnyTitle();
		final String author = createAnyAuthor();
		final Integer year = createAnyValidYearForBook();

		tut.add(AddBookSample.builder().author(author).title(title).year(year).build());

		// when
		final Throwable thrown = catchThrowable(() -> tut.add(AddBookSample.builder().author(author).title(title).year(year).build()));

		// then
		assertThat(thrown).isInstanceOf(BookAlreadyExistsException.class);
	}

	@Test
	void should_update_book() {
		// given
		final String title = createAnyTitle();
		final String author = createAnyAuthor();
		final Integer year = createAnyValidYearForBook();
		final Long id = tut.add(AddBookSample.builder().author(author).title(title).year(year).build());

		final String changedTitle = createAnyTitle();

		// when
		tut.update(UpdateBookSample.builder().bookId(id).author(author).title(changedTitle).year(year).build());

		// then
		final Book book = tut.getBook(id);
		assertThat(book.getId()).isEqualTo(id);
		assertThat(book.getYear()).isEqualTo(year);
		assertThat(book.getAuthor()).isEqualTo(author);
		assertThat(book.getTitle()).isNotEqualTo(title);
		assertThat(book.getTitle()).isEqualTo(changedTitle);
	}

	@Test
	void should_not_be_able_to_update_to_exactly_same_book() {
		// given
		final String title = createAnyTitle();
		final String author = createAnyAuthor();
		final Integer year = createAnyValidYearForBook();

		final Long book = tut.add(AddBookSample.builder().author(author).title(title).year(year).build());

		// when
		final Throwable thrown = catchThrowable(
				() -> tut.update(UpdateBookSample.builder().bookId(book).author(author).year(year).title(title).build()));

		// then
		assertThat(thrown).isInstanceOf(BookAlreadyExistsException.class);
	}

	@Test
	void should_throw_exception_when_trying_to_update_non_existing_book() {
		// when
		final Throwable thrown = catchThrowable(() -> tut.update(UpdateBookSample.builder().build()));

		// then
		assertThat(thrown).isInstanceOf(BookDoesNotExistException.class);
	}

	@Test
	void should_throw_exception_when_trying_to_update_to_invalid_fields() {
		// given
		final Long book = tut.add(AddBookSample.builder().build());

		// when
		final Throwable thrown_1 = catchThrowable(() -> tut.update(UpdateBookSample.builder().bookId(book).title("a").build()));
		final Throwable thrown_2 = catchThrowable(() -> tut.update(UpdateBookSample.builder().bookId(book).author(" ").build()));
		final Throwable thrown_3 = catchThrowable(() -> tut.update(UpdateBookSample.builder().bookId(book).year(-21).build()));

		// then
		assertThat(thrown_1).isInstanceOf(IllegalArgumentException.class);
		assertThat(thrown_2).isInstanceOf(IllegalArgumentException.class);
		assertThat(thrown_3).isInstanceOf(IllegalArgumentException.class);
	}

}