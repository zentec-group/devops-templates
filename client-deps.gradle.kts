val zentecUser: String = (project.findProperty("zentec.gpr.user")
    ?: System.getenv("ZENTEC_GPR_USER")
    ?: project.findProperty("gpr.user")
    ?: System.getenv("GPR_USER")
    ?: "github-actions") as String

val zentecToken: String = (project.findProperty("zentec.gpr.token")
    ?: System.getenv("ZENTEC_GPR_TOKEN")
    ?: project.findProperty("gpr.token")
    ?: System.getenv("GPR_TOKEN")
    ?: "") as String

// Configure Zentec GitHub Packages repository for dependency resolution
repositories {
    mavenCentral()
    maven {
        name = "ZentecGitHubPackages"
        url = uri("https://maven.pkg.github.com/zentec-group/*")
        credentials {
            username = zentecUser
            password = zentecToken
        }
    }
}

// Force 0-second cache for dynamic versions and changing snapshot modules
configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
    resolutionStrategy.cacheDynamicVersionsFor(0, "seconds")
}

// Branch-aware dynamic version resolution helper
val currentBranch: String = System.getenv("GITHUB_REF_NAME")
    ?: try {
        val process = ProcessBuilder("git", "rev-parse", "--abbrev-ref", "HEAD").start()
        process.inputStream.bufferedReader().readText().trim()
    } catch (_: Exception) {
        "develop"
    }

val dynamicZentecVersion: String = if (currentBranch == "main" || currentBranch == "master") {
    "latest.release"
} else {
    "1.0.0-rc.+"
}

// Expose zentecVersion property globally for client build.gradle.kts
extra["zentecVersion"] = dynamicZentecVersion
