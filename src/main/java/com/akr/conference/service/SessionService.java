package com.akr.conference.service;

import com.akr.conference.ApplicationException;
import com.akr.conference.models.Session;
import com.akr.conference.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    public Session getById (Long id) {
        Optional<Session> optionalSession = sessionRepository.findById(id);

        if(optionalSession.isPresent())
            return optionalSession.get();
        else
            throw new ApplicationException("No such id exists");

    }
}
