import org.gradle.api.JavaVersion

object Config {
    const val applicationId = "com.bestscore"
    const val compileSdk = 32
    const val minSdk = 24
    const val targetSdk = 32
    val javaVersion = JavaVersion.VERSION_1_8
}

object Releases {
    const val versionCode = 1
    const val versionName = "1.0"
}

object Modules {
    const val app = ":app"
    const val core = ":core"
    const val repository = ":repository"
    const val database = ":database"
    const val featureCreateTemplate = ":featureCreateTemplate"
    const val featureStartScreen = ":featureStartScreen"
    const val featureTemplateList = ":featureTemplateList"
    const val featureDice = ":featureDice"
    const val featureTimer = ":featureTimer"
    const val utils = ":utils"
    const val featurePlayGame = ":featurePlayGame"
}

object Versions {
    //Design
    const val appcompat = "1.5.1"
    const val material = "1.7.0"
    const val swipe = "1.1.0"

    //Kotlin
    const val core = "1.8.0"
    const val stdlib = "1.7.10"
    const val coroutinesCore = "1.6.4"
    const val coroutinesAndroid = "1.6.4"

    //Dagger2
    const val dagger = "2.43.2"

    //Coil
    const val coil = "2.1.0"

    //Room
    const val roomKtx = "2.4.3"
    const val runtime = "2.4.3"
    const val roomCompiler = "2.4.3"

    //ViewBinding
    const val viewBinding = "1.5.6"

    //Test
    const val jUnit = "4.13.2"
    const val runner = "1.2.0"
    const val espressoCore = "3.4.0"
    const val testExt = "1.1.3"

    //Navigation
    const val navigation = "2.5.1"

    //Fragment
    const val fragmentKtx = "1.5.4"
}

object Design {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val swipe = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipe}"
}

object Kotlin {
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.stdlib}"
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
}

object Dagger {
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
}

object Coil {
    const val coil = "io.coil-kt:coil:${Versions.coil}"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.runtime}"
    const val compiler = "androidx.room:room-compiler:${Versions.roomCompiler}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.roomKtx}"
}

object ViewBinding {
    const val viewBinding = "com.github.kirich1409:viewbindingpropertydelegate:${
        Versions.viewBinding
    }"
}

object TestImpl {
    const val junit = "junit:junit:${Versions.jUnit}"
    const val runner = "androidx.test:runner:${Versions.runner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val testExt = "androidx.test.ext:junit:${Versions.testExt}"
}

object NavigationComponent {
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
}

object Fragment {
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
}