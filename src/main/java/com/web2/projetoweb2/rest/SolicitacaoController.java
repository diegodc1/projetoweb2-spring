package com.web2.projetoweb2.rest;

import com.web2.projetoweb2.entity.EstadoSolicitacao;
import com.web2.projetoweb2.entity.Solicitacao;
import com.web2.projetoweb2.entity.SolicitacaoHistorico;
import com.web2.projetoweb2.services.EstadoSolicitacaoService;
import com.web2.projetoweb2.services.SolicitacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/solicitacoes")
public class SolicitacaoController {

    @Autowired
    private SolicitacoesService solicitacaoService;
    @Autowired
    private EstadoSolicitacaoService estadoSolicitacaoService;


    @GetMapping
    public ResponseEntity<List<Solicitacao>> getAllSolicitacoes() {
        List<Solicitacao> solicitacoes = solicitacaoService.getAllSolicitacoes();
        return new ResponseEntity<>(solicitacoes, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Solicitacao> getSolicitacaoById(@PathVariable Integer id) {
        Optional<Solicitacao> solicitacao = solicitacaoService.getSolicitacaoById(id);
        return solicitacao.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Autowired
    private SolicitacoesService solicitacoesService;

    @GetMapping("/{id}/historico")
    public ResponseEntity<?> getSolicitacaoHistorico(@PathVariable Integer id) {
        try {
            List<SolicitacaoHistorico> historico = solicitacoesService.getHistoricoBySolicitacao(id);
            return ResponseEntity.ok(historico);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> getSolicitacaoByEstadoAbertas(@PathVariable String estado) {
        Optional<EstadoSolicitacao> estadoSolicitacao = estadoSolicitacaoService.buscarPorDescricao(estado.toUpperCase());
        if (estadoSolicitacao.isPresent()) {
            List<Solicitacao> solicitacoes = solicitacaoService.getSolicitacaoByEstado(estadoSolicitacao.get());
            return new ResponseEntity<>(solicitacoes, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este tipo de estado não existe! Verique a escrita e tente novamente!");
        }
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Solicitacao>> getSolicitacoesByClienteId(@PathVariable Integer idCliente) {
        List<Solicitacao> solicitacoes = solicitacaoService.getSolicitacoesByClienteId(idCliente);

        if (solicitacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(solicitacoes);
        }
    }


    @PostMapping
    public ResponseEntity<Solicitacao> createSolicitacao(@RequestBody Solicitacao solicitacao) {
        Solicitacao createdSolicitacao = solicitacaoService.createSolicitacao(solicitacao);
        return new ResponseEntity<>(createdSolicitacao, HttpStatus.CREATED);
    }


//    @PutMapping("/update/{id}")
//    public ResponseEntity<Solicitacao> updateSolicitacao(@PathVariable Integer id, @RequestBody Solicitacao solicitacaoDetails) {
//        Optional<Solicitacao> updatedSolicitacao = solicitacaoService.updateSolicitacao(id, solicitacaoDetails);
//        return updatedSolicitacao.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSolicitacao(@PathVariable Integer id) {
        boolean deleted = solicitacaoService.deleteSolicitacao(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
