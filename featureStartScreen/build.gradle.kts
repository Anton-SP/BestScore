plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.featurestartscreen"
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = Config.javaVersion
        targetCompatibility = Config.javaVersion
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(Kotlin.core)
    implementation(Design.appcompat)
    implementation(Design.material)
    implementation(ViewBinding.viewBinding)
    implementation(project(mapOf("path" to ":featureCreateTemplate")))
    testImplementation(TestImpl.junit)
    androidTestImplementation(TestImpl.testExt)
    androidTestImplementation(TestImpl.espresso)
}