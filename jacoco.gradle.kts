import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.api.tasks.testing.Test

apply(plugin = "jacoco")

configure<JacocoPluginExtension> {
    toolVersion = "0.8.12"
}

val minCoverageVal: String = findProperty("coverage.threshold")?.toString() ?: "0.0"

val exclusionList = listOf(
    "**/dto/**",
    "**/model/**",
    "**/models/**",
    "**/entity/**",
    "**/entities/**",
    "**/config/**",
    "**/exception/**",
    "**/exceptions/**",
    "**/constant/**",
    "**/constants/**",
    "**/enum/**",
    "**/enums/**",
    "**/interceptor/**",
    "**/migration/**",
    "**/seeder/**",
    "**/*Application*"
)

// Packages of the classes generated from .proto files, read from the generated sources
// (<root>/<sourceSet>/<generator>/<package>) once compilation has produced them
val generatedProtoPackages: Set<String> by lazy {
    listOf("generated/sources/proto", "generated/source/proto")
        .map { layout.buildDirectory.dir(it).get().asFile }
        .filter { it.isDirectory }
        .flatMap { root ->
            root.walkTopDown()
                .filter { it.isFile && (it.extension == "java" || it.extension == "kt") }
                .map { it.parentFile.relativeTo(root).invariantSeparatorsPath.split("/").drop(2).joinToString("/") }
                .toList()
        }
        .toSet()
}

val isGeneratedProtoClass = Spec<FileTreeElement> {
    !it.isDirectory && it.relativePath.parent?.pathString in generatedProtoPackages
}

tasks.named<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.named("test"))
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
    classDirectories.setFrom(
        files(classDirectories.files.map {
            fileTree(it) {
                exclude(exclusionList)
                exclude(isGeneratedProtoClass)
            }
        })
    )
}

tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    dependsOn(tasks.named("jacocoTestReport"))
    classDirectories.setFrom(
        files(classDirectories.files.map {
            fileTree(it) {
                exclude(exclusionList)
                exclude(isGeneratedProtoClass)
            }
        })
    )
    violationRules {
        rule {
            element = "BUNDLE"
            limit {
                counter = "INSTRUCTION"
                value = "COVEREDRATIO"
                minimum = minCoverageVal.toBigDecimal()
            }
        }
    }
}

tasks.withType<Test> {
    finalizedBy(tasks.named("jacocoTestReport"))
}
