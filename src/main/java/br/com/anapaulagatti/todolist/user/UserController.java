//Camada responsável pelas requisições HTTP e primeira camada de acesso do usuário a aplicação

package br.com.anapaulagatti.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController // é uma annotation usada para criar uma controller REST
@RequestMapping("/users") // annotation responsável por informar a rota 
public class UserController {

    @Autowired //Annotation utilizada para que o Spring faça p gerenciamento de todo o ciclo de vida
    private IUserRepository userRepository;

    @PostMapping("/") // annotation que informa que as solicitações HTTP são do tipo POST para o caminho ("/")
    public ResponseEntity create(@RequestBody UserModel userModel){
        var user = this.userRepository.findByUsername(userModel.getUsername());

        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
          }


        /*Aqui estamos usando a biblioteca bcrypt que realiza a criptografia da senha e parametrizando 
         * de acordo com a documentação, onde cost seria basicamente a "força" da senha e estamos convertendo a 
         * senha em um array para que a criptografia seja feita. */
        var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashred); //setando a senha criptografada "passwordHashred" na userModel.

        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);

    }


/**public UserModel create(@RequestBody UserModel userModel): Declaração do método create. 
 * Esse método espera um objeto UserModel como entrada (representado pela anotação @RequestBody). 
 * Geralmente utilizado para processar dados recebidos do corpo de uma solicitação HTTP (por exemplo, POST).
 * 
 * var userCreated = this.userRepository.save(userModel): Nesta linha, o código está salvando o objeto 
 * userModel no repositório userRepository. O método save é usado para criar um novo registro no banco de 
 * dados com base no userModel.
 * 
 * O return userCreated: retorna o objeto userCreated após a inserção bem-sucedida no banco de dados.
 * 
 * Em resumo, o método "create" cria um novo usuário, salva-o no banco de dados e depois 
 * retorna esse novo usuário como resposta. */
}

