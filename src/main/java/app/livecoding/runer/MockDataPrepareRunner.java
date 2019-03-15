package app.livecoding.runer;

import app.livecoding.member.domain.model.Member;
import app.livecoding.member.domain.repository.MemberRepository;
import app.livecoding.team.domain.model.Team;
import app.livecoding.team.domain.repository.TeamRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * Created by taesu at : 2019-03-15
 *
 * 여기에 MockDataPrepareRunner 클래스에 대한 설명을 기술해주세요
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@Component
public class MockDataPrepareRunner implements ApplicationRunner {

    private TeamRepository teamRepository;
    private MemberRepository memberRepository;

    public MockDataPrepareRunner(TeamRepository teamRepository, MemberRepository memberRepository) {
        this.teamRepository = teamRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        this.memberRepository.save(Member.builder().memberName("admin").memberId("admin").phone("0000").build());

        this.teamRepository.saveAll(Arrays.asList(
                Team.builder().teamName("team01").build(),
                Team.builder().teamName("team02").build(),
                Team.builder().teamName("team03").build(),
                Team.builder().teamName("team04").build(),
                Team.builder().teamName("team05").build(),
                Team.builder().teamName("team06").build()
        ));

        this.memberRepository.saveAll(Arrays.asList(
                Member.builder().memberId("taesu").memberName("LeeTaeSu").phone("01099952723").team(this.teamRepository.findById(1L).orElseThrow(IllegalArgumentException::new)).build(),
                Member.builder().memberId("mjgu").memberName("GuMinJae").phone("01099568623").team(this.teamRepository.findById(2L).orElseThrow(IllegalArgumentException::new)).build(),
                Member.builder().memberId("james").memberName("James").phone("01056882733").team(this.teamRepository.findById(3L).orElseThrow(IllegalArgumentException::new)).build(),
                Member.builder().memberId("wckim").memberName("KimWoonChul").phone("01059658723").team(this.teamRepository.findById(4L).orElseThrow(IllegalArgumentException::new)).build(),
                Member.builder().memberId("jskim").memberName("KimJiSu").phone("01095686583").team(this.teamRepository.findById(5L).orElseThrow(IllegalArgumentException::new)).build(),
                Member.builder().memberId("jmc90").memberName("JoMinChul").phone("01095686583").team(this.teamRepository.findById(6L).orElseThrow(IllegalArgumentException::new)).build(),
                Member.builder().memberId("yjpark").memberName("ParkYoungJin").phone("01098567723").team(this.teamRepository.findById(1L).orElseThrow(IllegalArgumentException::new)).build(),
                Member.builder().memberId("tshwang").memberName("HwangTaeJung").phone("01023554563").team(this.teamRepository.findById(2L).orElseThrow(IllegalArgumentException::new)).build(),
                Member.builder().memberId("ebseo").memberName("SeoEnBul").phone("01023552523").team(this.teamRepository.findById(3L).orElseThrow(IllegalArgumentException::new)).build(),
                Member.builder().memberId("djjeong").memberName("JeongDaJeong").phone("01092355723").team(this.teamRepository.findById(4L).orElseThrow(IllegalArgumentException::new)).build(),
                Member.builder().memberId("chlee").memberName("LeeChanHo").phone("01091233243").team(this.teamRepository.findById(5L).orElseThrow(IllegalArgumentException::new)).build()
        ));
    }
}
