package ro.jademy.contacts.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @NotNull(message = "{contact.email.notNull}")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String address;
    @Column(name = "is_favorite")
    private boolean isFavorite;
    @Column(name = "create_date")
    @CreationTimestamp
    private LocalDateTime createDate;
    @Column(name = "modify_date")
    @UpdateTimestamp
    private LocalDateTime modifyDate;
}
