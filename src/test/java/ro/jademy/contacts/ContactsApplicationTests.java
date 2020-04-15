package ro.jademy.contacts;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ro.jademy.contacts.model.Contact;
import ro.jademy.contacts.repository.ContactRepository;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class ContactsApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ContactRepository contactRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Mockito.doNothing().when(contactRepository.save(Mockito.any(Contact.class)));
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testHomePage() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Contacts")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Get instant access your favorite contacts.")));
    }

    @Test
    public void testCreateUser() throws Exception {

        Contact contact = new Contact();
        contact.setFirstName("John");
        contact.setLastName("Doe");
        contact.setEmail("john@example.com");
        contact.setPhoneNumber("123456");
        contact.setAddress("Some Street nr. 1");

        Mockito.when(contactRepository.findByEmail(contact.getEmail())).thenReturn(Optional.of(contact));

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/contacts/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("firstName", contact.getFirstName()),
                                new BasicNameValuePair("lastName", contact.getLastName()),
                                new BasicNameValuePair("email", contact.getEmail()),
                                new BasicNameValuePair("phoneNumber", contact.getPhoneNumber()),
                                new BasicNameValuePair("address", contact.getAddress()))))))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/contacts/all"));

        Optional<Contact> optFoundContact = contactRepository.findByEmail(contact.getEmail());
        Assert.assertTrue(optFoundContact.isPresent());

        Contact foundContact = optFoundContact.get();
        Assert.assertEquals(contact.getFirstName(), foundContact.getFirstName());
        Assert.assertEquals(contact.getLastName(), foundContact.getLastName());
        Assert.assertEquals(contact.getEmail(), foundContact.getEmail());
        Assert.assertEquals(contact.getPhoneNumber(), foundContact.getPhoneNumber());
        Assert.assertEquals(contact.getAddress(), foundContact.getAddress());

    }

}
