package br.edu.ifsp.prw3_veiculos.Controller;

import br.edu.ifsp.prw3_veiculos.model.Veiculo;
import br.edu.ifsp.prw3_veiculos.repository.VeiculoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {
    private final VeiculoRepository veiculoRepository;

    public VeiculoController(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    @GetMapping
    public List<Veiculo> listar() {
        return veiculoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> buscarPorId(@PathVariable Long id) {
        return veiculoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Veiculo> cadastrar(@RequestBody Veiculo veiculo) {
        veiculo.setId(null);
        Veiculo salvo = veiculoRepository.save(veiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Veiculo> atualizar(@PathVariable Long id, @RequestBody Veiculo veiculo) {
        return veiculoRepository.findById(id)
                .map(veiculoExistente -> {
                    veiculo.setId(veiculoExistente.getId());
                    return ResponseEntity.ok(veiculoRepository.save(veiculo));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!veiculoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        veiculoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
