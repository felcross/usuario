package com.projetozero.usuario.controller;

import com.projetozero.usuario.business.UsuarioService;
import com.projetozero.usuario.business.ViaCepService;
import com.projetozero.usuario.controller.dtos.EnderecoDTO;
import com.projetozero.usuario.controller.dtos.TelefoneDTO;
import com.projetozero.usuario.controller.dtos.UsuarioDTO;
import com.projetozero.usuario.infrastructure.clients.ViaCepDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ViaCepService viaCepService;

    @PostMapping
    public ResponseEntity<UsuarioDTO>  salvarUsuario(@RequestBody UsuarioDTO usuarioDTO){
       return ResponseEntity.ok(usuarioService.salvarUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.autenticarUsuario(usuarioDTO));
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorEmail(@RequestParam("email") String email) {
      return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleterUsuarioPoremail(@PathVariable String email) {
        usuarioService.deleterUsuarioPoremail(email);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<UsuarioDTO> updateusuario(
            @RequestBody UsuarioDTO usuarioDTO,
            @RequestHeader("Authorization")String token) {
       ;
        return ResponseEntity.ok(usuarioService.atualizarUsuario(token, usuarioDTO));

    }

    @PatchMapping("/endereco")
    public ResponseEntity<EnderecoDTO> updateEndereco(
            @RequestBody EnderecoDTO enderecoDTO,@RequestParam Long id)  {
        return ResponseEntity.ok(usuarioService.atualizarEndereco(id,enderecoDTO));

    }

    @PatchMapping("/telefone")
    public ResponseEntity<TelefoneDTO> updateTelefone(
            @RequestBody TelefoneDTO telefoneDTO,@RequestParam Long id)  {
        return ResponseEntity.ok(usuarioService.atualizarTelefone(id,telefoneDTO));

    }

    @PostMapping("/endereco")
    public ResponseEntity<EnderecoDTO>  salvarEndereco(
            @RequestBody EnderecoDTO enderecoDTO,
            @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastraEndereco(token, enderecoDTO));
    }

    @PostMapping("/telefone")
    public ResponseEntity<TelefoneDTO>  salvarEndereco(
            @RequestBody TelefoneDTO telefoneDTO,
            @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastraTelefone(token,telefoneDTO));
    }

    @GetMapping("/endereco/{cep}")
    public ResponseEntity<ViaCepDTO> buscarDadosCep(@PathVariable("cep") String cep){
        return ResponseEntity.ok(viaCepService.buscarDadosEndereco(cep));
    }


 }
