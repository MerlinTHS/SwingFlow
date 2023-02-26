plugins {
    kotlin("multiplatform") version "1.8.10"
    `maven-publish`
}

group = "io.github.merlinths"
version = "1.0.4"

val coroutineVersion = "1.6.4"
val kluentVersion = "1.72"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions {
                languageVersion = "1.9"
                freeCompilerArgs += listOf(
                    "-Xcontext-receivers",
                    "-XXLanguage:+ReferencesToSyntheticJavaProperties"
                )
            }
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation("com.github.MerlinTHS:Kava:1.0.2")

                implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")

                implementation("org.slf4j:slf4j-api:2.0.6")
                implementation("org.slf4j:slf4j-simple:2.0.6")

                implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.10")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:$coroutineVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:$coroutineVersion")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineVersion")
                implementation("org.amshove.kluent:kluent:$kluentVersion")
            }
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = "SwingFlow"
            version = project.version.toString()

            from(components["java"])
        }
    }
}