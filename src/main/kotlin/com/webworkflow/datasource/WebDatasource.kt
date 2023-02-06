package com.webworkflow.datasource

import com.webworkflow.datasource.dto.MonthlyIncomeAndExpense
import com.webworkflow.datasource.dto.User
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Repository
import java.io.File
import java.io.FileInputStream


/**
 * @see Datasource
 * @author Mekhail Meiring
 * @since 03/02/2023
 *
 * WebDatasource is a concrete implementation of the Datasource interface.
 *
 * It is responsible for storing user's monthly income and expenses data in memory and
 * providing methods to interact with the stored data.
 *
 * This class is annotated with `@Repository` which indicates that it is a repository bean
 * for managing user's data.
 *
 * @property userIncomeAndExpense a mutable map of the user's first name to a list of their
 * monthly income and expenses
 */
@Repository("webDatasource")
class WebDatasource : Datasource{

    /**
     * Mutable map to store user's first name as key and their respective
     * monthly income and expense as values in form of a list
     */
    val userIncomeAndExpense = mutableMapOf<String, List<MonthlyIncomeAndExpense>>()


    /**
     * Adds a user to the database by storing their first name as key in the map
     * and initializing an empty list for their monthly income and expense
     *
     * @param user The user to be added to the database
     * @return the added user
     * @throws IllegalArgumentException if the user details are invalid
     */
    override fun addUser(user: User) : User {

        if (checkIfUserDetailsAreIncorrect(user)) {
            throw IllegalArgumentException("User details are invalid")
        }
        userIncomeAndExpense.clear()
        userIncomeAndExpense[user.firstName] = emptyList()
        return user
    }


    /**
     * Uploads an Excel file for a user and updates the database
     *
     * @param file The Excel file to be uploaded
     * @param userFirstName The first name of the user the file belongs to
     * @throws NoSuchElementException if the user does not exist in the database
     */
    override fun uploadExcelFile(file: File, userFirstName: String) {

        if (!userIncomeAndExpense.containsKey(userFirstName)) {
            throw NoSuchElementException("User does not exist")
        }

        val workbook = XSSFWorkbook(FileInputStream(file))
        val sheet = workbook.getSheetAt(0)
        val userData = userIncomeAndExpense.getValue(userFirstName).toMutableList()
        userData.clear()

        // Loops through each row in the sheet and maps it to a MonthlyIncomeAndExpense object
        for (i in 1 until sheet.physicalNumberOfRows) {
            val row = sheet.getRow(i)
            userData.add(mapRowToMonthlyIncomeAndExpense(row))
        }

        userIncomeAndExpense[userFirstName] = userData
    }


    /**
     * Retrieves the monthly income and expenses of a user
     *
     * @param userFirstName The first name of the user
     * @return The monthly income and expenses of the user as a list of MonthlyIncomeAndExpense objects
     */
    override fun getMonthlyIncomeAndExpense(userFirstName: String) : List<MonthlyIncomeAndExpense> {
        return userIncomeAndExpense.getValue(userFirstName)
    }


    /**
     * Maps a row in the Excel sheet to a MonthlyIncomeAndExpense object
     *
     * @param row The row in the Excel sheet
     * @return The MonthlyIncomeAndExpense object
     */
    private fun mapRowToMonthlyIncomeAndExpense(row: Row) : MonthlyIncomeAndExpense{

        val month = row.getCell(0).stringCellValue
        val income = row.getCell(1).stringCellValue
        val expense = row.getCell(2).stringCellValue

        return MonthlyIncomeAndExpense(month, income, expense)
    }


    /**
     * Validates the details of a user.
     *
     * @param user the user whose details are being validated
     * @return `true` if any of the following conditions are met:
     *   - The first name is blank
     *   - The first name contains numbers
     *   - The last name is blank
     *   - The last name contains numbers
     *   - The date of birth is blank
     * `false` otherwise
     */
    private fun checkIfUserDetailsAreIncorrect(user: User): Boolean {
        return user.firstName.isBlank() || Regex(".*[0-9].*").matches(user.firstName)
            || user.lastName.isBlank() || Regex(".*[0-9].*").matches(user.lastName)
            || user.dateOfBirth.isBlank()
    }

}