package ro.jademy.contacts;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ro.jademy.contacts.model.Contact;
import ro.jademy.contacts.repository.ContactRepository;

import java.time.LocalDateTime;

@SpringBootApplication
public class ContactsApplication {

    @Autowired
    private ContactRepository contactRepository;

    public static void main(String[] args) {
        SpringApplication.run(ContactsApplication.class, args);
    }

    @Bean
    public void initContacts() {
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
            Contact contact = new Contact();
            contact.setFirstName(faker.name().firstName());
            contact.setLastName(faker.name().lastName());
            contact.setEmail(faker.internet().emailAddress());
            contact.setPhoneNumber(faker.phoneNumber().phoneNumber());
            contact.setAddress(faker.address().fullAddress());
            contact.setCreateDate(LocalDateTime.now());
            contact.setModifyDate(LocalDateTime.now());

            contactRepository.save(contact);
        }
    }

}
