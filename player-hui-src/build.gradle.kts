plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

sourceSets.main.get().java.srcDir("src/main")
sourceSets.main.get().resources.srcDir("src/resources")

application {
    mainClassName = "sc.player.util.Starter"
}

repositories {
    jcenter()
    maven("https://maven.wso2.org/nexus/content/groups/wso2-public/")
    maven("https://jitpack.io")
}

dependencies {
    if(gradle.startParameter.isOffline) {
        implementation(fileTree("lib"))
    } else {
        implementation("com.github.software-challenge.backend", "hui_2025", "25.0.6")
    }
}

tasks.shadowJar {
    archiveBaseName.set("hui_2025_client")
    archiveClassifier.set("")
    destinationDirectory.set(rootDir)
}
