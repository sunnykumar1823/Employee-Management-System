package com.sunny.ems.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sunny.ems.entity.RefreshToken;
import com.sunny.ems.entity.User;
import com.sunny.ems.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {

	private final RefreshTokenRepository repo;

	private static final long REFRESH_EXPIRY = 7 * 24 * 60 * 60; // 7 days

	public RefreshTokenService(RefreshTokenRepository repo) {
		this.repo = repo;
	}

	// ✅ CREATE TOKEN ON LOGIN
	public RefreshToken createRefreshToken(User user) {

		repo.deleteByUserId(user.getId()); // old token delete

		RefreshToken token = new RefreshToken();
		token.setUser(user);
		token.setToken(UUID.randomUUID().toString());
		token.setExpiryDate(Instant.now().plusSeconds(REFRESH_EXPIRY));

		return repo.save(token);
	}

	// ✅ VERIFY TOKEN (USED IN /auth/refresh)
	public RefreshToken verifyToken(String token) {

		RefreshToken refreshToken = repo.findByToken(token)
				.orElseThrow(() -> new RuntimeException("Invalid refresh token"));

		if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
			repo.delete(refreshToken);
			throw new RuntimeException("Refresh token expired");
		}

		return refreshToken;
	}

	// ✅ LOGOUT
	public void deleteByUserId(Long userId) {
		repo.deleteByUserId(userId);
	}
}
