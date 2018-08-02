package com.studios.uio443.cluck.presentation.structure.repo;

import javax.inject.Inject;

public class RepositoryImpl implements Repository {

    @Inject
    public RepositoryImpl() {
    }

    @Override
    public String getValue() {
        return "Repo is " + this;
    }
}
