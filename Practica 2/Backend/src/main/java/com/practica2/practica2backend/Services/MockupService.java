package com.practica2.practica2backend.Services;

import com.practica2.practica2backend.Models.Mockup;
import com.practica2.practica2backend.Repositories.MockupRepository;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import org.springframework.cache.annotation.Cacheable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
public class MockupService {
    private final MockupRepository mockupRepository;
    private String SECRET = "22C34A3316285C5C80B2F34666F79A8FD61534C938AA916DDAE3CD48BE8023E2";

    public MockupService(MockupRepository mockupRepository) {
        this.mockupRepository = mockupRepository;
    }

    public Mockup save(Mockup mockup) {
        return mockupRepository.save(mockup);
    }

    @Cacheable("mockups")
    public Iterable<Mockup> findAll() {
        return mockupRepository.findAll();
    }

    public Mockup findByUUID(String uuid) {
        return mockupRepository.findByUuid(uuid);
    }

    public void delete(Mockup mockup) {
        mockupRepository.delete(mockup);
    }

    public Mockup generateUUID(Mockup mockup) {
        mockup.setUuid(UUID.randomUUID().toString());
        if (mockup.getName().isEmpty()) {
            mockup.setName(mockup.getUuid());
        }
        return mockup;
    }

    public String generateJwtToken(Mockup mockup, String username) {
        Long expiryTimeMillseconds = Long.parseLong("31557600000");
        //Long expiryTimeMillseconds = Long.parseLong("1000");

        if (mockup.getExpiryType().equalsIgnoreCase("Hora")) {
            expiryTimeMillseconds = Long.parseLong("3600000");
        }
        if (mockup.getExpiryType().equalsIgnoreCase("Dia")) {
            expiryTimeMillseconds = Long.parseLong("86400000");
        }
        if (mockup.getExpiryType().equalsIgnoreCase("Semana")) {
            expiryTimeMillseconds = Long.parseLong("604800000");
        }
        if (mockup.getExpiryType().equalsIgnoreCase("Mes")) {
            expiryTimeMillseconds = Long.parseLong("2629750000");
        }


        return Jwts.builder()
                .setSubject(mockup.getUuid())
                .claim("owner", username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expiryTimeMillseconds))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public Boolean isValidMockup(Mockup mockup, String token) {
        LocalDateTime time = LocalDateTime.now();
        if (!mockup.getAllowJWT()) {
            if (time.isAfter(mockup.getExpiryTime())) {
                return false;
            }

            return !time.isAfter(mockup.getExpiryTime());
        }
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(mockup.getToken());
            if(mockup.getToken().equals(token)){
                return false;
            }
            return true;
        } catch (MalformedJwtException | NullPointerException e) {
            System.out.println("Invalid JWT token: {}" + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: {}" + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: {}" + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: {}" + e.getMessage());
        }

        return false;
    }


}
