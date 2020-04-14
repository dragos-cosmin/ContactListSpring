package ro.jademy.contacts.service;

import org.springframework.stereotype.Service;

@Service
public class ContactService {

    public boolean isEmailValid(String email) {
        return email.length() > 5 && email.contains("@") && email.contains(".");
    }

}
