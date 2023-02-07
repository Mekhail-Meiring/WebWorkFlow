
clean_build_app: # Cleans and builds the application
	@echo "cleaning..."
	./gradlew clean
	@echo "Building jar..."
	./gradlew build
	@echo "Done."


build_image: ## Build the docker image
	@echo "Building image..."
	sudo docker build -t com/webworkflow .
	@echo "Done."


package: ## Packages the project
	make clean_build_app
	make build_image


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