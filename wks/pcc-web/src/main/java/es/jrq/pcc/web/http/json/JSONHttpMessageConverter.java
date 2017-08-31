package es.jrq.pcc.web.http.json;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * As defined in requirements, JSON is the model interface to work with Royalty Manager
 * REST API, so make sure JSON converter is defined and instantiated in the Spring Context.
 *
 * {@link MappingJackson2HttpMessageConverter} will be used as JSON to HTTP converter.
 * It will be configured to fail if any of the properties given are unknown to make
 * sure the interface is robust and
 * @author JRQ
 *
 */
@Component
public class JSONHttpMessageConverter {

    /**
     *
     * @return the initialized bean for JSON-HTTP conversions
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }

    /**
     * Serialize model object to JSON
     * @param object Model object to be converted
     * @return byte[] Byte Array representing the object
     * @throws IOException If any errors occurred while writing object values as bytes
     */
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return mapper.writeValueAsBytes(object);
    }

}
