plugins {
    module
    `published-module`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

dependencies {
    compileOnly(libs.paper.api)
    api(project(":core"))
}

tasks {
    processResources {
        expand("version" to project.version)
    }

    shadowJar {
        archiveFileName.set("paperkt-library-${version}.jar")

        dependencyFilter.apply {
            // Exclude all the dependencies that are already provided by Paper.
            exclude(dependency("org.jetbrains:annotations"))
        }
    }
}
