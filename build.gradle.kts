group = "io.github.selevinia"
version = "0.3.2"
description = "Spring Boot Autoconfigure for Tarantool Database"

plugins {
    `java-library`
    `maven-publish`
    signing
}

repositories {
    mavenLocal()
    mavenCentral()
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    api("org.springframework.boot:spring-boot-autoconfigure:2.5.2")

    compileOnly("io.github.selevinia:spring-data-tarantool:0.3.2")
    compileOnly("io.tarantool:cartridge-driver:0.4.3")
    compileOnly("io.projectreactor:reactor-core:3.4.7")

    annotationProcessor("org.springframework.boot:spring-boot-autoconfigure-processor:2.5.2")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:2.5.2")

    testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
    testImplementation("org.assertj:assertj-core:3.20.2")
    testImplementation("io.github.selevinia:spring-data-tarantool:0.3.2")
    testImplementation("io.tarantool:cartridge-driver:0.4.3")
    testImplementation("io.projectreactor:reactor-core:3.4.7")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name.set("Spring Boot Tarantool Autoconfigure")
                description.set("Spring Boot Autoconfigure for Tarantool Database")
                url.set("https://github.com/selevinia/spring-boot-autoconfigure-tarantool")

                scm {
                    connection.set("scm:git:git://github.com/selevinia/spring-boot-autoconfigure-tarantool.git")
                    developerConnection.set("scm:git:ssh://github.com/selevinia/spring-boot-autoconfigure-tarantool.git")
                    url.set("https://github.com/selevinia/spring-boot-autoconfigure-tarantool")
                }

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("rx-alex")
                        name.set("Alexander Rublev")
                        email.set("invalidator.post@gmail.com")
                    }
                    developer {
                        id.set("t-obscurity")
                        name.set("Tatiana Blinova")
                        email.set("blinova.tv@gmail.com")
                    }
                }
            }
        }
    }
    repositories {
        maven {
            val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials {
                username = rootProject.findProperty("nexus.username").toString()
                password = rootProject.findProperty("nexus.password").toString()
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}