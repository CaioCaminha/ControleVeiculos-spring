package caio.caminha.ControleVeiculo.repositories;

import caio.caminha.ControleVeiculo.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByCpf(String email);
}
