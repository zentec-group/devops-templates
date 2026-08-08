import org.gradle.testing.jacoco.tasks.JacocoReport
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification

plugins {
    jacoco
}

jacoco {
    toolVersion = "0.8.12"
}

val minCoverage: String = (project.findProperty("coverage.threshold") as? String) ?: "0.70"

val exclusionList = listOf(
    "**/dto/**",
    "**/model/**",
    "**/entity/**",
    "**/config/**",
    "**/exception/**",
    "**/constant/**",
    "**/enum/**",
    "**/interceptor/**",
    "**/migration/**",
    "**/seeder/**",
    "**/*Application*"
)

tasks.named<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.test)
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
    violationRules {
        rule {
            limit {
                minimum = minCoverage.toBigDecimal()
            }
        }
    }
    classDirectories.setFrom(
        files(classDirectories.files.map {
            fileTree(it) {
                exclude(exclusionList)
            }
        })
    )
}

tasks.test {
    finalizedBy(tasks.named("jacocoTestReport"))
}
