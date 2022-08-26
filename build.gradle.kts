import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val springDocOpenApiVersion: String by project
val log4jApiKotlinVersion: String by project
val mockkVersion: String by project
val springMockkVersion: String by project
val striktVersion: String by project
val restAssuredWebTestClientVersion: String by project

plugins {
    id("org.springframework.boot") version "2.7.3"
    id("io.spring.dependency-management") version "1.0.13.RELEASE"
    id("org.asciidoctor.convert") version "2.4.0"
    id("org.springdoc.openapi-gradle-plugin") version "1.4.0"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    id("org.jlleitschuh.gradle.ktlint-idea") version "11.0.0"
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.spring") version "1.7.10"
    jacoco
    java
}

group = "com.menta_tickets"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
    all {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
}

repositories {
    mavenCentral()
}

val snippetsDir by extra { file("build/generated-snippets") }

jacoco {
    version = "0.8.8"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:$log4jApiKotlinVersion")
    implementation("org.springdoc:springdoc-openapi-ui:$springDocOpenApiVersion")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:$springDocOpenApiVersion")
    implementation("org.springdoc:springdoc-openapi-kotlin:$springDocOpenApiVersion")
    implementation("org.springdoc:springdoc-openapi-javadoc:$springDocOpenApiVersion")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("com.ninja-squad:springmockk:$springMockkVersion")
    testImplementation("io.strikt:strikt-core:$striktVersion")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-webtestclient")
    testImplementation("io.rest-assured:spring-web-test-client:$restAssuredWebTestClientVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
        allWarningsAsErrors = true
    }
    finalizedBy(tasks.ktlintCheck)
}

task("stage") {
    dependsOn(tasks.build, tasks.clean)
}

tasks.build {
    mustRunAfter(tasks.clean)
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks.bootRun {
    dependsOn(tasks.ktlintCheck)
}

tasks.check {
    dependsOn(tasks.ktlintCheck)
}

tasks.withType<Test> {
    dependsOn(tasks.ktlintCheck)
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.test {
    outputs.dir(snippetsDir)
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
    extensions.configure(JacocoTaskExtension::class) {
        setDestinationFile(layout.buildDirectory.file("jacoco/jacocoTest.exec").get().asFile)
        classDumpDir = layout.buildDirectory.dir("jacoco/classpathdumps").get().asFile
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
    finalizedBy(tasks.jacocoTestCoverageVerification)
    reports {
        xml.required.set(true)
    }
    classDirectories.setFrom(
        classDirectories.files.map {
            fileTree(it).matching {
                exclude(
                    "**/utils/**",
                    "**/requests/**",
                    "**/responses/**",
                    "**/error/**",
                    "**/config/**",
                    "**/BaseController*",
                    "**/ChallengeApplication*",
                    "**/extensions/**"
                )
            }
        }
    )
}

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.jacocoTestReport)
    violationRules {
        rule {
            classDirectories.setFrom(tasks.jacocoTestReport.get().classDirectories)
            limit {
                value = "COVEREDRATIO"
                minimum = "0.8".toBigDecimal()
            }
        }

        rule {
            classDirectories.setFrom(tasks.jacocoTestReport.get().classDirectories)
            element = "CLASS"

            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.8".toBigDecimal()
            }
        }
    }
}

tasks.asciidoctor {
    inputs.dir(snippetsDir)
    dependsOn(tasks.test)
}
