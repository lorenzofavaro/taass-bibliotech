package taass.bibliotech.studyhallservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import taass.bibliotech.studyhallservice.entity.BookStudyHall;
import taass.bibliotech.studyhallservice.entity.StudyHall;
import taass.bibliotech.studyhallservice.models.StudyHallForm;
import taass.bibliotech.studyhallservice.repository.BookStudyHallRepository;
import taass.bibliotech.studyhallservice.repository.StudyHallRepository;

import java.util.*;

@Service
public class StudyHallServiceImpl implements StudyHallService {

    @Autowired
    private StudyHallRepository studyHallRepository;

    @Autowired
    private BookStudyHallRepository bookStudyHallRepository;


    @Override
    public List<StudyHall> getAllStudyHalls() {
         return studyHallRepository.findAllEagerBy();
    }

    @Override
    public StudyHall getStudyHall(Long id) {
        StudyHall studyHall = studyHallRepository.findById(id).orElseThrow(()
        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Study hall doesnt exists"));

        return studyHall;
    }

    @Override
    public void deleteStudyHall(Long id) throws ResponseStatusException {
        StudyHall studyHall = studyHallRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Study hall doesnt exist"));

        studyHallRepository.deleteById(id);
    }

    @Override
    public BookStudyHall createBook(Long idStudyHall, Long accountId) {
        BookStudyHall bookStudyHall = new BookStudyHall();
        bookStudyHall.setDate(new Date());
        bookStudyHall.setUserId(accountId);
        bookStudyHall.setStudyHallId(idStudyHall);
        bookStudyHallRepository.save(bookStudyHall);

        StudyHall studyHall = studyHallRepository.findById(idStudyHall).get();
        int avail = studyHall.getAvailability();
        studyHall.setAvailability(avail-1);
        studyHallRepository.save(studyHall);

        return bookStudyHall;

    }

    public void addStudyHall(StudyHallForm studyHallForm) {
        StudyHall studyHall = new StudyHall();
        studyHall.setName(studyHallForm.getName());
        studyHall.setAddress(studyHallForm.getAddress());
        studyHall.setAvailability(studyHallForm.getAvailability());
        studyHallRepository.save(studyHall);
    }

    public void editStudyHall(StudyHallForm studyHallForm) throws Exception {

        StudyHall studyHall = studyHallRepository.findById(studyHallForm.getId()).orElseThrow(() -> new Exception("Study hall doesnt exist"));

        studyHall.setName(studyHallForm.getName());
        studyHall.setAddress(studyHallForm.getAddress());
        studyHall.setAvailability(studyHallForm.getAvailability());
        studyHallRepository.save(studyHall);
    }

    @Override
    public List<BookStudyHall> getAllBookings(Long userId) {
        return bookStudyHallRepository.findAllByUserId(userId);
    }
}