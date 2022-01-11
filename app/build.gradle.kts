plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("com.google.android.gms.oss-licenses-plugin")
    id("kotlin-android")
}

android {
    compileSdk = Constants.compileSdk
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = Constants.packageName
        minSdk = Constants.minSdk
        targetSdk = Constants.targetSdk
        versionCode = Constants.versionCode
        versionName = Constants.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storeFile = File("${project.rootDir.absolutePath}/keystore/debug.keystore")
            storePassword = "android"
        }
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("String", "KAKAO_AUTH", "\"a7ddbcd24d7fff22320cc13a1e534104\"")
        }

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = Versions.javaVersion
        targetCompatibility = Versions.javaVersion
    }

    kotlinOptions {
        jvmTarget = Versions.jvmVersion
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core"))

    // Kotlin
    implementation(KotlinDependencies.kotlin)

    // AndroidX
    implementation(AndroidXDependencies.coreKtx)
    implementation(AndroidXDependencies.appCompat)
    implementation(AndroidXDependencies.constraintLayout)
    implementation(AndroidXDependencies.startup)
    implementation(AndroidXDependencies.hilt)
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")
    kapt(KaptDependencies.hiltCompiler)
    implementation(AndroidXDependencies.fragment)
    implementation(AndroidXDependencies.legacy)
    implementation(AndroidXDependencies.security)
    implementation(AndroidXDependencies.coroutines)
    implementation(AndroidXDependencies.lifeCycleKtx)
    implementation(AndroidXDependencies.lifecycleJava8)

    // Third-Party
    implementation(ThirdPartyDependencies.glide)
    kapt(KaptDependencies.glide)
    implementation(ThirdPartyDependencies.gson)
    implementation(platform(ThirdPartyDependencies.okHttpBom))
    implementation(ThirdPartyDependencies.okHttp)
    implementation(ThirdPartyDependencies.okHttpLoggingInterceptor)
    implementation(ThirdPartyDependencies.retrofit)
    implementation(ThirdPartyDependencies.retrofitGsonConverter)
    implementation(ThirdPartyDependencies.timber)
    implementation(ThirdPartyDependencies.ossLicense)
    implementation(ThirdPartyDependencies.kakaoLogin)
    implementation(ThirdPartyDependencies.naverMap)
    implementation(ThirdPartyDependencies.mapLocation)

    // Material Design
    implementation(MaterialDesignDependencies.materialDesign)

    // Flipper
    debugImplementation(ThirdPartyDependencies.flipper)
    debugImplementation(ThirdPartyDependencies.flipperNetwork) {
        exclude("com.squareup.okhttp3", "okhttp")
    }
    debugImplementation(ThirdPartyDependencies.flipperLeakCanary)
    debugImplementation(ThirdPartyDependencies.leakCanary)
    debugImplementation(ThirdPartyDependencies.soloader)

    // Test Dependency
    testImplementation(TestDependencies.jUnit)
    androidTestImplementation(TestDependencies.androidTest)
    androidTestImplementation(TestDependencies.espresso)
}