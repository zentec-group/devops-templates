val gprUser: String = project.findProperty("gpr.user") as String? ?: System.getenv("GPR_USER") ?: "github-actions"
val gprToken: String = project.findProperty("gpr.token") as String? ?: System.getenv("GPR_TOKEN") ?: ""

repositories {
    mavenCentral()
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/zentec-group/engine_schema")
        credentials {
            username = gprUser
            password = gprToken
        }
    }
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
    resolutionStrategy.cacheDynamicVersionsFor(0, "seconds")
}
