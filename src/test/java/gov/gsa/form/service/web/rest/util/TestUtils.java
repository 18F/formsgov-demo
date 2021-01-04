package gov.gsa.form.service.web.rest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Wube.Kifle
 *         <p>
 *         Description : The {@link TestUtils} class provides common utilities
 *         to assist developers in writing tests.
 *         </p>
 */
public class TestUtils {

	private TestUtils() {
		throw new UnsupportedOperationException("This class is static only and should not be instantiated");
	}

	/**
	 * Writes the given {@link Object} as a JSON {@link String} and returns the
	 * result.
	 *
	 * @param toWrite The {@link Object} to write as a {@link String}.
	 * @return The JSON {@link String} representation of the given {@link Object}
	 *         instance.
	 * @throws JsonProcessingException Throws a {@link JsonProcessingException} if
	 *                                 writing failed due to an unexpected error.
	 */
	public static String writeObjectToJsonBytes(final Object toWrite) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(toWrite);
	}

	/**
	 *
	 * @param fileName Input string as a {@link String}.
	 * @return XML string as a {@link String}
	 * @throws  {@link URISyntaxException , IOException }
	 */
	public static String readFile(final String fileName) throws URISyntaxException, IOException {
		Path path = Paths.get(TestUtils.class.getClassLoader().getResource(fileName).toURI());
		Stream<String> lines = Files.lines(path);
		String data = lines.collect(Collectors.joining("\n"));
		lines.close();
		return data;
	}
}
