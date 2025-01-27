package com.comp4680.webportal.repos;

/*
* 1. extends this interface with proper Abstract Class so that it can provide
* JPA functions on entity Events
*/
public interface EventsRepo {

    /*
     * 2. Define the query method by adding proper annotation and parameters.
     * The query should return a list of Events that have the same code as the
     * given code and the id is not the same as the given id.
     * The return type of the query should be List<Events> and accept two parameters
     * id and code.
     */

    /*
     * 3. Define the query method by adding proper annotation and parameters.
     * The query should return an Optional<Events> that have the same id as the
     * given id and the status is 'E'.
     * The return type of the query should be Optional<Events> and accept one
     * parameter.
     */
}