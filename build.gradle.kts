import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.2"
	id("io.spring.dependency-management") version "1.1.0"
	id("com.github.node-gradle.node") version "3.5.1"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
}

group = "com.WebWorkFlow"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17


repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.apache.poi:poi:5.2.3")
	implementation("org.apache.poi:poi-ooxml:5.2.3")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

node {
	download.set(true)
	version.set("16.13.0")
	exec {
		commandLine("npm", "install")
		workingDir("${project.projectDir}/src/main/frontend")
	}
}

tasks {

	withType<Test> {
		useJUnitPlatform()
	}

	register("buildFrontend") {
		dependsOn("nodeSetup")
		doLast {
			exec {
				commandLine("npm", "run", "build")
				workingDir("${project.projectDir}/src/main/frontend")
			}
		}
	}

	register("copyFrontendBuild") {
		dependsOn("buildFrontend")
		doLast {
			copy {
				from("${project.projectDir}/src/main/frontend/build")
				into("${project.projectDir}/src/main/resources/static")
			}
		}
	}

	withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "17"
		}
	}

	bootJar {
		dependsOn("copyFrontendBuild")
	}

}

