package com.comp4680.webportal.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.comp4680.webportal.models.Events;
import com.comp4680.webportal.repos.EventsRepo;

@Component
@SuppressWarnings("null")
public class EventsValidator implements Validator {

    @Autowired
    private EventsRepo repo;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Events.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Events event = (Events) target;

        // check if the code is already used by another event
        if (!repo.findEventsWithSameCode(event.getId(), event.getCode()).isEmpty()) {
            errors.rejectValue("code", "events.code.duplicate", "Code already exists");
        }

        // If the event status is ended in DB, then it should not be updated or deleted.
        if (repo.isEventsEnded(event.getId()).isPresent()) {
            errors.reject("events.status.ended", "Event is already ended");
        }
    }
}
