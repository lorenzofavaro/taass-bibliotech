package taass.bibliotech.studyhallservice.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class StudyHallForm {

    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    private int availability;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getAvailability() {
        return availability;
    }
}
