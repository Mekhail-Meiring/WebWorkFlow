# WebWorkFlow
An Extensible Web-Based Workflow System

## Description:
WebWorkFlow is a simple and user-friendly application that allows you to track your monthly income and expenses throughout 
the year. To use the app, you only need to create a profile, select and upload your Excel (.xlsx) file, and you will be 
presented with a graphical representation of your data.


## Features:
    - User-friendly interface for creating a profile and uploading Excel files
    - Automated graphical representation of income and expenses data
    - Ability to download the graph as a PNG file
    - Supports Excel files with the following format:
        - The first row should include the names of the columns (Month, Income, Expenses)
        - The months should be formatted as full names (e.g., January, February, March) or abbreviations (e.g., Jan, Feb, Mar)
        - The rest of the cells should contain the values for income and expenses for each month and year (e.g., R1000.00, R2000.00, R3500.99)


## How to Use the Application:

- Pre-requisites:
  - Docker installation
  - Make installation (for Unix-based systems)


Once you completed either of the options below, open your web browser and navigate to http://localhost:8080/

    - Option 1: Github Repository Packages:
        - Navigate to Github repository packages and pull the latest image of the container.
        - Run the following command in the terminal:
            - docker run -p 8080:8080 ghcr.io/mekhail-meiring/webworkflow:latest

    - Option 2: For Unix-based systems (such as Linux):
        - Open the terminal and use the following commands:
            - make package 
            - make run

    - Option 3: For other systems:
        - Run the following gradle commands in the terminal:
            - gradlew clean
            - gradlew bootBuildImage
            - docker run -p 8080:8080 docker.io/library/webworkflow:0.0.1-SNAPSHOT
    

## Live Deployment:
A live deployment of WebWorkFlow is also available at https://webworkflow-swlhg.ondigitalocean.app/
for easy access and demonstration.


## How to run tests:
    
    - For Unix-based systems (such as Linux).
        - Open the terminal and use the following commands:
            - make run_test

    - For other systems:
        - Run the following gradle command in the terminal:
            - gradlew test