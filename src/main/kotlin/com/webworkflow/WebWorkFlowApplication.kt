package com.webworkflow

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * The main entry point for the WebWorkFlowApplication.
 *
 * @author Mekhail Meiring
 * @since 03/02/2023
 *
 * This class is annotated with @SpringBootApplication, indicating it as the main configuration class for a Spring Boot application.
 * The main function is used to launch the application using the runApplication function from the Spring Boot framework.
 */
@SpringBootApplication
class WebWorkFlowApplication


/**
 * The starting point of the application, this function launches the WebWorkFlowApplication.
 *
 * @param args The command line arguments passed to the application.
 */
fun main(args: Array<String>) {
	runApplication<WebWorkFlowApplication>(*args)
}
