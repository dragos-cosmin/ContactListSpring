package ro.jademy.contacts.repository;

import org.springframework.data.repository.CrudRepository;
import ro.jademy.contacts.model.Contact;

import java.util.Optional;

public interface ContactRepository extends CrudRepository<Contact, Long> {

    Optional<Contact> findByEmail(String email);

}
