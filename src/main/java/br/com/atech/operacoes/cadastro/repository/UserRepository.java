package br.com.atech.operacoes.cadastro.repository;

import org.springframework.stereotype.Repository;

import br.com.atech.operacoes.cadastro.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
    public User findByEmail(String email);
    
    public User findByUsername(String username);
}
