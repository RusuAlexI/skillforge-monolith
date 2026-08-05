package com.skillforge.skillforge_monolith.entity;

import com.skillforge.skillforge_monolith.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Builder
public class LearningSession extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Column(nullable = false)
    private Integer durationMinutes;

    @Column(nullable = false)
    private LocalDate sessionDate;

    @Column(length = 500)
    private String notes;

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public Skill getSkill() {
//        return skill;
//    }
//
//    public void setSkill(Skill skill) {
//        this.skill = skill;
//    }
//
//    public Integer getDurationMinutes() {
//        return durationMinutes;
//    }
//
//    public void setDurationMinutes(Integer durationMinutes) {
//        this.durationMinutes = durationMinutes;
//    }
//
//    public LocalDate getSessionDate() {
//        return sessionDate;
//    }
//
//    public void setSessionDate(LocalDate sessionDate) {
//        this.sessionDate = sessionDate;
//    }
//
//    public String getNotes() {
//        return notes;
//    }
//
//    public void setNotes(String notes) {
//        this.notes = notes;
//    }
}