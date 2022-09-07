package taass.bibliotech.studyhallservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taass.bibliotech.studyhallservice.entity.BookStudyHall;

import java.util.List;

public interface BookStudyHallRepository extends JpaRepository<BookStudyHall, Long> {
    List<BookStudyHall> findAllByUserId(Long userId);

    List<BookStudyHall> findAllByStudyHallId(Long studyHallId);
}
