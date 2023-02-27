plugins {
    kotlin("multiplatform") version "1.8.10"
    id("com.vanniktech.maven.publish") version "0.24.0"
    `maven-publish`
}

group = "io.github.merlinths"
version = "1.0.0"

val coroutineVersion = "1.6.4"
val kluentVersion = "1.72"
val mockkVersion = "1.13.4"

repositories {
    mavenCentral()
}

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.Companion.S01, true)
    signAllPublications()

    coordinates(groupId = group.toString(), "swing-flow", version.toString())
    pom {
        name.set("SwingFlow")
        description.set("Kotlin Flow integration for Swing.")

        inceptionYear.set("2023")
        url.set("https://github.com/merlinths/swingflow")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("merlinths")
                name.set("Merlin Eden")
                url.set("https://github.com/MerlinTHS/")
            }
        }

        scm {
            url.set("https://github.com/MerlinTHS/SwingFlow/")
            connection.set("scm:git:git://github.com/merlinths/swingflow.git")
            developerConnection.set("scm:git:ssh://git@github.com/merlinths/swingflow.git")
        }
    }
}


kotlin {
    jvm {
        compilations.all {
            kotlinOptions {
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
                implementation("io.mockk:mockk:${mockkVersion}")
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