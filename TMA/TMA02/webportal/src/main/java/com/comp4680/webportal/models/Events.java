package com.comp4680.webportal.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

// 1. Define the class as an entity by adding proper annotation
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Events implements Serializable {
    // 2. Define the primary key by adding proper annotation
    /*
     * 3. Define the primary key generation strategy by adding proper annotation
     * with generation type = IDENTITY
     */
    private int id;

    /*
     * 4. Define the column by adding proper annotation and constraints :
     * length=100, unique and not null
     */
    /*
     * 5. Add proper validation annotation to check the length of this field within
     * 3 to 100. If violated, display error message
     * "Code must be between 3 and 100 characters"
     */
    /*
     * 6. Add proper validation annotation to check if this field is not null, if
     * violated, display error message "Code is required"
     */
    private String code;

    /*
     * 7. Define the column by adding proper annotation and constraints : length=255
     * and not null
     */
    /*
     * 8. Add proper validation annotation to check the length of this field within
     * 3 to 255. If violated, display error message
     * "Name must be between 3 and 255 characters"
     */
    /*
     * 9. Add proper validation annotation to check if this field is not null, if it
     * violated, display error message "Name is required"
     */
    private String name;

    /*
     * 10. Define the column by adding proper annotation and constraints :
     * length=500
     * and not null
     */
    /*
     * 11. Add proper validation annotation to check the length of this field within
     * 3 to 500. If violated, display error message
     * "Description must be between 3 and 500 characters"
     */
    /*
     * 12. Add proper validation annotation to check if this field is not null, if
     * violated, display error message "Description is required"
     */
    private String descr;

    /*
     * 13. Define the column by adding proper annotation and constraints :
     * length=1, not null and default value = 'P' by using
     * columnDefinition = "varchar(1) default 'P'"
     */
    @Pattern(regexp = "[PIE]", message = "Only P, I, E are allowed")
    private String status;

    /*
     * 14. Define the column by adding proper annotation and constraints :
     * not null and the column name in the database is evtDate
     */
    /*
     * 15. Add proper validation annotation to check if this field is a future date.
     * If violated, display error message "Event date must be in the future"
     */
    /*
     * 16. Add proper validation annotation to check if this field is not null, if
     * violated, display error message "Event date is required"
     */
    /* 17. Add proper annotation to define its DB type is Timestamp */
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date eventDate;

    @OneToMany(mappedBy = "event")
    private List<Items> items;

    public String getStatusString() {
        String rtn = "Pending";
        switch (this.status) {
            case "I":
                rtn = "In Progress";
                break;
            case "E":
                rtn = "End";
                break;
            case "P":
            default:
                rtn = "Pending";
                break;
        }
        return rtn;
    }
}
