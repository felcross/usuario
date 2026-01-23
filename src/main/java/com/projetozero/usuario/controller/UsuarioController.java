package com.projetozero.usuario.controller;

import com.projetozero.usuario.business.UsuarioService;
import com.projetozero.usuario.controller.dtos.UsuarioDTO;
import com.projetozero.usuario.infrastructure.entity.Usuario;
import com.projetozero.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager  authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<Usuario>  salvarUsuario(@RequestBody Usuario usuario){
       return ResponseEntity.ok(usuarioService.salvarUsuario(usuario));
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(),
                        usuarioDTO.getSenha()));

        return jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<Usuario> buscarUsuarioPorEmail(@RequestParam("email") String email) {
      return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleterUsuarioPoremail(@PathVariable String email) {
        usuarioService.deleterUsuarioPoremail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> updateusuario(
            @RequestBody UsuarioDTO usuarioDTO,
            @RequestHeader("Authorization")String token) {
       ;
        return ResponseEntity.ok(usuarioService.atualizarUsuario(token, usuarioDTO));

    }
 }
