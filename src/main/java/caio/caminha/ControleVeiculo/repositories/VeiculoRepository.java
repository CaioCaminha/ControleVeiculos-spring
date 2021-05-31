package caio.caminha.ControleVeiculo.repositories;

import caio.caminha.ControleVeiculo.models.Veiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    Page<Veiculo> findByUsuarioEmail(String email, Pageable pageable);
}
