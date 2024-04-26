package dominik.szczesniak.libraryexercise.books.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class BookTest {

	@Test
	void should_create_book() {
		new Book("foklwij fadkl", "Dominik Szczesniak", 2005);
		new Book("foklwij fadkl", "Dominik Szczesniak jr.", 2005);
		new Book("foklwij fadkl", "Dominik Szcze-sniak", 2005);
		new Book("foklwij fadkl", "Dominik", 2024);
		new Book("Spider-man 3", "Random-author jr.", 1442);
	}

	@Test
	void should_throw_exception_when_invalid_year() {
		// when
		final Throwable book_1 = catchThrowable(() -> new Book("asd", "qwe", 2100));
		final Throwable book_2 = catchThrowable(() -> new Book("asd", "qwe", -2100));


		// then
		assertThat(book_1).isInstanceOf(IllegalArgumentException.class);
		assertThat(book_2).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void should_throw_exception_when_invalid_author_name() {
		// when
		final Throwable book_1 = catchThrowable(() -> new Book("asd", "Dominik&% $&$?", 2005));
		final Throwable book_2 = catchThrowable(() -> new Book("asd", " ", 2005));
		final Throwable book_3 = catchThrowable(() -> new Book("asd", "", 2005));
		final Throwable book_4 = catchThrowable(() -> new Book("asd", null, 2005));

		// then
		assertThat(book_1).isInstanceOf(IllegalArgumentException.class);
		assertThat(book_2).isInstanceOf(IllegalArgumentException.class);
		assertThat(book_3).isInstanceOf(IllegalArgumentException.class);
		assertThat(book_4).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void should_throw_exception_when_invalid_title() {
		// when
		final Throwable book_1 = catchThrowable(() -> new Book(null, "qwe", 2000));
		final Throwable book_2 = catchThrowable(() -> new Book("", "qwe", 2000));
		final Throwable book_3 = catchThrowable(() -> new Book(" ", "qwe", 2000));
		final Throwable book_4 = catchThrowable(() -> new Book("b", "qwe", 2000));

		// then
		assertThat(book_1).isInstanceOf(IllegalArgumentException.class);
		assertThat(book_2).isInstanceOf(IllegalArgumentException.class);
		assertThat(book_3).isInstanceOf(IllegalArgumentException.class);
		assertThat(book_4).isInstanceOf(IllegalArgumentException.class);
	}

}