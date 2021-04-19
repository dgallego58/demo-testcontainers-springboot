package co.com.courseupdate.api.controller;

import co.com.courseupdate.data.UserDto;
import co.com.courseupdate.data.UserMapper;
import co.com.courseupdate.models.DocType;
import co.com.courseupdate.models.User;
import co.com.courseupdate.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(controllers = {UserController.class})
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    UserService userService;

    @Test
    void serializedOptionalUserDtoShouldBeOk() throws Exception {
        UserDto userDto = stubUserDto();
        when(userService.getUserContainingKeyword(anyString())).thenReturn(Optional.of(userDto));
        MvcResult result = mockMvc.perform(
                get("/api/user")
                        .param("keyword", "Name"))
                .andExpect(
                        status().isOk())
                .andDo(document("index",
                                responseFields(
                                        fieldWithPath("name").description("The user Name [string]"),
                                        fieldWithPath("lastName").description("The user lastName [string]"),
                                        fieldWithPath("docType").description("Document type for this user [ENUM]"),
                                        fieldWithPath("dni").description("document of national identifier [string]")
                                )))
                .andReturn();
        String actualJsonResponse = result.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(userDto), actualJsonResponse);
    }

    UserDto stubUserDto(){
        return UserMapper.INSTANCE.toUserDto(User.create()
                                                     .setName("TestName")
                                                     .setLastName("TestLastName")
                                                     .setDocType(DocType.CC)
                                                     .setDni("1023")
                                                     .setId(10L));
    }

}