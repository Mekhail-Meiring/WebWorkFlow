
clean_build_app: # Cleans and builds the application
	@echo "cleaning..."
	./gradlew clean
	@echo "Building jar..."
	./gradlew build
	@echo "Done."


build_container: ## Build the docker container for the application from dockerfile
	@echo "Building image..."
	sudo docker build -t com/webworkflow .
	@echo "Done."


package: ## Packages the project
	make clean_build_app
	make build_container


run: ## Run the application
	@echo "Running..."
	sudo docker run -d -p 8080:8080 com/webworkflow
	@echo "Done."


stop: ## Stop all running containers
	@echo "Stopping..."
	sudo docker stop $(sudo docker ps -aq)
	@echo "Done."


run_tests: ## Run project tests
	@echo "Running tests..."
	./gradlew test
	@echo "Done."