package com.mleekko.json;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Mleekko
 */
@Component
public class JsonConverter {


    ObjectMapper mapper = new ObjectMapper();
    {
        mapper.configure(SerializationConfig.Feature.AUTO_DETECT_IS_GETTERS, false);
        mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
    }


    public String toJsonString(Object o) throws IOException {
        return mapper.writeValueAsString(o);
    }


    public <T> T parse(String s, Class<T> valueType) {
        try {
            return mapper.readValue(s, valueType);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     *
     * @param response
     * @param o
     * @throws IOException
     */
    public void writeToResponse(HttpServletResponse response, Object o) throws IOException {
        mapper.writeValue(response.getWriter(), o);

        finishUp(response);
    }

    public void writeToResponse(HttpServletResponse response, String s) throws IOException {
        mapper.writeValue(response.getWriter(), s);
        finishUp(response);
    }

    private void finishUp(HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Connection", "close");
    }

}
