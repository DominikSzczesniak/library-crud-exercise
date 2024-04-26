package dominik.szczesniak.libraryexercise.books.domain.model;

import org.apache.commons.lang3.RandomStringUtils;

public class TitleSample {

	public static String createAnyTitle() {
		return RandomStringUtils.randomAlphabetic(10);
	}

}
