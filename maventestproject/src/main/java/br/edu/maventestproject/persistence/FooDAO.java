package br.edu.maventestproject.persistence;

import br.edu.maventestproject.service.FooService;

public class FooDAO implements DAO{
    FooService fooService = new FooService();
}
