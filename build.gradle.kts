// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.gradleVersion}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.20")
        classpath(ClassPathPlugins.hilt)
        classpath(ClassPathPlugins.oss)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}