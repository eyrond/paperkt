import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URL

plugins {
    org.jetbrains.dokka
    org.jetbrains.kotlin.jvm
    `maven-publish`
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

    withType<JavaCompile> {
        targetCompatibility = "17"
    }

    withType<AbstractDokkaLeafTask> {
        moduleName.set(project.name)
        failOnWarning.set(true)
        dokkaSourceSets.configureEach {
            jdkVersion.set(17)
            suppressGeneratedFiles.set(false)
            sourceLink {
                localDirectory.set(project.projectDir)
                remoteUrl.set(URL("https://github.com/eyrond/paperkt/blob/${project.commitHash}/${project.name}"))
                remoteLineSuffix.set("#L")
            }
            externalDocumentationLink("https://kotlinlang.org/api/kotlinx.coroutines")
            externalDocumentationLink("https://kotlinlang.org/api/kotlinx.serialization")
            externalDocumentationLink(
                url = "https://kotlinlang.org/api/kotlinx-datetime",
                packageListUrl = "https://kotlinlang.org/api/kotlinx-datetime/kotlinx-datetime/package-list",
            )
        }
    }
}

publishing {
    publications.register<MavenPublication>(project.name) {
        from(components["java"])
        artifact(tasks.kotlinSourcesJar)
    }
}
