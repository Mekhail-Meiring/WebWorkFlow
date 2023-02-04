package com.webworkflow.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.webworkflow.datasource.dto.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.io.File

@SpringBootTest
@AutoConfigureMockMvc
internal class WebControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    @Nested
    @DisplayName("Post /addUser")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class  AddUser{
        @Test
        fun `should add a user to database` () {
            // Given
            val user = User("John", "Doe", "01/01/2000")

            // When
            val performPost = mockMvc.post("/addUser") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(user)
            }

            // Then
            performPost
                .andExpect {
                    status { isCreated() }
                    jsonPath("$.first_name") { value("John") }
                    jsonPath("$.last_name") { value("Doe") }
                    jsonPath("$.date_of_birth") { value("01/01/2000") }
                }
        }

        @Test
        fun `should return BAD REQUEST when invalid userdata is given` () {
            // Given
            val invalidUser = User("123", "", "")

            // When
            val performPost = mockMvc.post("/addUser") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidUser)
            }

            // Then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }

        }
    }


    @Nested
    @DisplayName("Upload content from excel file")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class  UploadExcelFile{

        @Test
        fun `Given that there is a user in the database` () {
            // When/ Then
            mockMvc.post("/addUser") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(User("John", "Doe", "01/01/2000"))
            }
                .andExpect {
                    status { isCreated() }
                }
        }


        @Test
        fun `Then we should be able to upload an excel file to the database` () {

            // Given
            val file = File("src/test/resources/excel/test.xlsx")
            val fileName = file.name
            val fileContent = file.readBytes()
            val multipartFile = MockMultipartFile("file", fileName, "application/vnd.ms-excel", fileContent)

            // When
            val performPost = mockMvc.perform(
                MockMvcRequestBuilders.multipart("/upload")
                    .file(multipartFile)
                    .param("username", "John")
            )

            // Then
            assertThat(performPost.andReturn().response.status).isEqualTo(HttpStatus.CREATED.value())
        }

        @Test
        fun `Then we should be able to get the monthly income and expenses` () {

            // When
            val performGet = mockMvc.get("/monthlyIncomeAndExpense/John")

            // Then
            performGet
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                }
        }

        @Test
        fun `When we upload excel file to a user that does not exist should return NOT_FOUND` () {
            // Given
            val file = File("src/test/resources/excel/test.xlsx")
            val fileName = file.name
            val fileContent = file.readBytes()
            val multipartFile = MockMultipartFile("file", fileName, "application/vnd.ms-excel", fileContent)

            // When
            val performPost = mockMvc.perform(
                MockMvcRequestBuilders.multipart("/upload")
                    .file(multipartFile)
                    .param("username", "Jack") // Jack does not exist in the database
            )

            // Then
            assertThat(performPost.andReturn().response.status).isEqualTo(HttpStatus.NOT_FOUND.value())
            assertThat(performPost.andReturn().response.contentAsString).isEqualTo("User does not exist")

        }
    }

}