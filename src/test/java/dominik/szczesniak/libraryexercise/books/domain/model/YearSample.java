package dominik.szczesniak.libraryexercise.books.domain.model;

import java.util.Random;

public class YearSample {

	public static int createAnyValidYearForBook() {
		int currentYear = java.time.Year.now().getValue();
		final Random random = new Random();
		return random.nextInt(currentYear);
	}

}
