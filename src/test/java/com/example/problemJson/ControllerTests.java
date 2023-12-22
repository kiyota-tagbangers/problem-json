package com.example.problemJson;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author kiyota
 */
@WebMvcTest({ItemController.class, ErrorController.class})
@AutoConfigureMockMvc
@Import(RestDocsConfig.class)
@AutoConfigureRestDocs
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ControllerTests {

    final MockMvc mvc;

    final ObjectMapper objectMapper;

    @Test
    void problemDetail() throws Exception {
        this.mvc.perform(get("/problem-detail"))
                .andExpect(status().isInternalServerError())
                .andDo(print())
                .andDo(document("problem-detail"));
    }

    @Test
    void runtimeException() throws Exception {
        this.mvc.perform(get("/runtime-exception"))
                .andExpect(status().isInternalServerError())
                .andDo(print())
                .andDo(document("runtime-exception"));
    }

    @Test
    void itemsSuccess() throws Exception {
        var item = new Item(null, "ValidName", "ValidMemo");

        this.mvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(item)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("items-success"));
    }

    @Test
    void itemsValidationErrorNameMin() throws Exception {
        var item = new Item(null, "I", "ValidMemo");

        this.mvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(item)))
                .andExpect(status().isInternalServerError())
                .andDo(print())
                .andDo(document("items-validation-error-name-min"));
    }

    @Test
    void itemsValidationErrorNameMaxAndMemoMax() throws Exception {
        var item = new Item(null, "InvalidNameTooLong", "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");

        this.mvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(item)))
                .andExpect(status().isInternalServerError())
                .andDo(print())
                .andDo(document("items-validation-error-name-max-and-memo-max"));
    }
}