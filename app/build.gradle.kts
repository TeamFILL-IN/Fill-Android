plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version Versions.kotlinVersion
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("com.google.android.gms.oss-licenses-plugin")
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
        create("release") {
            keyAlias = "fillin"
            keyPassword = "fillinandroid"
            storeFile = File("${project.rootDir.absolutePath}/keystore/releasekey.jks")
            storePassword = "fillinandroid"
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
        }
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
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
    implementation(KotlinDependencies.serialization)

    // AndroidX
    implementation(AndroidXDependencies.coreKtx)
    implementation(AndroidXDependencies.appCompat)
    implementation(AndroidXDependencies.constraintLayout)
    implementation(AndroidXDependencies.startup)
    implementation(AndroidXDependencies.hilt)
    kapt(KaptDependencies.hiltCompiler)
    implementation(AndroidXDependencies.fragment)
    implementation(AndroidXDependencies.legacy)
    implementation(AndroidXDependencies.security)
    implementation(AndroidXDependencies.coroutines)
    implementation(AndroidXDependencies.lifeCycleKtx)
    implementation(AndroidXDependencies.lifecycleJava8)
    implementation(AndroidXDependencies.pagingRuntime)
    implementation(AndroidXDependencies.splashScreen)

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
    implementation(ThirdPartyDependencies.dotsIndicator)
    implementation(ThirdPartyDependencies.kotlinSerializationConverter)

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