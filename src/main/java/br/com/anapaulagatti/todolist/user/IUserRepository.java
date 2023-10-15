package br.com.anapaulagatti.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


/**Define uma interface chamada IUserRepository que herda comportamentos da 
 * JpaRepository do Spring Data JPA. Já a parte de <UserModel, UUID> mostra qual a classe(entity) o repositório
 * está representando e qual o tipo de ID da entity.*/
public interface IUserRepository extends JpaRepository<UserModel, UUID>{
    UserModel findByUsername(String username);
    
}

