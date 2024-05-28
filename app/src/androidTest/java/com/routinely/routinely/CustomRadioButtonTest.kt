package com.routinely.routinely

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.RadioButtonColors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.routinely.routinely.ui.components.CustomRadioButton
import org.junit.Rule
import org.junit.Test

class CustomRadioButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCustomRadioButtonSelection() {
        var selected by mutableStateOf(false)

        composeTestRule.setContent {
            Column {
                CustomRadioButton(
                    selected = selected,
                    onSelected = { selected = it },
                    selectedIcon = painterResource(android.R.drawable.checkbox_on_background),
                    unselectedIcon = painterResource(android.R.drawable.checkbox_off_background),
                    colors = RadioButtonColors(
                        selectedColor = Color.Green,
                        unselectedColor = Color.Red,
                        disabledUnselectedColor = Color.Black,
                        disabledSelectedColor = Color.Blue
                    )
                )
            }
        }

        // Verifica se o botão está inicialmente desmarcado
        composeTestRule.onNodeWithContentDescription("Unchecked").assertExists()

        // Clica no botão
        composeTestRule.onNodeWithContentDescription("Unchecked").performClick()

        // Verifica se o botão está marcado após o clique
        composeTestRule.onNodeWithContentDescription("Checked").assertExists()
    }
}