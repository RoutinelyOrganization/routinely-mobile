package com.routinely.routinely.token

import com.routinely.routinely.authentication.AuthenticationData
import com.routinely.routinely.core.Session
import timber.log.Timber

internal class IsLoggedInAndIsValidUseCaseImpl(
    private val authenticationData: AuthenticationData,
    private val session: Session
) : IsLoggedInAndIsValidUseCase {
    override suspend fun invoke(): Boolean {
        try {
            if(!authenticationData.isUserAlreadyLoggedIn()) {
                return false
            }

            val tokens = authenticationData.loginUserAndReturnToken() ?: return false

            session.saveTokens(tokens)
            return true
        } catch (e: Exception) {
            Timber.w(e)
            return false
        }

    }
}
