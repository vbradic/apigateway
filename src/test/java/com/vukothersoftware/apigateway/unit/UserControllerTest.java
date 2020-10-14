package com.vukothersoftware.apigateway.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import static org.assertj.core.api.Assertions.assertThat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vukothersoftware.controller.UserController;
import com.vukothersoftware.exception.BadUserParametersException;
import com.vukothersoftware.exception.UserNotFoundException;
import com.vukothersoftware.service.UserConsumer;
import com.vuksoftware.exception.DataNotFoundException;
import com.vuksoftware.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserConsumer userConsumer;



    @Test
    public void getUserById_TestOK() throws Exception {

        User user = new User("1", "Vuk", "Bradic", "1987");
        Mockito.when(userConsumer.getUserById(Mockito.anyString())).thenReturn(user);
        String inputInJson = this.mapToJson(user);

        String URI = "/user/1";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();

        assertThat(outputInJson).isEqualTo(inputInJson);
        assertEquals(HttpStatus.OK.value(), response.getStatus());


    }

    @Test
    public void getUserById_Test404() throws Exception {

        Mockito.when(userConsumer.getUserById(Mockito.anyString())).thenThrow(new UserNotFoundException("No user for given id"));

        String URI = "/user/2";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result =  mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(404, response.getStatus());
        assertEquals("{\"message\":\"No user for given id\"}", response.getContentAsString());
    }

    @Test
    public void saveUser_TestOK() throws Exception {

        User user = new User("1", "Vuk", "Bradic", "1987");
        Mockito.when(userConsumer.saveUser(any(User.class))).thenReturn(user);
        String inputInJson = this.mapToJson(user);

        String URI = "/user";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(inputInJson);

        MvcResult result =  mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();

        assertThat(outputInJson).isEqualTo(inputInJson);
        assertEquals(HttpStatus.OK.value(), response.getStatus());


    }

    @Test
    public void saveUser_BadRequest() throws Exception {

        User user = new User();
        user.setName("testName");
        Mockito.when(userConsumer.saveUser(any(User.class))).thenThrow(new BadUserParametersException("Wrong params"));
        String inputInJson = this.mapToJson(user);

        String URI = "/user";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(inputInJson);

        MvcResult result =  mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(400, response.getStatus());
        assertEquals("{\"message\":\"Wrong params\"}", response.getContentAsString());

    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
