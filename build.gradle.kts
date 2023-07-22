import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
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
                implementation("org.seleniumhq.selenium:selenium-java:4.10.0")
                implementation("io.insert-koin:koin-core:3.1.2")

                // kt-vox
                implementation("com.github.kitakkun:kt-vox:0.0.3")
                val retrofitVersion = "2.9.0"
                implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")

                val voyagerVersion = "1.0.0-rc06"
                // Navigator
                implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
                // BottomSheetNavigator
                implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")
                // TabNavigator
                implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
                // Transitions
                implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")

                implementation("org.jetbrains.compose.material:material-icons-extended:1.4.1")
                implementation("io.github.bonigarcia:webdrivermanager:5.0.3")

                implementation("io.coil-kt:coil-compose:2.4.0")
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "com.github.kitakkun.mirrorcomment.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "MirrorComment"
            packageVersion = "1.0.0"
        }
    }
}
