package app.livecoding.config;

import app.livecoding.member.domain.model.Member;
import app.livecoding.member.domain.repository.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Created by taesu at : 2019-03-12
 *
 * 여기에 EnableAuditConfiguration 클래스에 대한 설명을 기술해주세요
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class EnableAuditConfiguration {
    private MemberRepository memberRepository;

    public EnableAuditConfiguration(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public AuditorAware<Member> auditorAware() {
        return () -> this.memberRepository.findByMemberId("admin");
    }
}
