package org.alma.mircoservices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.alma.mircoservices.domain.Multiplication;
import org.alma.mircoservices.services.MultiplicationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest(MultiplicationController.class)
public class MultiplicationControllerTest {

    @MockBean
    private MultiplicationService multiplicationService;

    @Autowired
    private MockMvc mvc;
    
    private JacksonTester<Multiplication> json;

    @Before
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void getRandomMultiplicationTest() throws Exception{
        given(multiplicationService.createRandomMultiplication())
        .willReturn(new Multiplication(70,20));
        // Prend le mvc, execute un get qui prend un json en sortie et retourne une réponse

        MockHttpServletResponse response = mvc.perform(get("/multiplications/random")
            .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        
        // response à le statut OK
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        // vérification du resultat json
        assertThat(response.getContentAsString()).isEqualTo(json.write(new Multiplication(70,20)).getJson());
        }
    
}