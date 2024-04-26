package dominik.szczesniak.libraryexercise.books.domain.model;

import java.util.Random;

public class BookIdSample {

	public static Long createAnyBookId() {
		return new Random().nextLong(1, 999999);
	}

}
