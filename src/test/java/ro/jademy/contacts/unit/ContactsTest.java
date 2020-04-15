package ro.jademy.contacts.unit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import ro.jademy.contacts.service.ContactService;

@RunWith(SpringRunner.class)
public class ContactsTest {

    @Test
    public void testContactEmailValid() {
        // given
        ContactService contactService = new ContactService();

        // when
        boolean result = contactService.isEmailValid("test@example.com");

        // then
        Assert.assertTrue(result);
    }

    @Test
    public void testContactEmailNotValidDot() {
        // given
        ContactService contactService = new ContactService();

        // when
        boolean result = contactService.isEmailValid("test@examplecom");

        // then
        Assert.assertFalse(result);
    }

    @Test
    public void testContactEmailNotValidAt() {
        // given
        ContactService contactService = new ContactService();

        // when
        boolean result = contactService.isEmailValid("testexample.com");

        // then
        Assert.assertFalse(result);
    }

    @Test
    public void testContactEmailNotValidLength() {
        // given
        ContactService contactService = new ContactService();

        // when
        boolean result = contactService.isEmailValid("test");

        // then
        Assert.assertFalse(result);
    }

}
