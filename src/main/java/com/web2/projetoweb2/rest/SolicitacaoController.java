package com.web2.projetoweb2.rest;

import com.web2.projetoweb2.entity.Solicitacao;
import com.web2.projetoweb2.services.SolicitacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/solicitacoes")
public class SolicitacaoController {

    @Autowired
    private SolicitacoesService solicitacaoService;


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
