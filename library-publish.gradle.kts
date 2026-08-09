apply(plugin = "maven-publish")

// Dynamic credential resolution supporting both Zentec and legacy property naming conventions.
val publishUser: String = project.findProperty("zentec.gpr.user") as String?
    ?: System.getenv("ZENTEC_GPR_USER")
    ?: project.findProperty("gpr.user") as String?
    ?: System.getenv("GPR_USER")
    ?: "github-actions"

val publishToken: String = project.findProperty("zentec.gpr.token") as String?
    ?: System.getenv("ZENTEC_GPR_TOKEN")
    ?: project.findProperty("gpr.token") as String?
    ?: System.getenv("GPR_TOKEN")
    ?: ""

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
            if (project.hasProperty("version") && project.property("version") != "unspecified") {
                version = project.property("version").toString()
            }
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/zentec-group/${project.name}")
            credentials {
                username = publishUser
                password = publishToken
            }
        }
    }
}
