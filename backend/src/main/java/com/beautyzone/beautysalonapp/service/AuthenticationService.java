package com.beautyzone.beautysalonapp.service;

import com.beautyzone.beautysalonapp.rest.dto.AuthenticationRequest;
import com.beautyzone.beautysalonapp.rest.dto.AuthenticationResponse;
import com.beautyzone.beautysalonapp.rest.dto.RegisterRequest;

public interface AuthenticationService {

    /**
     * Registers a new user based on the provided registration request.
     *
     * @param request The registration request containing user details.
     * @return An authentication response with the generated JWT token and user ID.
     */
    AuthenticationResponse register(RegisterRequest request);

    /**
     * Authenticates a user based on the provided authentication request.
     *
     * @param request The authentication request containing user credentials.
     * @return An authentication response with the generated JWT token and user ID.
     */
    AuthenticationResponse authenticate(AuthenticationRequest request);

}