package taass.bibliotech.studyhallservice.service;

import taass.bibliotech.studyhallservice.entity.BookStudyHall;
import taass.bibliotech.studyhallservice.entity.StudyHall;
import taass.bibliotech.studyhallservice.models.StudyHallForm;

import java.util.List;

public interface StudyHallService {


    List<StudyHall> getAllStudyHalls();

    StudyHall getStudyHall(Long id);

    void deleteStudyHall(Long id);

    BookStudyHall createBook(Long id, Long accountId);

    void addStudyHall(StudyHallForm studyHallForm);

    void editStudyHall(StudyHallForm studyHallForm) throws Exception;

    List<BookStudyHall> getAllBookings(Long id);

}
