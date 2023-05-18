plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.jvm.plugin)
    implementation(libs.kotlin.serialization.plugin)
    implementation(libs.dokka.plugin)
}
