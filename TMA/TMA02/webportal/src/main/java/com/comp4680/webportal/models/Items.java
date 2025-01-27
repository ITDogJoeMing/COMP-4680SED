package com.comp4680.webportal.models;

import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.validator.constraints.Length;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "event")
public class Items implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false, unique = true)
    @Length(min = 3, max = 100, message = "Code must be between 3 and 100 characters")
    @NotBlank
    private String code;

    @Column(length = 255, nullable = false)
    @Length(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    @NotBlank
    private String name;

    @Column(length = 500, nullable = false)
    @Length(min = 3, max = 500, message = "Description must be between 3 and 500 characters")
    @NotBlank
    private String descr;

    @Column(nullable = false, precision = 10, scale = 2, columnDefinition = "decimal(10,2) default 0.0")
    @DecimalMin(value = "0.0", message = "Price must be greater than 0.0")
    private BigDecimal price;

    @Column(length = 20, nullable = false, columnDefinition = "varchar(1) default 'P'")
    @Pattern(regexp = "[PISW]", message = "Only P, I, S, W are allowed")
    private String status;

    @ManyToOne
    @JoinColumn(name = "eventId", nullable = false)
    private Events event;

    public String getStatusString() {
        String rtn = "Pending";
        switch (this.status) {
            case "I":
                rtn = "In Progress";
                break;
            case "S":
                rtn = "Sold";
                break;
            case "W":
                rtn = "Withdrawn";
                break;
            case "P":
            default:
                rtn = "Pending";
                break;
        }
        return rtn;
    }
}