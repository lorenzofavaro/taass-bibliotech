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

import static java.lang.Integer.min;

@Service
public class StudyHallServiceImpl implements StudyHallService {

    @Autowired
    private StudyHallRepository studyHallRepository;

    @Autowired
    private BookStudyHallRepository bookStudyHallRepository;

    private void computeRealAvailability(List<StudyHall> studyHalls) {
        for (StudyHall studyHall : studyHalls) {
            int totalAvailability = studyHall.getAvailability();
            List<BookStudyHall> bookStudyHalls = bookStudyHallRepository.findAllByStudyHallId(studyHall.getId());
            int studyHallsBookedToday = (int) bookStudyHalls.stream().filter(x -> Objects.equals(x.getDate().getDay(), new Date().getDay())).count();
            studyHall.setAvailability(Integer.max(0, totalAvailability - studyHallsBookedToday));
        }
    }

    @Override
    public List<StudyHall> getAllStudyHalls() {
        List<StudyHall> studyHalls = studyHallRepository.findAllEagerBy();
        computeRealAvailability(studyHalls);
        return studyHalls;
    }

    @Override
    public List<StudyHall> getMostAvailableStudyHalls(Integer count) {
        List<StudyHall> studyHalls = studyHallRepository.findAllEagerBy();
        computeRealAvailability(studyHalls);
        studyHalls.sort(Comparator.comparingInt(StudyHall::getAvailability).reversed());

        return studyHalls.subList(0, min(studyHalls.size(), count));
    }

    @Override
    public StudyHall getStudyHall(Long id) {
        StudyHall studyHall = studyHallRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Study hall doesn't exists"));

        return studyHall;
    }

    @Override
    public void deleteStudyHall(Long id) throws ResponseStatusException {
        StudyHall studyHall = studyHallRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Study hall doesn't exist"));

        studyHallRepository.deleteById(id);
    }

    @Override
    public BookStudyHall createBook(Long idStudyHall, Long accountId) {
        List<BookStudyHall> dbBookStudyHalls = bookStudyHallRepository.findAllByUserId(accountId);
        // If the user has already another booking today for some studyhall, we denied the booking
        if (dbBookStudyHalls != null && dbBookStudyHalls.size() > 0) {
            if (dbBookStudyHalls.stream().anyMatch(x -> x.getDate().getDay() == new Date().getDay())) {
                return null;
            }
        }
        BookStudyHall bookStudyHall = new BookStudyHall();
        bookStudyHall.setDate(new Date());
        bookStudyHall.setUserId(accountId);
        bookStudyHall.setStudyHallId(idStudyHall);
        bookStudyHallRepository.save(bookStudyHall);

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
        StudyHall studyHall = studyHallRepository.findById(studyHallForm.getId()).orElseThrow(() -> new Exception("Study hall doesn't exist"));
        studyHall.setName(studyHallForm.getName());
        studyHall.setAddress(studyHallForm.getAddress());
        studyHall.setAvailability(studyHallForm.getAvailability());
        studyHallRepository.save(studyHall);
    }

    @Override
    public List<BookStudyHall> getAllBookings(Long userId) {
        return bookStudyHallRepository.findAllByUserId(userId);
    }

    @Override
    public List<BookStudyHall> getTodayBookings(Long userId) {
        List<BookStudyHall> todayBookings = new ArrayList<>();
        List<BookStudyHall> bookings = bookStudyHallRepository.findAllByUserId(userId);
        for (BookStudyHall bookStudyHall : bookings) {
            if (bookStudyHall.getDate().getDay() == new Date().getDay()) {
                todayBookings.add(bookStudyHall);
            }
        }
        System.out.println("bookings: " + Arrays.toString(bookings.toArray()));
        System.out.println("todayBookings: " + Arrays.toString(todayBookings.toArray()));
        return todayBookings;
    }
}