package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Bob on 23/10/2014.
 */
@Entity
@Table(name = "users")
public class User {

    private String email;

    @Id
    public String getEmail() {
        return email;
    }

    public void setEmail(String email){ this.email = email; }


}