package com.comp4680.webportal.controllers;

// 1. Annotation for making this class a MVC Controller
// 2. Annotation for mapping the URL path "/events" or "" to this controller
public class EventsController {
    private static final String SUBPATH = "events/";

    // 3. Dependency injection for a proper repository

    // 4. Dependency injection for a proper validator

    /*
     * 5. Add a method to handle HTTP GET requests for URL paths "", "/", "index",
     * and send all the events records to view with key "events". The view is in
     * subpath events/ and the view name is index
     */

    /*
     * 6. Add a method to handle HTTP GET requests for URL path "/create" and send a
     * new Events object to view with key "event". The view is in subpath events/
     * and the view name is form
     */

    /*
     * 7. Add a method to handle HTTP POST requests for URL path "/create" and save
     * the new event. If there is any validation error, including both JPA
     * annotation validation and custom validator validation, send the event object
     * back to view with key "event". The view is in subpath events/ and the view
     * name is form. If the event is saved successfully, redirect to URL path
     * "/index"
     */

    /*
     * 8. Add a method to handle HTTP GET requests for URL path "/edit/{id}". Get
     * the id from the path by proper annotation. If the event is not found with the
     * given id, throw a CustomException with error code "EVT-EDIT" and message
     * "Event not found with id [id]", and return to URL path "/events/index".
     * Otherwise, send the event with the given id to view with key "event". The
     * view is in subpath events/ and the view name is form
     */

    /*
     * 9. Add a method to handle HTTP POST requests for URL path "/edit" and save
     * the updated event. If there is any validation error, including both JPA
     * annotation validation and custom validator validation, send the event object
     * back to view with key "event". The view is in subpath events/ and the view
     * name is form. If the event is saved successfully, redirect to URL path
     * "/index"
     */

    /*
     * 10. Add a method to handle HTTP GET requests for URL path "/delete/{id}". Get
     * the id from the path by proper annotation. If the event is not found with the
     * given id, throw a CustomException with error code "EVT-DEL" and message
     * "Event not found with id [id]", and return to URL path "/events/index". If
     * the event is already ended, throw a CustomException with error code "EVT-DEL"
     * and message "Event is already ended", and return to URL path "/events/index".
     * Otherwise, delete the event with the given id. Redirect to URL path "/index"
     */
}