package com.cev.finalproyect.proyectservices.domain;


import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class User implements UserDetails  {
	
	@Id
	@UuidGenerator
	@JdbcTypeCode(java.sql.Types.VARCHAR)
    UUID id;
	
	String name;
	String lastName;
	String email;
	Boolean termsAccepted;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	Date dateOfBirth;
	
	private String password;
	
    String role;

    @ManyToOne
    Home home;
    
    @OneToMany(mappedBy = "user")
    List<Task> tasks;
   

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Home getHome() {
		return home;
	}

	public void setHome(Home home) {
		this.home = home;
	}
	
	public String getPassword() {
		return password;
	}


	/*public List<Home> getHomes() {
		return homes;
	}

	public void setHomes(List<Home> homes) {
		this.homes = homes;
	}*/

	public void setPassword(String password) {
		this.password = password;
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
		return List.of(new SimpleGrantedAuthority(("USER")));
	}



    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private String name;
        private String lastName;
        private String email;
        private Boolean termsAccepted;
        private Date dateOfBirth;
        private String password;
        private String role;

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

        public UserBuilder dateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder role(String role) {
            this.role = role;
            return this;
        }


        public User build() {
            User user = new User();
            user.name = this.name;
            user.lastName = this.lastName;
            user.email = this.email;
            user.termsAccepted = this.termsAccepted;
            user.dateOfBirth = this.dateOfBirth;
            user.password = this.password;
            user.role = this.role;
            return user;
        }
    }
    
}


