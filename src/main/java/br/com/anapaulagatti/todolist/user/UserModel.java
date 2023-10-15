//Classe modelo de usuário

package br.com.anapaulagatti.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_users") //annotation que diz que objeto que vai representar uma tabela
public class UserModel {

    @Id
    @GeneratedValue (generator = "UUID")
    private UUID id;

    @Column(unique = true)//Define o atributo como unico
    private String username;
    private String name;
    private String password;

    @CreationTimestamp //Qdo os dados forem criados, o banco ja vai possuir essa informação de forma automática
    private LocalDateTime createdAt;
}