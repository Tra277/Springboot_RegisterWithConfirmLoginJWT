package com.tsubakistudio.codeAmigos_Security.confirmationToken;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {
    private final ConfimationTokenRepository confimationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken){
        confimationTokenRepository.save(confirmationToken);
        confimationTokenRepository.save(confirmationToken);
    }

    public Optional<ConfirmationToken> getToken(String token){
        return confimationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token){
        return confimationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
