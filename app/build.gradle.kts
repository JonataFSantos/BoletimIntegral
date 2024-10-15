plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")

    id("androidx.navigation.safeargs.kotlin")


}



android {
    namespace = "com.example.boletimintegral"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.boletimintegral"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding{
        enable=true
    }
    buildFeatures {
        viewBinding = true
    }




}

dependencies {

    //Dependencies Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Dependencies
    implementation(libs.material.v1130alpha06)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    //navigation fragment dependencies
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)


    // fragment
    implementation(libs.androidx.fragment.ktx)

    //FIREBASE DEPENDENCIES
    // Add the dependencies for any other desired Firebase products
    // https://fire base.google.com/docs/android/setup#available-libraries

    // Import the Firebase BoM
    implementation(platform(libs.firebase.bom))
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation(libs.firebase.analytics)
    //Authentication
    implementation(libs.firebase.auth)
    //Cloud Messaging
    implementation(libs.firebase.messaging)
    //Data base
    implementation(libs.firebase.firestore)
    //Clock
    implementation(kotlin("stdlib"))
    implementation(libs.kotlinx.datetime)
    //Analytics
    implementation (libs.firebase.analytics.ktx)





}