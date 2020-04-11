package ro.jademy.contacts.controller;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.jademy.contacts.exception.ResourceNotFoundException;
import ro.jademy.contacts.model.Contact;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/contacts")
public class ContactsController {

    private static final Logger logger = LoggerFactory.getLogger(ContactsController.class);

    private Map<Long, Contact> contacts = initContacts();

    @RequestMapping("/all")
    public String index(Model model) {

        logger.debug("Entered contacts index");

        model.addAttribute("contacts", contacts);

        return "contacts/all";
    }

    @RequestMapping("/details/{id}")
    public String getContactDetails(@PathVariable Long id, Model model) {

        logger.debug("Entered contacts details");

        Optional<Contact> optContact = Optional.ofNullable(contacts.get(id));

        if (optContact.isPresent()) {
            model.addAttribute("contact", optContact.get());

            return "contacts/details";
        }

        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "/create")
    public String showContactCreatePage(Model model) {

        logger.debug("Show contacts create");

        return "home";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createContact(Model model) {

        logger.debug("Creating contact");

        return "home";
    }

    @RequestMapping(value = "/edit/{id}")
    public String showContactEditPage(@PathVariable Long id, Model model) {

        logger.debug("Show contacts edit");

        Optional<Contact> optContact = Optional.ofNullable(contacts.get(id));

        if (optContact.isPresent()) {
            model.addAttribute("contact", optContact.get());

            return "contacts/edit";
        }

        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editContact(@ModelAttribute Contact editedContact) {

        logger.debug("Saving edited contact");

        logger.debug("Updated Contact: " + editedContact.toString());

        Contact contact = contacts.get(editedContact.getId());
        contact.setFirstName(editedContact.getFirstName());
        contact.setLastName(editedContact.getLastName());
        contact.setEmail(editedContact.getEmail());
        contact.setPhoneNumber(editedContact.getPhoneNumber());
        contact.setAddress(editedContact.getAddress());
        contact.setFavorite(editedContact.isFavorite());
        contact.setModifyDate(LocalDateTime.now());

        return "redirect:/contacts/details/" + contact.getId();
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteContact(@PathVariable Long id) {

        logger.debug("Deleting contact");
        Optional<Contact> optContact = Optional.ofNullable(contacts.get(id));

        if (optContact.isPresent()) {
            contacts.remove(optContact.get().getId());

            return "redirect:/contacts/all";
        }

        throw new ResourceNotFoundException();
    }

    private Map<Long, Contact> initContacts() {

        Faker faker = new Faker();
        Map<Long, Contact> contacts = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            Contact c = new Contact();
            long id = i + 101;
            c.setId(id);
            c.setFirstName(faker.name().firstName());
            c.setLastName(faker.name().lastName());
            c.setEmail(faker.internet().emailAddress());
            c.setPhoneNumber(faker.phoneNumber().phoneNumber());
            c.setAddress(faker.address().fullAddress());
            c.setCreateDate(LocalDateTime.now());
            c.setModifyDate(LocalDateTime.now());

            if (id == 101) {
                c.setFavorite(true);
            }

            contacts.put(id, c);
        }
        return contacts;
    }

}
