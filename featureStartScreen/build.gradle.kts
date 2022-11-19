plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.featurestartscreen"
//    compileSdk = Config.compileSdk
//
//    defaultConfig {
//        minSdk = Config.minSdk
//        targetSdk = Config.targetSdk
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles("consumer-rules.pro")
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = Config.javaVersion
//        targetCompatibility = Config.javaVersion
//    }
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
}

dependencies {
    //Modules
    implementation(project(Modules.core))

    //Kotlin
    implementation(Kotlin.core)

    //AndroidX
    implementation(Design.appcompat)

    //Design
    implementation(Design.material)

    //ViewBinding
    implementation(ViewBinding.viewBinding)

    //Navigation
    implementation(NavigationComponent.navigationFragment)
    implementation(NavigationComponent.navigationUi)

    //Tests
    testImplementation(TestImpl.junit)
    androidTestImplementation(TestImpl.testExt)
    androidTestImplementation(TestImpl.espresso)


}