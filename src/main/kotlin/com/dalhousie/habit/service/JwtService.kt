package com.dalhousie.habit.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function
import javax.crypto.SecretKey

interface JwtService {

    fun extractUsername(jwtToken: String): String

    fun generateToken(userDetails: UserDetails): String

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean
}

@Service
class JwtServiceImpl : JwtService {

    override fun extractUsername(jwtToken: String): String = extractClaim<String>(jwtToken, Claims::getSubject)

    override fun generateToken(userDetails: UserDetails): String = buildToken(HashMap<String, Any>(), userDetails)

    override fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val isTokenExpired: Boolean = isTokenExpired(token)
        val username = extractUsername(token)
        return (username == userDetails.username) && !isTokenExpired
    }

    private fun buildToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String {
        return Jwts.builder()
            .also { it.claims().add(extraClaims) }
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            // For now setting token expiration to 10 days
            // [TODO]: Setup refresh token concept for updating expired tokens
            .expiration(
                Date(
                System.currentTimeMillis() +
                        1000L /*Milliseconds*/ *
                        60 /*Seconds*/ *
                        60 /*Minutes*/ *
                        24 /*Hours*/ *
                        10 /*Days*/
            )
            )
            .signWith(getSignInKey())
            .compact()
    }

    private fun isTokenExpired(token: String): Boolean {
        return try {
            extractClaim(token, Claims::getExpiration).before(Date())
        } catch (exception: Exception) {
            false
        }
    }

    private fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T {
        val claims: Claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts
            .parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    private fun getSignInKey(): SecretKey {
        val keyBytes: ByteArray = Decoders.BASE64.decode(SECRET_KEY)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    companion object {
        private const val SECRET_KEY: String = "d419ce37778265923e615c226b69adbf34c3707f20fa94703bd16dfbd5f52fca"
    }
}
