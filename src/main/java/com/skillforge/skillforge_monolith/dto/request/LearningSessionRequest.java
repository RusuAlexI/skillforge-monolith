package com.skillforge.skillforge_monolith.dto.request;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class LearningSessionRequest {
    @NotNull
    @Positive
    private Integer durationMinutes;

    @NotNull @PastOrPresent
    private LocalDate sessionDate;

    @Size(max = 500)
    private String notes;
}