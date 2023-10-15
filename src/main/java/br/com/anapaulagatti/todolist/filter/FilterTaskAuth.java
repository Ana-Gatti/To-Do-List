package br.com.anapaulagatti.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.anapaulagatti.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component //É uma anotação genérica para indicar que uma classe é um componente gerenciado pelo Spring.
public class FilterTaskAuth extends OncePerRequestFilter {

  @Autowired
  private IUserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    var servletPath = request.getServletPath();

    //Verifica se estamos chamando nossa rota de tasks
    if (servletPath.startsWith("/tasks/")) {
          
        // Pegar a autenticação (usuario e senha)
        var authorization = request.getHeader("Authorization");

        /* na authorization contém uma string no formato "Basic [valor codificado]". 
          A função substring("Basic".length()) começa a extração após a string "Basic",  
          e remove todos os espaços em branco com a função .trim(), capturando então, 
          o [valor codificado] restante. */
        var authEncoded = authorization.substring("Basic".length()).trim();

        /* Converte a sequência codificada em Base64 (armazenada em authEncoded) de volta para sua 
          forma original de bytes e armazena essa representação em authDecode*/
        byte[] authDecode = Base64.getDecoder().decode(authEncoded);

        //Convertendo o Array de Bytes em uma nova String
        var authString = new String(authDecode);

        /*O método split(":") é usado para dividir essa string em um array de strings 
        com base no caractere ":"". Isso significa que credentials será um array com dois elementos, 
        onde credentials[0] conterá o nome de usuário (username) e credentials[1] conterá a senha (password).*/
        String[] credentials = authString.split(":");
        String username = credentials[0];
        String password = credentials[1];

        System.out.println("Authorization");
        System.out.println(username);
        System.out.println(password);
          
        //Se o usuário existir, eu vou validar a senha, se ele não existir, retornará o erro 401.
        var user = this.userRepository.findByUsername(username);
        if(user == null) {
            response.sendError(401, "Usuário sem autorização");
        } else {
            //Valida a  senha
            var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
            if (passwordVerify.verified)  {//.verified retorna um booleano
              //Segue o fluxo normal
              request.setAttribute("idUser", user.getId()); //Utilizando a própria autenticação para pegar o ID
              filterChain.doFilter(request, response);
            } else {
              response.sendError(401);
            }

          }
        } else {
          filterChain.doFilter(request, response);
        }

  }
}
    