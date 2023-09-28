package com.example.routinely.util

import com.example.routinely.R

sealed class Categories(val nameId: Int) {
    object Career:
            Categories(nameId = R.string.category_career_text)
    object Health:
        Categories(nameId = R.string.category_health_text)
    object Finances:
        Categories(nameId = R.string.category_finances_text)
    object Studies:
        Categories(nameId = R.string.category_studies_text)
}