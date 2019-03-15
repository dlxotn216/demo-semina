package app.livecoding.member.interfaces.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Lee Tae Su on 2019-03-15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MemberCreateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 사용자_생성_테스트() throws Exception {
        //Given
        MockHttpServletRequestBuilder body = post("/members")
                .contentType("application/json")
                .content("{  \"memberId\": \"taesu@crscube.co.kr\",  \"memberName\": \"CRS Taesu\",  \"phone\": \"01099952723\"  }");

        //When
        ResultActions perform = this.mockMvc.perform(body);

        //Then
        perform.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.memberKey").exists())
                .andExpect(jsonPath("$.memberId").value("taesu@crscube.co.kr"))
                .andExpect(jsonPath("$.memberName").value("CRS Taesu"))
                .andExpect(jsonPath("$.phone").value("01099952723"));
    }
}