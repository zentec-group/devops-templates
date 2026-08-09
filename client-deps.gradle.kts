val zentecUser: String = (project.findProperty("zentec.gpr.user") ?: System.getenv("ZENTEC_GPR_USER") ?: project.findProperty("gpr.user") ?: System.getenv("GPR_USER") ?: "github-actions") as String
val zentecToken: String = (project.findProperty("zentec.gpr.token") ?: System.getenv("ZENTEC_GPR_TOKEN") ?: project.findProperty("gpr.token") ?: System.getenv("GPR_TOKEN") ?: "") as String

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/zentec-group/engine_schema")
        credentials {
            username = zentecUser
            password = zentecToken
        }
    }
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
    resolutionStrategy.cacheDynamicVersionsFor(0, "seconds")
}
