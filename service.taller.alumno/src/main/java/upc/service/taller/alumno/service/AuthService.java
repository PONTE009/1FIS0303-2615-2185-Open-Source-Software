package upc.service.taller.alumno.service;

import upc.service.taller.alumno.dto.auth.AuthResponseDto;
import upc.service.taller.alumno.dto.auth.LoginRequestDto;
import upc.service.taller.alumno.dto.auth.RefreshTokenRequestDto;
import upc.service.taller.alumno.dto.auth.RefreshTokenResponseDto;
import upc.service.taller.alumno.dto.auth.RegistroUsuarioRequestDto;
import upc.service.taller.alumno.dto.auth.RegistroUsuarioResponseDto;

public interface AuthService {
    RegistroUsuarioResponseDto registrar(RegistroUsuarioRequestDto request);

    AuthResponseDto login(LoginRequestDto request);

    RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto request);
}
