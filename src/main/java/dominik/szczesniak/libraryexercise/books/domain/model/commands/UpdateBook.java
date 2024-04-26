package dominik.szczesniak.libraryexercise.books.domain.model.commands;

import lombok.NonNull;
import lombok.Value;

@Value
public class UpdateBook {

	@NonNull Long bookId;
	@NonNull String title;
	@NonNull String author;
	@NonNull Integer year;

}
