package dominik.szczesniak.libraryexercise.books.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BooksServiceConfiguration {

	@Bean
	BooksService booksService(final BooksRepository booksRepository) {
		return new BooksService(booksRepository);
	}

}
