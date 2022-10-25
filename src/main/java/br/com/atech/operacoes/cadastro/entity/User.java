package br.com.atech.operacoes.cadastro.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder	
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tb_user SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Entity
@Table(name = "tb_user")
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@NotNull
	@Column(name="name")
	private String name;
	
	@NotNull
	@Column(name="username", unique = true)
	private String username;
	
	@NotNull
	@Email
	@Column(name="email", unique = true)
	private String email;
	
	@NotNull
	@Size(min = 6, max = 12)
 	@Column(name="password")
	private String password;
	
	@Column(name="deleted")
	private boolean deleted;
	

}
