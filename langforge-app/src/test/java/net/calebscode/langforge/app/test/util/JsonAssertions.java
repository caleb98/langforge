package net.calebscode.langforge.app.test.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import tools.jackson.databind.ObjectMapper;

public final class JsonAssertions {

	private static ObjectMapper mapper = new ObjectMapper();

	public static void assertJsonEquals(String expected, String actual) {
		var expectedNode = mapper.readTree(expected);
		var actualNode = mapper.readTree(actual);
		assertEquals(expectedNode, actualNode);
	}

}
