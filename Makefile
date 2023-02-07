
package: # Package the application
	@echo "cleaning..."
	./gradlew clean
	@echo "Building docker image..."
	./gradlew bootBuildImage
	@echo "Done."


run: ## Run the application
	@echo "Running..."
	sudo docker run -d -p 8080:8080 docker.io/library/webworkflow:0.0.1-SNAPSHOT
	@echo "Done."


stop: ## Stop all running containers
	@echo "Stopping..."
	sudo docker stop $(sudo docker ps -aq)
	@echo "Done."


run_tests: ## Run project tests
	@echo "Running tests..."
	./gradlew test
	@echo "Done."