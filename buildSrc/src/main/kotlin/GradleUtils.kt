import org.codehaus.groovy.runtime.ResourceGroovyMethods
import org.gradle.api.GradleException
import java.io.File

private const val VERSION_NAME_FILE_PATH = "buildSrc/src/main/kotlin/constants/Android.kt"
private const val VERSION_NAME_FIELD = "VERSION_NAME"

private const val ANSI_YELLOW = "\u001B[33m"
private const val ANSI_RESET = "\u001B[0m"

fun generationMobileVersionAndSave() = generationVersionAndSave(
    VERSION_NAME_FILE_PATH,
    VERSION_NAME_FIELD
)

fun generationVersionAndSave(path: String, field: String): String {
    val versionNameFile = File(path)

    val versionNameLine = versionNameFile.readLines()
        .firstOrNull { it.contains(field) }
        .orEmpty()

    if (versionNameLine.isEmpty()) {
        throw GradleException("Field not found [$field] in file [$path]")
    }

    val versionString = versionNameLine.split("=")
        .lastOrNull()
        .orEmpty()
        .trim()
        .removeSurrounding("\"")

    if (versionString.isEmpty()) {
        throw GradleException("You need to specify the string version number for field [$field]")
    }

    val versionsString = versionString.split(".")
    val lastDotVersion = versionsString.lastOrNull().orEmpty()
    val isEmptyDotVersion = lastDotVersion.length != 3
    val newDotVersion = if (!isEmptyDotVersion) lastDotVersion.toInt() + 1 else 1
    val formatNewDotVersion = String.format("%03d", newDotVersion)

    val versionStringWithNewDotVersion = if (isEmptyDotVersion) {
        "$versionString.$formatNewDotVersion"
    } else {
        versionString.replaceFirst(lastDotVersion, formatNewDotVersion)
    }

    val versionNameFileWithNewVersion = versionNameFile.readText()
        .replaceFirst(versionString, versionStringWithNewDotVersion)

    println("${ANSI_YELLOW}Generation new version app: $versionStringWithNewDotVersion$ANSI_RESET")

    ResourceGroovyMethods.write(versionNameFile, versionNameFileWithNewVersion)

    return versionStringWithNewDotVersion
}
