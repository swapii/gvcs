plugins {
    kotlin("jvm") version "1.9.22"
    `maven-publish`
    `java-gradle-plugin` // Need to force JitPack publish artifact with needed group
}

publishing {
    publications {
        create<MavenPublication>("plugin") {
            artifactId = "gvcs-gradle-plugin"
            from(components["java"])
        }
    }
}

dependencies {
    implementation(gradleApi())
}
