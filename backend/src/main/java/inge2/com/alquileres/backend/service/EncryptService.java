package inge2.com.alquileres.backend.service;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class EncryptService {
    public String encryptPassword(String password){
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }
    public boolean verifyPassword(String originalPassword, String hashedPassword) {
        return BCrypt.checkpw(originalPassword, hashedPassword);
    }
}
