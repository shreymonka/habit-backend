package com.dalhousie.habit.config

import com.dalhousie.habit.service.JwtService
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val userDetailsService: UserDetailsService,
    private val jwtService: JwtService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwtToken = getJwtToken(request, response, filterChain) ?: return
        val userEmail = getUserEmail(jwtToken, response) ?: return

        SecurityContextHolder.getContext().authentication.let {
            if (it != null) return@let
            val userDetails = userDetailsService.loadUserByUsername(userEmail)
            if (!jwtService.isTokenValid(jwtToken, userDetails))
                return@let
            val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities).apply {
                details = WebAuthenticationDetailsSource().buildDetails(request)
            }
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

    private fun getJwtToken(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ): String? = request.getHeader("Authorization").let {
        if (it == null || !it.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return null
        }
        return it.substring(ACCESS_TOKEN_START_INDEX)
    }

    private fun getUserEmail(jwtToken: String, response: HttpServletResponse): String? {
        // extract userEmail from jwtToken also catch error if the token is INVALID or EXPIRED
        return try {
            jwtService.extractUsername(jwtToken)
        } catch (exception: Exception) {
            when (exception) {
                is ExpiredJwtException, is MalformedJwtException -> {
                    response.status = HttpStatus.UNAUTHORIZED.value()
                    response.writer.write("User is Unauthorized. -> " + exception.message)
                    null
                }
                else -> null
            }
        }
    }

    companion object {
        private const val ACCESS_TOKEN_START_INDEX = 7
    }
}
