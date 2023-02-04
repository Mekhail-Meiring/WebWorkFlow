import com.github.gradle.node.task.NodeTask
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
	yarnVersion.set("1.27.17")
}


tasks{

	withType<Test> {
		useJUnitPlatform()
	}

	val install = create<NodeTask> ("install dependencies") {
		workingDir.set(file("${project.projectDir}/src/main/frontend"))
		args.set(listOf("install"))
	}

	val build = create<NodeTask> ("build frontend") {
		dependsOn(install)
		workingDir.set(file("${project.projectDir}/src/main/frontend"))
		args.set(listOf("build"))
	}

	val copy = create<Copy> ("copy frontend") {
		dependsOn(build)
		from(file("${project.projectDir}/src/main/frontend/build"))
		into(file("${rootDir}/build/resources/main/static/."))
//		into(file("${project.projectDir}/src/main/resources/static"))
	}

	val cleanup = create<Delete> ("cleanup frontend") {
		delete(file("${project.projectDir}/src/main/frontend/build"))
	}

	withType<KotlinCompile> {
		dependsOn(copy)
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "17"
		}
	}

	clean {
		dependsOn(cleanup)
	}



}

