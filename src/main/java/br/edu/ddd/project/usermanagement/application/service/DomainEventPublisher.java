package br.edu.ddd.project.usermanagement.application.service;

public interface DomainEventPublisher {
    void publish(Object event);
}
