package com.akr.conference.controllers;

import com.akr.conference.models.Session;
import com.akr.conference.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {
    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping
    public List<Session> list() {
        return sessionRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Session get(@PathVariable Long id) {
        return sessionRepository.getOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Session create (@RequestBody final Session session) {
        return sessionRepository.saveAndFlush(session);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete (@PathVariable Long id) {
        //@ToDo: add logic to delete children records, otherwise it will fail on delete due to FK dependency
        sessionRepository.deleteById(id);
    }

    @RequestMapping(value = {"id"}, method = RequestMethod.PUT)
    public Session update (@PathVariable Long id, @RequestBody Session session) {
        /*
            if using PUT provide all attributes for entity otherwise they will become null,
            for partial updates use PATCH.

            Validation checks can be added to verify all attribute are non-null for PUT request type
         */
        Session oldSession = sessionRepository.getOne(id);
        Objects.requireNonNull(oldSession);
        BeanUtils.copyProperties(session, oldSession, "session_id");
        return sessionRepository.saveAndFlush(oldSession);
    }
}
