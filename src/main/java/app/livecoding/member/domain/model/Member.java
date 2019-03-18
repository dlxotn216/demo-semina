package app.livecoding.member.domain.model;

import app.livecoding.team.domain.model.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by taesu at : 2019-03-15
 *
 * 여기에 Member 클래스에 대한 설명을 기술해주세요
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "MEMBER")
@Entity
@SequenceGenerator(name = "MEMBER_SEQ", sequenceName = "MEMBER_SEQ")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "MEMBER_SEQ")
    @Column(name = "MEMBER_KEY")
    private Long memberKey;

    @Column(name = "MEMBER_ID", unique = true, nullable = false)
    private String memberId;

    @Column(name = "MEMBER_NAME", nullable = false)
    private String memberName;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "STARTED_AT")
    @Builder.Default
    private LocalDate startedAt = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "TEAM_KEY")
    private Team team;

    public Member updateMember(String memberName, String phone) {
        this.memberName = memberName;
        this.phone = phone;
        return this;
    }

    public void setTeam(Team team) {
        if (this.team != null) {
            this.team.removeMember(this);
        }

        this.team = team;
        this.team.addMember(this);
    }
}
