package org.vnsemkin.taskmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.vnsemkin.taskmanagementsystem.AbstractControllerTest;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.vnsemkin.taskmanagementsystem.configuration.constants.TMSConstants.TOKEN_URL;

public class AuthControllerTests extends AbstractControllerTest {

    @Test
    public void shouldReturnJWT() throws Exception {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("email", "user@gmail.com");
        requestMap.put("password", "user");
        String jsonContent =
                new ObjectMapper().writeValueAsString(requestMap);
        MvcResult mvcResult = perform(MockMvcRequestBuilders.post(TOKEN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("token"));
    }
}
