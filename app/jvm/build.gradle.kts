import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvmToolchain(17)
    jvm()

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.datetime)

                implementation(compose.material3)
                implementation(libs.lifecycle.runtime.compose)

                runtimeOnly(compose.desktop.currentOs)
            }
        }
    }
}

compose {
    desktop {
        application {
            mainClass = "io.github.taetae98coding.remotemacro.JvmAppKt"

            nativeDistributions {
                includeAllModules = true
                targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)

                packageName = "RemoteMacro"
                packageVersion = "1.0.0"

                macOS {
                    appStore = true
                    bundleID = "io.github.taetae98coding.remotemacro"
                    iconFile.set(rootProject.file("asset/icon/icon.icns"))
                }
                windows {
                    iconFile.set(rootProject.file("asset/icon/icon.ico"))
                }
                linux {
                    iconFile.set(rootProject.file("asset/icon/icon.png"))
                }
            }

            buildTypes {
                release {
                    proguard {
                        isEnabled.set(false)
                    }
                }
            }
        }
    }
}