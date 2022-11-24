plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.bestscore.featurestartscreen"

}

dependencies {
    //Modules
    implementation(project(Modules.core))
    implementation(project(Modules.utils))
    implementation(project(Modules.database))
    implementation(project(Modules.repository))
    implementation(project(Modules.utils))

    //Kotlin
    implementation(Kotlin.core)

    //AndroidX
    implementation(Design.appcompat)
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")


    //Design
    implementation(Design.material)

    //ViewBinding
    implementation(ViewBinding.viewBinding)

    //Fragment
    implementation(Fragment.fragmentKtx)

    //Coroutines
    implementation(Kotlin.coroutinesCore)
    implementation(Kotlin.coroutinesAndroid)

    //Dagger2
    implementation(Dagger.dagger)
    kapt(Dagger.daggerCompiler)

    //Navigation
    implementation(NavigationComponent.navigationFragment)
    implementation(NavigationComponent.navigationUi)

    //Tests
    testImplementation(TestImpl.junit)
    androidTestImplementation(TestImpl.testExt)
    androidTestImplementation(TestImpl.espresso)


}