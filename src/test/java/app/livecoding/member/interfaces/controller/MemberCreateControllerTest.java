package app.livecoding.member.interfaces.controller;

import app.livecoding.member.domain.model.Member;
import app.livecoding.member.domain.repository.MemberRepository;
import lombok.NonNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.history.Revisions;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
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
    private MemberRepository memberRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 사용자_생성_테스트() throws Exception {
        //Given
        MockHttpServletRequestBuilder body = post("/members")
                .contentType("application/json")
                .content("{  \"memberId\": \"leetaesu\",  \"memberName\": \"CRS Taesu\",  " +
                                 " \"email\": \"taesu@crscube.co.kr\", \"phone\": \"01099952723\"  }");

        //When
        ResultActions perform = this.mockMvc.perform(body);

        //Then
        //Assertion for response body
        perform.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.memberKey").exists())
                .andExpect(jsonPath("$.memberId").value("leetaesu"))
                .andExpect(jsonPath("$.email").value("taesu@crscube.co.kr"))
                .andExpect(jsonPath("$.memberName").value("CRS Taesu"))
                .andExpect(jsonPath("$.phone").value("01099952723"))
        ;

        //Assertion for revision
        Revisions<Integer, Member> revisions = this.memberRepository.findRevisions(1L);
        assertThat(revisions.getContent().size()).isEqualTo(1);
        assertThat(revisions.getContent().get(0)).isNotNull();

        Member entity = revisions.getContent().get(0).getEntity();
        assertThat(entity.getMemberId()).isEqualTo("leetaesu");
        assertThat(entity.getEmail()).isEqualTo("taesu@crscube.co.kr");
        assertThat(entity.getMemberName()).isEqualTo("CRS Taesu");
        assertThat(entity.getPhone()).isEqualTo("01099952723");
    }
}