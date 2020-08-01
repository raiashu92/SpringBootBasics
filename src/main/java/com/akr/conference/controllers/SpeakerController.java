package com.akr.conference.controllers;

import com.akr.conference.models.Speaker;
import com.akr.conference.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakerController {
    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List<Speaker> getAll () {
        return speakerRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Speaker getbyId (@PathVariable Long id) {
        return speakerRepository.getOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Speaker create (@RequestBody final Speaker speaker) {
        return speakerRepository.saveAndFlush(speaker);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete (@PathVariable Long id) {
        speakerRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Speaker update (@PathVariable Long id, @RequestBody Speaker speaker) {
        Speaker oldSpeaker = speakerRepository.getOne(id);
        Objects.requireNonNull(oldSpeaker);
        BeanUtils.copyProperties(speaker, oldSpeaker, "speaker_id");
        return speakerRepository.saveAndFlush(oldSpeaker);
    }
}
