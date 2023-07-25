import org.gradle.internal.impldep.org.testng.reporters.XMLUtils.xml
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.storage.CacheResetOnProcessCanceled.enabled

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    alias(libs.plugins.about.libraries)
    alias(libs.plugins.detekt)
}

group = "com.github.kitakkun.mirrorcomment"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://jitpack.io")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.selenium)
                implementation(libs.koin.core)

                // kt-vox
                implementation(libs.ktvox)
                implementation(libs.retrofit)

                // voyager
                implementation(libs.voyager.navigator)
                implementation(libs.voyager.bottom.sheet.navigator)
                implementation(libs.voyager.tab.navigator)
                implementation(libs.voyager.transitions)

                implementation(libs.material.icons.extended)
                implementation(libs.webdrivermanager)

                implementation(libs.compose.color.picker)
                implementation(libs.compose.color.picker.jvm)
                implementation(libs.about.libraries.core)
                implementation(libs.about.libraries.compose)
                implementation(libs.multiplatform.settings)
            }
        }
        val jvmTest by getting
    }
}

dependencies {
    commonTestImplementation(kotlin("test"))
    commonTestImplementation(libs.koin.test)
}

compose.desktop {
    application {
        mainClass = "com.github.kitakkun.mirrorcomment.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "MirrorComment"
            packageVersion = "1.0.0"
            // ref: https://stackoverflow.com/questions/61727613/unexpected-behaviour-from-gson/74914488#74914488
            modules("jdk.unsupported")
            macOS {
                iconFile.set(project.file("icon.icns"))
            }
            linux {
                iconFile.set(project.file("icon.png"))
            }
            windows {
                iconFile.set(project.file("icon.ico"))
            }
        }
        buildTypes.release {
            proguard {
                configurationFiles.from("proguard-rules.pro")
            }
        }
    }
}

aboutLibraries {
    registerAndroidTasks = false
}

detekt {
    toolVersion = libs.versions.detekt.get()
    source.from("src/jvmMain/kotlin")
}
