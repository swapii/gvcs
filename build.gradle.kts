plugins {
    `java-gradle-plugin`
    kotlin("jvm") version "1.9.22"
    `maven-publish`
}

gradlePlugin {
    plugins {
        create("plugin") {
            id = "$group.gvcs"
            implementationClass = "gvcs.GVCSPlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("plugin") {
            artifactId = "$group.gvcs.gradle.plugin"
            from(components["java"])
        }
    }
}
