package app.livecoding.team.domain.model;

import app.livecoding.member.domain.model.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by taesu at : 2019-03-15
 *
 * 여기에 Team 클래스에 대한 설명을 기술해주세요
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
@Entity
@SequenceGenerator(name = "TEAM_SEQ", sequenceName = "TEAM_SEQ")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "TEAM_SEQ")
    private Long teamKey;

    private String teamName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team", cascade = CascadeType.ALL)
    private List<Member> members;

    public void addMember(Member member) {
        if (this.members == null) {
            this.members = new ArrayList<>();
        }

        this.members.add(member);
    }

    public void removeMember(Member member) {
        if (CollectionUtils.isEmpty(this.members)) {
            return;
        }

        this.members.removeIf(element -> element.getMemberKey().equals(member.getMemberKey()));
    }
}
