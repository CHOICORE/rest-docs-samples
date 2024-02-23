plugins {
    java
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
}

group = "me.choicore.study"
version = "0.0.1-SNAPSHOT"

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
    "asciidoctorExt"("org.springframework.restdocs:spring-restdocs-asciidoctor")
}
// val snippetsDir by extra { file("build/generated-snippets") }
//
// tasks.test {
//    useJUnitPlatform {
//        excludeTags("restdocs")
//    }
// }
//
// tasks.asciidoctor {
//    dependsOn("restdocsTest")
//    inputs.dir(snippetsDir)
//    configurations("asciidoctorExt")
//    baseDirFollowsSourceFile()
// }
//
// tasks.register("restdocsTest", Test::class) {
//    group = "verification"
//    outputs.dir(snippetsDir)
//    useJUnitPlatform {
//        includeTags("restdocs")
//    }
// }
//
// tasks.bootJar {
//    dependsOn(tasks.asciidoctor)
//    from(tasks.asciidoctor.get().outputDir) {
//        into("static/docs")
//    }
// }
// tasks.test {
//    useJUnitPlatform {
//        excludeTags("restdocs")
//    }
// }
//
// tasks.asciidoctor {
//    dependsOn("restdocsTest")
//    configurations("asciidoctorExt")
//    baseDirFollowsSourceFile()
// }
//
// tasks.register<Test>("restdocsTest") {
//    group = "verification"
//    useJUnitPlatform {
//        includeTags("restdocs")
//    }
// }
//
// tasks.bootJar {
//    dependsOn(tasks.asciidoctor)
//    from(tasks.asciidoctor.get().outputDir) {
//        into("static/docs")
//    }
// }

tasks {

    test { useJUnitPlatform { excludeTags("restdocs") } }

    create("restdocsTest", Test::class) {
        group = "verification"
        useJUnitPlatform {
            includeTags("restdocs")
        }
    }

    asciidoctor {
        dependsOn("restdocsTest")
        configurations("asciidoctorExt")
        baseDirFollowsSourceFile()
    }

    bootJar {
        dependsOn(asciidoctor)
        from(asciidoctor.get().outputDir) {
            into("static/docs")
        }
    }
}
