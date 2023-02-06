package com.webworkflow.controller

import com.webworkflow.datasource.dto.MonthlyIncomeAndExpense
import com.webworkflow.datasource.dto.User
import com.webworkflow.service.WebService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.lang.IllegalStateException

/**
 * WebController is a RESTful web service that provides APIs for adding a user, uploading a file,
 * and retrieving the monthly income and expense of a user.
 *
 * @author Mekhail Meiring
 * @since 03/02/2023
 *
 * @RestController marks this class as a RESTful web service endpoint.
 *
 * @RequestMapping("/") maps this class to the root URL.
 *
 * @ExceptionHandler maps exceptions to HTTP status codes and returns them as a response.
 *
 * @PostMapping maps an HTTP POST request to a method that handles the request.
 *
 * @ResponseStatus(HttpStatus.CREATED) sets the HTTP status code for the response to CREATED (201).
 *
 * @GetMapping maps an HTTP GET request to a method that handles the request.
 *
 * @PathVariable specifies the value of a path variable in the URL.
 *
 * @RequestBody specifies that the request body will contain JSON data.
 *
 * @RequestParam specifies a request parameter.
 */
@RestController
@RequestMapping("/api")
class WebController(private val service: WebService){

    /**
     * Handles NoSuchElementException exceptions and returns a NOT_FOUND (404) HTTP status code
     * and the error message as the response.
     *
     * @param e The exception to be handled.
     *
     * @return A ResponseEntity object containing the error message and the NOT_FOUND HTTP status code.
     */
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    /**
     * Handles IllegalArgumentException exceptions and returns a BAD_REQUEST (400) HTTP status code
     * and the error message as the response.
     *
     * @param e The exception to be handled.
     *
     * @return A ResponseEntity object containing the error message and the BAD_REQUEST HTTP status code.
     */
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalState(e: IllegalStateException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.FORBIDDEN)


    /**
     * Adds a user by calling the addUser method of the WebService class.
     *
     * @param user The user to be added.
     *
     * @return The added user.
     */
    @PostMapping("addUser")
    @ResponseStatus(HttpStatus.CREATED)
    fun addUser(@RequestBody user: User): User = service.addUser(user)


    /**
     * Uploads an Excel file by calling the uploadExcelFile method of the WebService class.
     *
     * @param file The file to be uploaded.
     *
     * @param username The username associated with the file.
     */
    @PostMapping("upload")
    @ResponseStatus(HttpStatus.CREATED)
    fun upload(@RequestParam("file") file: MultipartFile, @RequestParam("username") username: String) : String {
        val tempFile = transformFile(file)
        service.uploadExcelFile(tempFile, username)
        return "File uploaded successfully"
    }


    /**
     * Retrieve the list of [MonthlyIncomeAndExpense] for the given username.
     *
     * @param username the username to retrieve data for.
     * @return the list of [MonthlyIncomeAndExpense] for the given username.
     */
    @GetMapping("monthlyIncomeAndExpense/{username}")
    fun getMonthlyIncomeAndExpense(@PathVariable username: String) : List<MonthlyIncomeAndExpense> {
        return service.getMonthlyIncomeAndExpense(username)
    }


    /**
     * Transforms the given [MultipartFile] into a [File].
     *
     * @param file the file to transform.
     * @return a [File] representation of the given [MultipartFile].
     */
    private fun transformFile(file: MultipartFile): File {
        val tempFile = File.createTempFile("temp", file.originalFilename)
        tempFile.deleteOnExit()
        file.transferTo(tempFile)
        return tempFile
    }

}