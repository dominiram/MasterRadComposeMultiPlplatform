plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.realm.plugin)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.navigator)
            implementation(libs.navigator.screen.model)
            implementation(libs.tab.navigator)
            implementation(libs.navigator.transitions)
            implementation(libs.navigator.koin)
            implementation(libs.koin.core)
            implementation(libs.kotlin.coroutines)

            implementation(libs.coil3.coil.compose)
            implementation(libs.coil.network.ktor)

            implementation(libs.mongodb.realm)
            implementation(libs.stately.common)

            implementation(libs.constraintlayout.compose.multiplatform)
            implementation(libs.kamel.image)
            implementation(libs.ktor.client.core)

            implementation(libs.koin.core.v343)
            implementation(libs.koin.compose)
            implementation(libs.androidx.datastore.preferences.core)
            implementation(libs.androidx.datastore.core)

            implementation(libs.peekaboo.ui)
            implementation(libs.peekaboo.image.picker)
        }

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.android)
        }

        val iosMain by creating {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
    }

    task("testClasses")
}

android {
    namespace = "com.naum.master_rad_cmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.naum.master_rad_cmp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}
