package com.tsubakistudio.codeAmigos_Security.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @GetMapping("/confirm")
    public ResponseEntity<AuthenticationResponse> confirmEmail(@RequestParam("token") String token){
        return ResponseEntity.ok(authenticationService.confirmToken(token));
    }

    @PostMapping("/resend-confirmation")
    public String resendConfirmation(@RequestParam("email") String email){
        authenticationService.resendConfirmationEmail(email);
        return "Confirmation email resent!!! please check you email ❤";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authenticationService.refreshToken(request.getRefreshToken()));
    }
}
