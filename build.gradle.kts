import com.epages.restdocs.apispec.gradle.OpenApi3Task

plugins {
    java
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    id("com.epages.restdocs-api-spec") version "0.19.2"
}

group = "me.choicore.study"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
    create("asciidoctorExt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("com.epages:restdocs-api-spec-mockmvc:0.19.2")
    "asciidoctorExt"("org.springframework.restdocs:spring-restdocs-asciidoctor")
}

tasks.register<Test>("restdocsTest") {
    group = "verification"
    useJUnitPlatform {
        includeTags("restdocs")
    }
}

tasks.withType<OpenApi3Task> {
    dependsOn("restdocsTest")
}

tasks.register<Copy>("copyOasToSwaggerUI") {
    dependsOn("openapi3")
    val staticResourcesDir = "src/main/resources/static"
    val openapi3Filename = "${openapi3.outputFileNamePrefix}.${openapi3.format}"

    doFirst {
        delete("${layout.projectDirectory}/$staticResourcesDir/$openapi3Filename")
    }
    from("${layout.buildDirectory.get()}/api-spec/$openapi3Filename")
    into(staticResourcesDir)
}

tasks.test {
    useJUnitPlatform {
        excludeTags("restdocs")
    }
}

tasks.asciidoctor {
    dependsOn("restdocsTest")
    configurations("asciidoctorExt")
    baseDirFollowsSourceFile()
}

openapi3 {
    setServer("http://localhost:8080")
    title = "Sample API"
    description = "This is a sample API"
    version = "${project.version}"
    format = "json"
}
