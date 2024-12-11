package com.web2.projetoweb2.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web2.projetoweb2.dto.FuncionarioDTO;
import com.web2.projetoweb2.entity.TipoPerfil;
import com.web2.projetoweb2.entity.Usuario;
import com.web2.projetoweb2.repositorys.TipoPerfilRepository;
import com.web2.projetoweb2.repositorys.UsuarioRepository;

@Service
public class FuncionarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private TipoPerfilRepository tipoPerfilRepository;
    
    @Autowired
    private PasswordHashingService passwordHashingService;

    public List<Usuario> listarFuncionarios() {
        Optional<TipoPerfil> perfilFuncionario = tipoPerfilRepository.findByDescricao("Funcionario");
        if (perfilFuncionario.isPresent()) {
            return usuarioRepository.findByTipoPerfil(perfilFuncionario.get());
        }
        return List.of();
    }

    @Transactional
    public Usuario criarFuncionario(FuncionarioDTO funcionarioDTO) {
        // Verificar se já existe um funcionário com o mesmo email
        if (usuarioRepository.existsByEmail(funcionarioDTO.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario funcionario = new Usuario();
        funcionario.setEmail(funcionarioDTO.getEmail());
        funcionario.setNome(funcionarioDTO.getNome());
        funcionario.setAtivo(true);
        funcionario.setDataCriacao(LocalDateTime.now());
        funcionario.setDataNascimento(funcionarioDTO.getDataNascimento());

        // Gerar hash da senha
        try {
            String salt = passwordHashingService.generateSalt();
            String senhaHash = passwordHashingService.hashPassword(funcionarioDTO.getSenha(), salt);
            funcionario.setSenha(senhaHash);
            funcionario.setSalt(salt);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar hash da senha", e);
        }

        // Definir perfil como funcionário
        Optional<TipoPerfil> perfilFuncionario = tipoPerfilRepository.findByDescricao("Funcionario");
        if (perfilFuncionario.isEmpty()) {
            throw new RuntimeException("Perfil de funcionário não encontrado");
        }
        funcionario.setTipoPerfil(perfilFuncionario.get());

        return usuarioRepository.save(funcionario);
    }

    @Transactional
    public Usuario atualizarFuncionario(Long id, FuncionarioDTO funcionarioDTO) {
        Usuario funcionario = usuarioRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        // Verificar se o novo email já existe para outro funcionário
        if (!funcionario.getEmail().equals(funcionarioDTO.getEmail()) && 
            usuarioRepository.existsByEmail(funcionarioDTO.getEmail())) {
            throw new RuntimeException("Email já cadastrado para outro funcionário");
        }

        funcionario.setEmail(funcionarioDTO.getEmail());
        funcionario.setNome(funcionarioDTO.getNome());
        funcionario.setDataNascimento(funcionarioDTO.getDataNascimento());
        funcionario.setSenha(funcionario.getSenha());

        return usuarioRepository.save(funcionario);
    }

    @Transactional
    public void removerFuncionario(Long id, Long funcionarioLogadoId) {
        // Verificar se está tentando remover a si mesmo
        if (id.equals(funcionarioLogadoId)) {
            throw new RuntimeException("Não é permitido remover o próprio usuário");
        }

        // Contar número total de funcionários ativos
        long totalFuncionarios = listarFuncionarios().stream()
                .filter(f -> f.getAtivo())
                .count();

        if (totalFuncionarios <= 1) {
            throw new RuntimeException("Não é possível remover o único funcionário do sistema");
        }

        Usuario funcionario = usuarioRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        // Realizar exclusão lógica
        funcionario.setAtivo(false);
        usuarioRepository.save(funcionario);
    }

    public Optional<Usuario> buscarFuncionarioPorId(Long id) {
        return Optional.ofNullable(usuarioRepository.findById(id));
    }
}