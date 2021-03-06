import dependencies.Deps

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish")
}

kotlin {
    jvm()

    js {
        browser()
        nodejs()
    }

    ios()
    tvos()
    watchos()

    linuxX64()
    macosX64()
    mingwX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(autoModules.remotedata))
                implementation(Deps.Coroutines.core)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(project(autoModules.testUtil))
            }
        }

        val jvmTest by getting {
            dependencies {
                runtimeOnly(Deps.Kotlin.reflect)
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

signing {
    val SIGNING_PRIVATE_KEY: String? by project
    val SIGNING_PASSWORD: String? by project
    useInMemoryPgpKeys(SIGNING_PRIVATE_KEY, SIGNING_PASSWORD)
}

tasks.named<Test>("jvmTest") {
    useJUnitPlatform {
        includeEngines("kotest")
    }
}
