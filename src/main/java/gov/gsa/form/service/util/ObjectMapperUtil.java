package gov.gsa.form.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

/**
 * @author Wube Kifle
 *
 * <p>
 * Description : Object mapper class.
 * to IDR API
 * </p>
 */
public final class ObjectMapperUtil {
	//You know.This is Util class and has static members, is not meant to be instantiated.
	private ObjectMapperUtil() {
	}

	public static <T> T readFromJson(final String jsonSource, Class<T> typeToRead) throws IOException {
		return new ObjectMapper().readerFor(typeToRead).readValue(jsonSource);
	}

	public static String writeToJsonString(Object payload) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		return  mapper.writeValueAsString(payload);
	}

}
