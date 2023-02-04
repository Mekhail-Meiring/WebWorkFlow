package com.webworkflow.datasource

import com.webworkflow.datasource.dto.MonthlyIncomeAndExpense
import com.webworkflow.datasource.dto.User
import java.io.File


/**
 * Interface to define the methods for a data source
 *
 * @author Mekhail Meiring
 * @since 03/02/2023
 */
interface Datasource {

    /**
     * Add a new user to the data source
     *
     * @param user User details
     * @return User instance with added details
     */
    fun addUser(user: User) : User

    /**
     * Upload an Excel file for a user
     *
     * @param file Excel file to be uploaded
     * @param userFirstName First name of the user who is uploading the file
     */
    fun uploadExcelFile(file: File, userFirstName: String)

    /**
     * Get the monthly income and expense of a user
     *
     * @param userFirstName First name of the user for which the data is requested
     * @return List of monthly income and expenses for the user
     */
    fun getMonthlyIncomeAndExpense(userFirstName: String) : List<MonthlyIncomeAndExpense>
}