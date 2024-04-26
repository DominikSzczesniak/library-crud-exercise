package dominik.szczesniak.libraryexercise.books.domain.model;

import org.apache.commons.lang3.RandomStringUtils;

public class AuthorSample {

	public static String createAnyAuthor() {
		return RandomStringUtils.randomAlphabetic(10);
	}

}
