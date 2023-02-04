package com.webworkflow.service

import com.webworkflow.datasource.Datasource
import com.webworkflow.datasource.dto.MonthlyIncomeAndExpense
import com.webworkflow.datasource.dto.User
import org.springframework.stereotype.Service
import java.io.File


/**
 * @author Mekhail Meiring
 * @since 03/02/2023
 *
 * The WebService class provides an interface to interact with the underlying data source
 * to add users, upload Excel files, and retrieve monthly income and expenses.
 *
 * This class is annotated with @Service to indicate it is a service component in Spring Boot.
 * @param datasource the data source to be used
 */
@Service
class WebService(val datasource: Datasource) {

    /**
     * Adds a user to the data source.
     *
     * @param user the user to be added
     * @return the added user
     */
    fun addUser(user: User): User = datasource.addUser(user)

    /**
     * Uploads an excel file for a user.
     *
     * @param file the Excel file to be uploaded
     * @param user_name the first name of the user
     */
    fun uploadExcelFile(file: File, user_name: String) = datasource.uploadExcelFile(file, user_name)

    /**
     * Retrieves the monthly income and expenses for a user.
     *
     * @param username the first name of the user
     * @return a list of monthly income and expenses
     */
    fun getMonthlyIncomeAndExpense(username: String): List<MonthlyIncomeAndExpense> {
        return datasource.getMonthlyIncomeAndExpense(username)
    }

}
