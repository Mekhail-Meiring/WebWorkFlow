package com.webworkflow.datasource

import com.webworkflow.datasource.dto.User
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class WebDatasourceTest {

    private val datasource = WebDatasource()

    @Test
    fun `should add a user to a map`() {
        // When
        datasource.addUser(User("John", "Doe", "01/01/2000"))

        // Then
        assertThat(datasource.userIncomeAndExpense.size).isEqualTo(1)
    }


    @Test
    fun `should be able get monthly income and expenses from excel file`() {
        // Given
        datasource.addUser(User("John", "Doe", "01/01/2000"))
        val file = File("src/test/resources/excel/test.xlsx")

        // When
        datasource.uploadExcelFile(file, "John")

        // Then
        assertThat(datasource.getMonthlyIncomeAndExpense("John").size).isEqualTo(12)
        assertThat(datasource.getMonthlyIncomeAndExpense("John").first().month).isEqualTo("Jan")
        assertThat(datasource.getMonthlyIncomeAndExpense("John").last().month).isEqualTo("Dec")
    }
}