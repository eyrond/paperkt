import java.lang.System.getenv

plugins {
    `maven-publish`
    signing
}


fun MavenPublication.registerDokkaJar() {
    tasks.register<Jar>("${name}DokkaJar") {
        archiveClassifier.set("javadoc")
        destinationDirectory.set(destinationDirectory.get().dir(name))
        from(tasks.named("dokkaHtml"))
    }
}

publishing {
    publications {
        withType<MavenPublication>().configureEach {
            artifact(registerDokkaJar())

            groupId = "dev.eyrond.paperkt"
            artifactId = project.name
            version = moduleVersion

            pom {
                name.set(project.name)
                description.set(project.description)
                url.set("https://github.com/eyrond/paperkt")

                developers {
                    developer {
                        name.set("Eyrond")
                        url.set("https://eyrond.dev")
                        email.set("contact@eyrond.dev")
                        timezone.set("Europe/Kyiv")
                    }
                }

                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/eyrond/paperkt/issues")
                }

                licenses {
                    license {
                        name.set("GPL v3.0")
                        url.set("https://opensource.org/license/gpl-3-0/")
                    }
                }

                scm {
                    connection.set("scm:git:ssh://github.com/eyrond/paperkt.git")
                    developerConnection.set("scm:git:ssh://git@github.com:eyrond/paperkt.git")
                    url.set("https://github.com/eyrond/paperkt")
                }
            }
        }
    }

    repositories {
        maven {
            url = uri(
                if (isRelease) {
                    "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
                } else {
                    "https://s01.oss.sonatype.org/content/repositories/snapshots/"
                }
            )

            credentials {
                username = getenv("NEXUS_USER")
                password = getenv("NEXUS_PASSWORD")
            }
        }
    }
}

if (isRelease) {
    signing {
        val secretKey = findProperty("signingKey")?.toString()
        val password = findProperty("signingPassword")?.toString()
        if (secretKey != null && password != null) {
            useInMemoryPgpKeys(secretKey, password)
        }
        sign(publishing.publications)
    }
}
