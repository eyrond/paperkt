plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
}

group = "dev.eyrond"
version = "1.0.1"

subprojects {
    group = "${rootProject.group}.paperkt"
    version = rootProject.version
}
