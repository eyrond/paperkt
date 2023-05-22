plugins {
    module
    `published-module`
    id("io.papermc.paperweight.userdev") version "1.5.5"
}

dependencies {
    api(libs.kotlin.coroutines)
    api(libs.kotlin.logging) {
        // Provided by Paper.
        exclude(group = "org.slf4j", module = "slf4j-api")
    }
    paperweight.paperDevBundle(libs.versions.paper.api.get())
    api(kotlin("reflect"))
}

kotlin {
    sourceSets.forEach {
        it.languageSettings {
            optIn("kotlinx.coroutines.DelicateCoroutinesApi")
            optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            optIn("kotlinx.coroutines.InternalCoroutinesApi")
        }
    }
}
