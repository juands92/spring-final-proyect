package com.cev.finalproyect.proyectservices.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;


    public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "binary(16)", updatable = false, nullable = false)
    private UUID id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Session> sessions;
    
    @ManyToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Task> tasks;  


    @Column(nullable = false)
    private String name;
    
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_expenses",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "expense_id")
    )
    
    private List<Expense> expenses = new ArrayList<>();
  

    public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private Boolean termsAccepted;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    @JsonIgnore
    private String password;

    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_id")
    private Home home;

 
    public User() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getTermsAccepted() {
        return termsAccepted;
    }

    public void setTermsAccepted(Boolean termsAccepted) {
        this.termsAccepted = termsAccepted;
    }

    //public Date getDateOfBirth() {
      //  return dateOfBirth;
    //}

   // public void setDateOfBirth(Date dateOfBirth) {
     //   this.dateOfBirth = dateOfBirth;
   // }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private String name;
        private String lastName;
        private String email;
        private Boolean termsAccepted;
        //private Date dateOfBirth;
        private String password;
        private String role;
        private Home home;

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder termsAccepted(Boolean termsAccepted) {
            this.termsAccepted = termsAccepted;
            return this;
        }

       // public UserBuilder dateOfBirth(Date dateOfBirth) {
         //   this.dateOfBirth = dateOfBirth;
           // return this;
        //}

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder role(String role) {
            this.role = role;
            return this;
        }

        public UserBuilder home(Home home) {
            this.home = home;
            return this;
        }

        public User build() {
            User user = new User();
            user.name = this.name;
            user.lastName = this.lastName;
            user.email = this.email;
            user.termsAccepted = this.termsAccepted;
            //user.dateOfBirth = this.dateOfBirth;
            user.password = this.password;
            user.role = this.role;
            user.home = this.home;
            return user;
        }
    }
}
