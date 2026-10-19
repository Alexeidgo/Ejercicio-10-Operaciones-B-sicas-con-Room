package com.mexiti.costogasolina

import androidx.compose.material3.Surface
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import com.mexiti.costogasolina.ui.views.CostGasLayout
import org.junit.Rule
import org.junit.Test
import java.text.NumberFormat

class NuevoMontoUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calcularMonto_10l_24(){
        composeTestRule.setContent {
            Surface {
                CostGasLayout(
                    navController = rememberNavController(),
                    onSaveTransaction = { _, _, _, _, _ -> }
                )
            }
        }
        composeTestRule.onNodeWithText("Ingresa precio por litro de Gasolina")
            .performTextInput("24")
        composeTestRule.onNodeWithText("Litros")
            .performTextInput("10")

        val montoEsperado = NumberFormat.getCurrencyInstance().format(440.0)

        composeTestRule.onNodeWithText("Monto Total: $montoEsperado").
        assertExists("No se encontro ningún nodo con este texto")

    }
}