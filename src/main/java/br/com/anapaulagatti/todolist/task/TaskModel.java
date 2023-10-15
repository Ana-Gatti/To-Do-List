package br.com.anapaulagatti.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID") //informa que o ID seja gerado de forma automática
    private UUID id;
    private String description;
  
    @Column(length = 50)//Restringindo o tamanho do title em 50 caracteres
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;
  
    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setTitle(String title) throws Exception {
        if (title.length() > 50) {
          throw new Exception("O campo title deve conter no máximo 50 caracteres");
        }
    
        this.title = title;
      }

}

/*A anotação @Column é usada para personalizar a forma como os atributos de uma classe são mapeados 
para colunas no banco de dados.*/