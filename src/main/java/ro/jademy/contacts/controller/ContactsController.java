package ro.jademy.contacts.controller;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.jademy.contacts.model.Contact;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/contacts")
public class ContactsController {

    private static final Logger logger = LoggerFactory.getLogger(ContactsController.class);

    private List<Contact> contacts = initContacts();

    @RequestMapping("/all")
    public String index(Model model) {

        logger.debug("Entered contacts index");

        model.addAttribute("contacts", contacts);

        return "contacts/all";
    }

    @RequestMapping("/details/{id}")
    public String getContactDetails(@PathVariable Long id, Model model) {

        logger.debug("Entered contacts details");

        return "home";
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

        return "home";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editContact(@PathVariable Long id, Model model) {

        logger.debug("Editing contact");

        return "home";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String deleteContact(@PathVariable Long id, Model model) {

        logger.debug("Deleting contact");

        return "home";
    }

    private List<Contact> initContacts() {

        Faker faker = new Faker();
        List<Contact> contacts = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Contact c = new Contact();
            c.setId(i + 101);
            c.setFirstName(faker.name().firstName());
            c.setLastName(faker.name().lastName());
            c.setEmail(faker.internet().emailAddress());
            c.setPhoneNumber(faker.phoneNumber().phoneNumber());
            contacts.add(c);
        }
        return contacts;
    }

}
