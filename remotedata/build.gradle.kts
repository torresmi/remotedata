import dependencies.Deps

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.dokka")
    id("com.github.dcendents.android-maven")
    id("com.jfrog.bintray")
}

kotlin {
    jvm()

    sourceSets {
        val commonTest by getting {
            dependencies {
                implementation(Deps.Kotest.assertions)

                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(project(autoModules.testUtil))

                implementation(kotlin("test-junit"))

                runtimeOnly(Deps.Kotlin.reflect)
            }
        }
    }

    targets {
        iosArm64()
        watchosArm64()
        tvosArm64()
        js()
        macosX64()
        linuxX64()
        mingwX64()
    }
}

tasks.named<Test>("jvmTest") {
    useJUnitPlatform {
        includeEngines("kotest")
    }
}
