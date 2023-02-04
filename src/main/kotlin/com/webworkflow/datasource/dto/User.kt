package com.webworkflow.datasource.dto

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Data class representing the User entity
 *
 * @property firstName The first name of the user
 * @property lastName The last name of the user
 * @property dateOfBirth The date of birth of the user
 */
data class User(
    @JsonProperty("first_name")
    val firstName : String,
    @JsonProperty("last_name")
    val lastName : String,
    @JsonProperty("date_of_birth")
    val dateOfBirth : String
)
