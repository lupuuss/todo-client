import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform") version "1.4.30"
    kotlin("plugin.serialization") version "1.4.30"
    id("com.android.application")
}

group = "com.github.lupuuss.todo"
version = "1.0"

repositories {
    google()
    jcenter()
    mavenCentral()
    maven {
        url = uri("https://dl.bintray.com/kotlin/kotlin-js-wrappers")
        url = uri("https://mymavenrepo.com/repo/2BJkI2Y3564lDjV9fO9A/")
    }
}

val vKtor = "1.5.1"
val vCoroutines = "1.4.2"
val vLifecycle = "2.3.0"
val vKodein = "7.3.0"

kotlin {
    android()
    js(LEGACY) {
        browser {
            binaries.executable()
            webpackTask {
                cssSupport.enabled = true
            }
            runTask {
                cssSupport.enabled = true
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.github.lupuuss.todo:core:1.0.4")

                implementation("org.kodein.di:kodein-di:$vKodein")

                implementation("io.ktor:ktor-client-core:$vKtor")
                implementation("io.ktor:ktor-client-websockets:$vKtor")
                implementation("io.ktor:ktor-client-serialization:$vKtor")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$vCoroutines")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {

                implementation("org.kodein.di:kodein-di-framework-android-x:$vKodein")
                implementation("org.kodein.di:kodein-di-jvm:$vKodein")

                implementation("com.github.lupuuss.todo:core-jvm:1.0.4")

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$vCoroutines")

                implementation("com.google.android.material:material:1.3.0")
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$vLifecycle")
                implementation("androidx.lifecycle:lifecycle-livedata-ktx:$vLifecycle")

                implementation("io.ktor:ktor-client-okhttp:$vKtor")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.1")
            }
        }
        val jsMain by getting {
            dependencies {

                implementation("com.github.lupuuss.todo:core-js:1.0.4")

                implementation("org.jetbrains:kotlin-react:16.13.1-pre.113-kotlin-1.4.0")
                implementation("org.jetbrains:kotlin-react-dom:16.13.1-pre.113-kotlin-1.4.0")
                implementation("org.jetbrains:kotlin-styled:1.0.0-pre.113-kotlin-1.4.0")
                implementation("org.jetbrains:kotlin-react-router-dom:5.1.2-pre.113-kotlin-1.4.0")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

android {

    packagingOptions {
        exclude("META-INF/core.kotlin_module")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        dataBinding = true
    }

    compileSdkVersion(30)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        applicationId = "com.github.lupuuss.todo.client.android"
        minSdkVersion(24)
        targetSdkVersion(30)
    }

}

tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack") {
    outputFileName = "output.js"
}

tasks.withType(KotlinCompile::class) {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}