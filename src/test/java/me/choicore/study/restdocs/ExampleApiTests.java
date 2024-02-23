package me.choicore.study.restdocs;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExampleApi.class)
@RestDocsTest
class ExampleApiTests {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void examples() throws Exception {
        this.mockMvc
                .perform(post("/v1/examples")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ExampleRequest("parameter1", "parameter2"))))
                .andDo(
                        MockMvcRestDocumentationBuilder.withDefault()
                                .resource(
                                        ResourceSnippetParameters.builder()
                                                .description("The description of the resource")
                                                .requestSchema(Schema.schema("The request schema"))
                                                .responseSchema(Schema.schema("The response schema"))
                                                .requestFields(
                                                        fieldWithPath("parameter1").type(JsonFieldType.STRING).description("The first parameter"),
                                                        fieldWithPath("parameter2").type(JsonFieldType.STRING).description("The second parameter")
                                                )
                                                .responseFields(
                                                        fieldWithPath("message").type(JsonFieldType.STRING).description("The message of the response")
                                                )
                                                .build()
                                )
                                .build()
                )
                .andExpect(status().isOk());
    }
}

