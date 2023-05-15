plugins {
    module
}

description = "PaperKt core to bundle in plugins."

dependencies {
    api(libs.kotlin.coroutines)
    api(libs.kotlin.logging) {
        // Provided by Paper.
        exclude(group = "org.slf4j", module = "slf4j-api")
    }
    compileOnly(libs.paper.api)
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
