import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    org.jetbrains.kotlin.jvm
}

repositories {
    mavenCentral()
    // PaperMC repository.
    maven("https://papermc.io/repo/repository/maven-public/")
}

tasks {
    withType<KotlinCompile> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    withType<JavaCompile>() {
        targetCompatibility = "17"
    }
}
