package com.store.models;

import jakarta.persistence.*;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name="user")
public class User implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String firstName;
    
    @Column(nullable=false)
    private String lastName;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Privilege> userRoles = new ArrayList<>();

	@OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Address> address;

	public User()
	{
		super();
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public List<Privilege> getRoles()
	{
		return userRoles;
	}

	public void setRoles(List<Privilege> userRoles)
	{
		this.userRoles = userRoles;
	}

	public List<Address> getAddress()
	{
		return address;
	}

	public void setAddress(List<Address> address)
	{
		this.address = address;
	}

	public void addAddress(Address address)
	{
		address.setUserId(getId());
		this.address.add(address);
	}

	public void removeAddress(Long id)
	{
		for (Address i : address)
		{
			if (i.getId() == id)
			{
				address.remove(i);
				break;
			}
		}
	}

	@Override
	public String toString()
	{
		return "User{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\''
				+ ", email='" + email + '\'' + ", password='" + password + '\'' + ", userRoles=" + userRoles
				+ ", address=" + address + '}';
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorites = new ArrayList<GrantedAuthority>();
		userRoles.forEach(userRole -> authorites.add(new SimpleGrantedAuthority(userRole.getRole().getName())));
		return authorites;
	}

	@Override
	public String getUsername()
	{
		return null;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}
}