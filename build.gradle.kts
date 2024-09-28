plugins {
    kotlin("jvm") version "2.0.20"
}

group = "dev.h4kt"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public")
    maven("https://repo.codemc.io/repository/maven-public")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    compileOnly("de.tr7zw:item-nbt-api-plugin:2.13.2")
}

kotlin {
    jvmToolchain(21)
}
