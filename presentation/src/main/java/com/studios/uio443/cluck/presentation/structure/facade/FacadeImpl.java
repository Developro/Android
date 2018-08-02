package com.studios.uio443.cluck.presentation.structure.facade;

import com.studios.uio443.cluck.presentation.structure.repo.Repository;

import javax.inject.Inject;

public class FacadeImpl implements Facade {
    private Repository repository;

    @Inject
    public FacadeImpl(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String getValue() {
        return "Facade is " + this + "\n" + repository.getValue();
    }
}
