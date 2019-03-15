package app.livecoding.team.interfaces.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Lee Tae Su on 2019-03-15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TeamCreateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 팀_멤버와_함께_생성_테스트() throws Exception {
        //Given
        MockHttpServletRequestBuilder post = post("/teams")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                                 "  \"teamName\": \"Dev1\",\n" +
                                 "  \"members\": [\n" +
                                 "    {\n" +
                                 "      \"memberId\": \"taesu@crscube.co.kr\",\n" +
                                 "      \"memberName\": \"CRS Taesu\",\n" +
                                 "      \"phone\": \"01099952723\"\n" +
                                 "    },\n" +
                                 "    {\n" +
                                 "      \"memberId\": \"lee@crscube.co.kr\",\n" +
                                 "      \"memberName\": \"CRS Lee\",\n" +
                                 "      \"phone\": \"01055952723\"\n" +
                                 "    }\n" +
                                 "  ]\n" +
                                 "}");

        //When
        ResultActions perform = this.mockMvc.perform(post);

        //Then
        perform.andDo(print())
                .andExpect(status().isCreated());
    }
}