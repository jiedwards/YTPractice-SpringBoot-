package com.example.demo.dao;


import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {

    private static List<Person> DB = new ArrayList<>();
    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
       Optional<Person> personExists = selectPersonById(id);
       if (personExists.isEmpty()) {
           return 0;
       }
       DB.remove(personExists.get());
       return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person updateP) {
        return selectPersonById(id)
                .map(person -> {
                    int indexOfPersontoUpdate = DB.indexOf(person);
                    if (indexOfPersontoUpdate >= 0) {
                        DB.set(indexOfPersontoUpdate, new Person(id, updateP.getName()));
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }
}
