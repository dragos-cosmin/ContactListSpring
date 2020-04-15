package ro.jademy.contacts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.jademy.contacts.exception.ResourceNotFoundException;
import ro.jademy.contacts.model.Contact;
import ro.jademy.contacts.repository.ContactRepository;

import java.util.Optional;

@Controller
@RequestMapping(value = "/contacts")
public class ContactsController {

    private static final Logger logger = LoggerFactory.getLogger(ContactsController.class);

    @Autowired
    private ContactRepository contactRepository;

    @RequestMapping("/all")
    public String index(Model model) {

        logger.debug("Entered contacts index");

        model.addAttribute("contacts", contactRepository.findAll());



        logger.info("Contact by email: " + contactRepository.findByEmail("gigi@example.com"));



        return "contacts/all";
    }

    @RequestMapping("/details/{id}")
    public String getContactDetails(@PathVariable Long id, Model model) {

        logger.debug("Entered contacts details");

        Optional<Contact> optContact = contactRepository.findById(id);

        if (optContact.isPresent()) {
            model.addAttribute("contact", optContact.get());

            return "contacts/details";
        }

        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "/create")
    public String showContactCreatePage(Model model) {

        logger.debug("Show contacts create");
        model.addAttribute("contact", new Contact());

        return "contacts/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createContact(@ModelAttribute Contact newContact) {

        logger.debug("Creating contact");

        contactRepository.save(newContact);

        return "redirect:/contacts/all";
    }

    @RequestMapping(value = "/edit/{id}")
    public String showContactEditPage(@PathVariable Long id, Model model) {

        logger.debug("Show contacts edit");

        Optional<Contact> optContact = contactRepository.findById(id);

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


        Optional<Contact> optContact = contactRepository.findById(editedContact.getId());

        if (optContact.isPresent()) {
            Contact contact = optContact.get();
            contact.setFirstName(editedContact.getFirstName());
            contact.setLastName(editedContact.getLastName());
            contact.setEmail(editedContact.getEmail());
            contact.setPhoneNumber(editedContact.getPhoneNumber());
            contact.setAddress(editedContact.getAddress());
            contact.setFavorite(editedContact.isFavorite());

            contactRepository.save(contact);
        }

        return "redirect:/contacts/details/" + editedContact.getId();
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteContact(@PathVariable Long id) {

        logger.debug("Deleting contact");

        contactRepository.deleteById(id);

        return "redirect:/contacts/all";
    }

}
