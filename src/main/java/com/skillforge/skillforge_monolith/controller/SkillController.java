package com.skillforge.skillforge_monolith.controller;


import com.skillforge.skillforge_monolith.entity.Skill;
import com.skillforge.skillforge_monolith.repository.SkillRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Map;

@RestController
@AllArgsConstructor
public class SkillController {
    private SkillRepository skillRepository;

    @GetMapping("/api/skills")
    public ResponseEntity<Skill> getSkill(@RequestParam String id) {
        return  ResponseEntity.ok(skillRepository.getReferenceById(id));
    }
//debug why it's not saving into repository
    @PostMapping("/api/skills")
    public ResponseEntity<Skill> postSkill(@RequestBody Skill skill) {
        return ResponseEntity.ok(skillRepository.save(skill));
    }
}
