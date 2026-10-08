package br.edu.ifsp.prw3_veiculos.Controller;

import br.edu.ifsp.prw3_veiculos.dto.DadosListagemConserto;
import br.edu.ifsp.prw3_veiculos.dto.DadosListagemFull;
import br.edu.ifsp.prw3_veiculos.dto.DadosAtualizacaoConserto;
import br.edu.ifsp.prw3_veiculos.model.Conserto;
import br.edu.ifsp.prw3_veiculos.repository.ConsertoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/consertos")
public class ConsertoController {
    private final ConsertoRepository consertoRepository;

    public ConsertoController(ConsertoRepository consertoRepository) {
        this.consertoRepository = consertoRepository;
    }

    @GetMapping
    public ResponseEntity<List<DadosListagemConserto>> listar() {
        List<DadosListagemConserto> consertos = consertoRepository.findAllByAtivoTrue().stream()
                .map(DadosListagemConserto::new)
                .toList();
        return ResponseEntity.ok(consertos);
    }

    @GetMapping("/full")
    public ResponseEntity<Page<DadosListagemFull>> listarCompleto(Pageable pageable) {
        Page<DadosListagemFull> consertos = consertoRepository.findAll(pageable)
                .map(DadosListagemFull::new);
        return ResponseEntity.ok(consertos);
    }

    @PostMapping
    public ResponseEntity<Conserto> cadastrar(@Valid @RequestBody Conserto conserto) {
        conserto.setId(null);
        conserto.setAtivo(true);
        Conserto salvo = consertoRepository.save(conserto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosListagemConserto> getConsertoById(@PathVariable Long id) {
        Optional<Conserto> consertoOptional = consertoRepository.findById(id);

        if (consertoOptional.isPresent()) {
            Conserto conserto = consertoOptional.get();
            return ResponseEntity.ok(new DadosListagemConserto(conserto));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DadosListagemConserto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody DadosAtualizacaoConserto dados
    ) {
        Optional<Conserto> consertoOptional = consertoRepository.findById(id);
        if (consertoOptional.isEmpty() || !consertoOptional.get().isAtivo()) {
            return ResponseEntity.notFound().build();
        }

        Conserto conserto = consertoOptional.get();
        conserto.setDataSaida(dados.dataSaida());
        conserto.getMecanicoResponsavel().setNome(dados.nomeMecanico());
        conserto.getMecanicoResponsavel().setAnosDeExperiencia(dados.anosDeExperiencia());

        Conserto atualizado = consertoRepository.save(conserto);
        return ResponseEntity.ok(new DadosListagemConserto(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirLogicamente(@PathVariable Long id) {
        Optional<Conserto> consertoOptional = consertoRepository.findById(id);
        if (consertoOptional.isEmpty() || !consertoOptional.get().isAtivo()) {
            return ResponseEntity.notFound().build();
        }

        Conserto conserto = consertoOptional.get();
        conserto.setAtivo(false);
        consertoRepository.save(conserto);
        return ResponseEntity.noContent().build();
    }
}
