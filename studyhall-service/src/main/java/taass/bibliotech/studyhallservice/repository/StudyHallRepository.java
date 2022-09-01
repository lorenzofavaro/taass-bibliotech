package taass.bibliotech.studyhallservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taass.bibliotech.studyhallservice.entity.StudyHall;

import java.util.List;

public interface StudyHallRepository extends JpaRepository<StudyHall, Long> {

    List<StudyHall> findAllEagerBy();
}
