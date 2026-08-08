apply(plugin = "maven-publish")

val gprUser: String = project.findProperty("gpr.user") as String? ?: System.getenv("GPR_USER") ?: "github-actions"
val gprToken: String = project.findProperty("gpr.token") as String? ?: System.getenv("GPR_TOKEN") ?: ""

configure<PublishingExtension> {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/zentec-group/${project.name}")
            credentials {
                username = gprUser
                password = gprToken
            }
        }
    }
}
