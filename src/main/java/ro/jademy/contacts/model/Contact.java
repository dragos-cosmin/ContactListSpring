package ro.jademy.contacts.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Contact {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private boolean isFavorite;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

}
