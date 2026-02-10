package com.sunny.ems.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	// ✅ CREATE (delete old first)
	@Transactional
	public RefreshToken createRefreshToken(User user) {

		// 🔥 VERY IMPORTANT — remove old token first
		repo.deleteByUserId(user.getId());

		RefreshToken token = new RefreshToken();
		token.setUser(user);
		token.setToken(UUID.randomUUID().toString());
		token.setExpiryDate(Instant.now().plusSeconds(REFRESH_EXPIRY));

		return repo.save(token);
	}

	// 🔁 VERIFY + ROTATE
	@Transactional
	public RefreshToken verifyAndRotate(String oldToken) {

		RefreshToken token = repo.findByToken(oldToken)
				.orElseThrow(() -> new RuntimeException("Invalid refresh token"));

		if (token.getExpiryDate().isBefore(Instant.now())) {
			repo.delete(token);
			throw new RuntimeException("Refresh token expired");
		}

		User user = token.getUser();

		// old token removed automatically by createRefreshToken
		return createRefreshToken(user);
	}

	// 🚪 LOGOUT
	public void deleteByUserId(Long userId) {
		repo.deleteByUserId(userId);
	}
}
