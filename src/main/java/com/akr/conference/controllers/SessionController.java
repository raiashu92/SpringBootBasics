package com.akr.conference.controllers;

import com.akr.conference.ApplicationException;
import com.akr.conference.models.Session;
import com.akr.conference.repositories.SessionRepository;
import com.akr.conference.service.SessionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {
    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionService sessionService;

    @GetMapping
    public List<Session> list() {
        return (List<Session>) sessionRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public ResponseEntity<Session> get(@PathVariable Long id) {
        try {
            return new ResponseEntity<Session>(sessionService.getById(id), HttpStatus.OK);
        } catch (ApplicationException exception) {
            return new ResponseEntity<Session>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Session create (@RequestBody final Session session) {
        return sessionRepository.save(session);
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
        Session oldSession = sessionService.getById(id);
        Objects.requireNonNull(oldSession);
        BeanUtils.copyProperties(session, oldSession, "session_id");
        return sessionRepository.save(oldSession);
    }
}
