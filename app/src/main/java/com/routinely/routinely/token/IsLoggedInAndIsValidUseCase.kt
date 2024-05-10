package com.routinely.routinely.token

interface IsLoggedInAndIsValidUseCase {
    suspend operator fun invoke(): Boolean
}