package com.devsuperior.bds04.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "tb_user")  //Anotação que define o nome da tabela no banco de dados
public class User implements UserDetails, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long   id;
	
	@Column(unique = true)
	private String email;
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER)  //Exigência do Spring Security, quando for fazer autenticação de usuário.
	@JoinTable(name = "tb_user_role",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>(); 
	
	public User() {		
	}

	public User(Long id, String email, String password) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	// Os metodos abaixo são de implementação obrigatória por causa da interface UserDetails do Spring Security.

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//Este metodo percorre a lista de roles através de um metodo lambda para converter cada elemento role da lista para um GrantedAuthority.
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		return email;
	}

	// Metodo para verificar se a conta do usuário não está EXPIRADA. 
	// Por padrão colocamos true para nunca estar expirada, caso contrário deverá ser implementada uma lógica para verificar se a conta está expirada.
	@Override
	public boolean isAccountNonExpired() {
		return true; 
	}

	// Metodo para verificar se a conta do usuário não está BLOQUEADA. 
	// Por padrão colocamos true para nunca estar bloqueada, caso contrário deverá ser implementada uma lógica para verificar se a conta está bloqueada.
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// Metodo para verificar se as credenciais do usuário não estão EXPIRADAS. 
	// Por padrão colocamos true para nunca estarem expiradas, caso contrário deverá ser implementada uma lógica para verificar se as credenciais estão expiradas.
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// Metodo para verificar se a conta do usuário está HABILITADA. 
	// Por padrão colocamos true para deixar semprer habilitada, caso contrário deverá ser implementada uma lógica para verificar se a conta está desabilitada.
	@Override
	public boolean isEnabled() {
		return true;
	}

}
