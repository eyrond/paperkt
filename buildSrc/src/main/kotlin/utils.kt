import org.gradle.api.Project
import java.io.ByteArrayOutputStream

private val Project.gitTag: String?
    get() = execGit("tag", "--no-column", "--points-at", "HEAD")
        .takeIf { it.isNotBlank() }
        ?.lines()
        ?.single()

val Project.moduleVersion: String get() = gitTag ?: "${execGit("branch", "--show-current").replace("/", "-")}-SNAPSHOT"

val Project.commitHash get() = execGit("rev-parse", "--verify", "HEAD")
val Project.isRelease: Boolean get() = gitTag != null

private fun Project.execGit(vararg command: String): String {
    val output = ByteArrayOutputStream()
    exec {
        commandLine("git", *command)
        standardOutput = output
        errorOutput = output
        workingDir = rootDir
    }.rethrowFailure().assertNormalExitValue()
    return output.toString().trim()
}
