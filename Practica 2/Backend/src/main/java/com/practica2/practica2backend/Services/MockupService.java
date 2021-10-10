package com.practica2.practica2backend.Services;

import com.practica2.practica2backend.Models.Mockup;
import com.practica2.practica2backend.Models.User;
import com.practica2.practica2backend.Repositories.MockupRepository;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;


import java.security.SignatureException;
import java.time.LocalDate;
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

    public Mockup save(Mockup mockup){
        return mockupRepository.save(mockup);
    }

    public Iterable<Mockup> findAll(){
        return mockupRepository.findAll();
    }

    public Mockup findByUUID(String uuid){
        return mockupRepository.findByUuid(uuid);
    }

    public void delete(Mockup mockup){
        mockupRepository.delete(mockup);
    }

    public Mockup generateUUID(Mockup mockup){
        mockup.setUuid(UUID.randomUUID().toString());
        if(mockup.getName().isEmpty()){
            mockup.setName(mockup.getUuid());
        }
        return mockup;
    }

    public String generateJwtToken(Mockup mockup, String username) {
        Long expiryTimeMillseconds = Long.parseLong("31557600000");
        //Long expiryTimeMillseconds = Long.parseLong("1000");

        if(mockup.getExpiryTime().equalsIgnoreCase("Hora")){
            expiryTimeMillseconds = Long.parseLong("3600000");
        }
        if(mockup.getExpiryTime().equalsIgnoreCase("Dia")){
            expiryTimeMillseconds = Long.parseLong("86400000");
        }
        if(mockup.getExpiryTime().equalsIgnoreCase("Semana")){
            expiryTimeMillseconds = Long.parseLong("604800000");
        }
        if(mockup.getExpiryTime().equalsIgnoreCase("Mes")){
            expiryTimeMillseconds = Long.parseLong("2629750000");
        }


        return Jwts.builder()
                .setSubject(mockup.getUuid())
                .claim("owner",username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expiryTimeMillseconds))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public boolean validateJwtToken(String authToken) {

        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: {}"+ e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: {}"+e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: {}"+e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: {}"+e.getMessage());
        }

        return false;
    }

    public String validateMessageJwtToken(String authToken) {

        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authToken);

        } catch (MalformedJwtException e) {
            return ("Token invalido: {}"+ e.getMessage());

        } catch (ExpiredJwtException e) {
            return ("JWT token ha expirado: {}"+e.getMessage());
        } catch (UnsupportedJwtException e) {
            return ("JWT token no soportado: {}"+e.getMessage());
        } catch (IllegalArgumentException e) {
            return ("JWT la cadena está vacía: {}"+e.getMessage());
        }

        return ("no error");
    }



}
