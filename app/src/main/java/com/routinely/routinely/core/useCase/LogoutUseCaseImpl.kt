package com.routinely.routinely.core.useCase

import com.routinely.routinely.authentication.AuthenticationData
import com.routinely.routinely.core.Session

internal class LogoutUseCaseImpl(
    private val session: Session,
    private val authenticationData: AuthenticationData
): LogoutUseCase {
    override suspend fun invoke() {
        session.clearData()
        authenticationData.clearUserCredentials()
    }
}