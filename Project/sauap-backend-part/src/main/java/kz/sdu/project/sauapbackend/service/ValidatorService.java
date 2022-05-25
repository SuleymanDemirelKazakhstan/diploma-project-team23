package kz.sdu.project.sauapbackend.service;

import kz.sdu.project.sauapbackend.exception.ValidationException;

public interface ValidatorService<T> {

    void validate(T object) throws ValidationException;

}
