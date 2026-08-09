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
    "**/support/**",
    "**/interceptor/**",
    "**/migration/**",
    "**/seeder/**",
    "**/*Application*"
)

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
