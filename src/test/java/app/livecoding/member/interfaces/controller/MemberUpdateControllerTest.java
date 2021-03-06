package app.livecoding.member.interfaces.controller;

import app.livecoding.member.domain.model.Member;
import app.livecoding.member.domain.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.history.Revisions;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Lee Tae Su on 2019-03-15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MemberUpdateControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 사용자_변경_테스트() throws Exception {
        //Given
        Member save
                = this.memberRepository.save(Member.builder().memberId("test")
                                                     .memberName("test01")
                                                     .email("taesu@crscube.co.kr").phone("01099991111").build());

        //When
        MockHttpServletRequestBuilder body = put("/members/" + save.getMemberKey())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{  \"memberName\": \"CRS Taesu\",  \"phone\": \"01099952723\"  }");
        ResultActions perform = this.mockMvc.perform(body);

        //Then
        //Assertion for response body
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberKey").value(save.getMemberKey()))
                .andExpect(jsonPath("$.memberId").value(save.getMemberId()))
                .andExpect(jsonPath("$.email").value("taesu@crscube.co.kr"))
                .andExpect(jsonPath("$.memberName").value("CRS Taesu"))
                .andExpect(jsonPath("$.phone").value("01099952723"))
        ;

        //Assertion for revision
        Revisions<Integer, Member> revisions = this.memberRepository.findRevisions(save.getMemberKey());
        assertThat(revisions.getContent().size()).isEqualTo(2);
        assertThat(revisions.getContent().get(1)).isNotNull();

        Member entity = revisions.getContent().get(1).getEntity();
        assertThat(entity.getMemberId()).isEqualTo(save.getMemberId());
        assertThat(entity.getEmail()).isEqualTo("taesu@crscube.co.kr");
        assertThat(entity.getMemberName()).isEqualTo("CRS Taesu");
        assertThat(entity.getPhone()).isEqualTo("01099952723");
    }
}