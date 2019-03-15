package app.livecoding.team.domain.repository;

import app.livecoding.team.domain.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by taesu at : 2019-03-15
 *
 * 여기에 TeamRepository 인터페이스에 대한 설명을 기술해주세요
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
public interface TeamRepository extends JpaRepository<Team, Long> {
}
