package taass.bibliotech.studyhallservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taass.bibliotech.studyhallservice.entity.BookStudyHall;
import taass.bibliotech.studyhallservice.entity.StudyHall;
import taass.bibliotech.studyhallservice.models.StudyHallForm;
import taass.bibliotech.studyhallservice.service.StudyHallService;
import taass.bibliotech.studyhallservice.utility.RestUtility;

import javax.validation.Valid;
import java.util.List;

import static taass.bibliotech.studyhallservice.utility.RestUtility.HEADER_AUTH;

@CrossOrigin
@RestController
public class StudyHallController {

    @Autowired
    StudyHallService studyHallService;

    @GetMapping("/studyhalls/all")
    public ResponseEntity<List<StudyHall>> getAllStudyHalls() {
        List<StudyHall> studyHalls = studyHallService.getAllStudyHalls(false);
        return ResponseEntity.ok(studyHalls);
    }

    @GetMapping("/studyhalls/admin_all")
    public ResponseEntity<List<StudyHall>> getAllStudyHallsAdmin() {
        List<StudyHall> studyHalls = studyHallService.getAllStudyHalls(true);
        return ResponseEntity.ok(studyHalls);
    }

    @GetMapping("/studyhalls/most_available/{count}")
    public ResponseEntity<List<StudyHall>> getMostAvailableStudyHalls(@PathVariable Integer count) {
        List<StudyHall> studyHalls = studyHallService.getMostAvailableStudyHalls(count);
        return ResponseEntity.ok(studyHalls);
    }

    @GetMapping("/studyhalls/{id}")
    public ResponseEntity<StudyHall> getStudyHall(@PathVariable Long id) throws Exception {
        StudyHall studyHall = studyHallService.getStudyHall(id);
        return ResponseEntity.ok(studyHall);
    }

    @DeleteMapping("/studyhalls/delete/{id}")
    public HttpEntity<String> deleteStudyHall(@PathVariable Long id) throws Exception {
        studyHallService.deleteStudyHall(id);
        return ResponseEntity.ok("Study hall deleted");
    }

    @PostMapping("/studyhalls/add")
    public HttpEntity<String> addStudyHall(@Valid @RequestBody StudyHallForm studyHallForm) {
        studyHallService.addStudyHall(studyHallForm);
        return ResponseEntity.ok("Product added");
    }

    @PostMapping("/studyhalls/edit")
    public HttpEntity<String> editProduct(@Valid @RequestBody StudyHallForm studyHallForm) throws Exception {
        studyHallService.editStudyHall(studyHallForm);
        return ResponseEntity.ok("Study hall edited");
    }

    @GetMapping("/bookings/all")
    public ResponseEntity<List<BookStudyHall>> getBookings(@RequestHeader(HEADER_AUTH) String tokenHeader) {
        Long userId = RestUtility.getUserId(tokenHeader);
        List<BookStudyHall> bookings = studyHallService.getAllBookings(userId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/bookings/today")
    public ResponseEntity<List<BookStudyHall>> getTodayBookings(@RequestHeader(HEADER_AUTH) String tokenHeader) {
        Long userId = RestUtility.getUserId(tokenHeader);
        List<BookStudyHall> bookings = studyHallService.getTodayBookings(userId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/bookings/create/{id}")
    public ResponseEntity<BookStudyHall> createBook(@RequestHeader(HEADER_AUTH) String tokenHeader, @PathVariable Long id) {
        Long accountId = RestUtility.getUserId(tokenHeader);
        BookStudyHall bookStudyHall = studyHallService.createBook(id, accountId);
        return ResponseEntity.ok(bookStudyHall);
    }

    @DeleteMapping("/bookings/cancel/{id}")
    public ResponseEntity<Boolean> cancelBooking(@RequestHeader(HEADER_AUTH) String tokenHeader, @PathVariable Long id) {
        studyHallService.cancelBooking(id);
        return ResponseEntity.ok(true);
    }

}
