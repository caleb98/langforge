package net.calebscode.langforge.app.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class VersionNumberTest {

	@Test
	void majorVersionStringToVersionNumber() {
		var result = VersionNumber.parse("123");

		assertEquals(new VersionNumber(123, 0, 0), result);
	}

	@Test
	void majorVersionStringWithWhitespaceToVersionNumber() {
		var result = VersionNumber.parse("\n\t123   ");

		assertEquals(new VersionNumber(123, 0, 0), result);
	}

	@Test
	void majorMinorStringToVersionNumber() {
		var result = VersionNumber.parse("9.1");

		assertEquals(new VersionNumber(9, 1, 0), result);
	}

	@Test
	void majorMinorPatchStringToVersionNumber() {
		var result = VersionNumber.parse("5.2.7");

		assertEquals(new VersionNumber(5, 2, 7), result);
	}

	@Test
	void emptyStringFails() {
		assertThrows(IllegalArgumentException.class, () -> VersionNumber.parse(""));
	}

	@Test
	void invalidCharactersFail() {
		assertThrows(IllegalArgumentException.class, () -> VersionNumber.parse("9.1.2-beta"));
		assertThrows(IllegalArgumentException.class, () -> VersionNumber.parse("v9.1.2"));
		assertThrows(IllegalArgumentException.class, () -> VersionNumber.parse("release"));
	}

	@Test
	void tooManyNumbersFail() {
		assertThrows(IllegalArgumentException.class, () -> VersionNumber.parse("1.2.3.4"));
	}

}
