package com.example.produtosapi.controller;

import com.example.produtosapi.dto.AuthRequest;
import com.example.produtosapi.dto.AuthResponse;
import com.example.produtosapi.model.Usuario;
import com.example.produtosapi.security.JwtTokenProvider;
import com.example.produtosapi.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UsuarioService usuarioService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider tokenProvider,
                          UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        Usuario usuario = (Usuario) usuarioService.loadUserByUsername(authRequest.getUsername());

        return ResponseEntity.ok(new AuthResponse(
                jwt,
                "Bearer",
                usuario.getUsername(),
                usuario.getNome()
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = usuarioService.cadastrarUsuario(usuario);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usuario.getUsername(),
                            usuario.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken(authentication);

            // Retorna a resposta com a mensagem de sucesso e o objeto AuthResponse
            return ResponseEntity.ok(new AuthResponse(
                    jwt,
                    "Bearer",
                    novoUsuario.getUsername(),
                    novoUsuario.getNome()
            ));
        } catch (Exception e) {
            // Em caso de erro, retorna um erro com a mensagem
            return ResponseEntity.badRequest().body("Erro ao criar usuário: " + e.getMessage());
        }
    }

}
