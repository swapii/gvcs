plugins {
    `java-gradle-plugin`
    kotlin("jvm") version "1.7.0"
    `maven-publish`
}

repositories {
    mavenCentral()
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
