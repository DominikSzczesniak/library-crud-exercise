package dominik.szczesniak.libraryexercise.books.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Year;

import static com.google.common.base.Preconditions.checkArgument;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"title", "author", "release_year"}))
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String author;
	@Column(name = "release_year")
	private int year;

	Book(final String title, final String author, final int year) {
		checkIsValidData(title, author, year);
		this.title = title;
		this.author = author;
		this.year = year;
	}

	void update(final String title, final String author, final Integer year) {
		checkIsValidData(title, author, year);
		this.title = title;
		this.author = author;
		this.year = year;
	}

	private void checkIsValidData(final String title, final String author, final int year) {
		checkArgument(isValidYear(year), "Invalid year - make sure it is bigger than 0 and doesn't exceed current year.");
		checkArgument(isValidAuthor(author), "Invalid author name, make sure you don't use any special characters other than . and -.");
		checkArgument(isValidTitle(title), "Invalid title - cannot be blank and must have 2 or more characters.");
	}

	private static boolean isValidTitle(final String title) {
		return title != null && !title.isBlank() && title.length() > 1;
	}

	private static boolean isValidYear(final int year) {
		return year <= Year.now().getValue() && year >= 0;
	}

	private boolean isValidAuthor(final String author) {
		if (author == null) {
			return false;
		}
		final String regex = "^[A-Za-z][A-Za-z\\s.-]*$";
		return author.matches(regex);
	}

}
