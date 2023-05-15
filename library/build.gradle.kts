plugins {
    module
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

description = "PaperKt library that bundles all the modules."

dependencies {
    api(libs.paper.api)
    api(project(":core"))
}

tasks {
    processResources {
        expand("version" to project.version)
    }

    shadowJar {
        dependencyFilter.apply {
            fun ResolvedDependency.isDerivedFrom(name: String): Boolean {
                return parents.any { it.moduleName == name || it.isDerivedFrom(name) }
            }

            // Exclude all the dependencies that are already provided by Paper.
            exclude(dependency("org.jetbrains:annotations"))
            exclude {
                it.moduleGroup == "io.papermc.paper" && it.moduleName == "paper-api" || it.isDerivedFrom("paper-api")
            }
        }
    }
}
