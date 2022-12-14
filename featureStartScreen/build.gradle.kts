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

    //Kotlin
    implementation(Kotlin.core)

    //AndroidX
    implementation(Design.appcompat)
    implementation(Design.swipe)


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