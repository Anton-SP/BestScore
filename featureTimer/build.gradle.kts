plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.bestscore.featuretimer"

}

dependencies {
    //Modules
    implementation(project(Modules.core))
    implementation(project(Modules.utils))

    //ViewBinding
    implementation(ViewBinding.viewBinding)

    //Fragment
    implementation(Fragment.fragmentKtx)

    //Coroutines
    implementation(Kotlin.coroutinesCore)
    implementation(Kotlin.coroutinesAndroid)

    //Kotlin
    implementation(Kotlin.core)

    //AndroidX
    implementation(Design.appcompat)

    //Design
    implementation(Design.material)

    //Tests
    testImplementation(TestImpl.junit)
    androidTestImplementation(TestImpl.testExt)
    androidTestImplementation(TestImpl.espresso)
}