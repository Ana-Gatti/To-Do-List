package br.com.anapaulagatti.todolist.task;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

/*Aqui estamos dizendo que "ITaskRepository" é uma interface que herda métodos da JpaRepository,
* informando a entidade e a chave primária*/
public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    List<TaskModel> findByIdUser(UUID idUser);
    
}
