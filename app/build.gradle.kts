plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.crosoften.emnuvem"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.crosoften.emnuvem"
        minSdk = 26
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
    buildFeatures {
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    val koinAndroidVersion = "3.4.2"
    val navigationVersion = "2.7.2"
    val lifecycleVersion = "2.6.2"
    val retrofitVersion = "2.9.0"

    implementation ("androidx.core:core-ktx:1.12.0")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    //Navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation ("androidx.navigation:navigation-ui-ktx:$navigationVersion")

    //Lifecycle Extensions
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.15.1")

    //Koin
    implementation ("io.insert-koin:koin-android:$koinAndroidVersion")
    implementation ("io.insert-koin:koin-androidx-navigation:$koinAndroidVersion")

    // Retrofit
    implementation ("com.google.code.gson:gson:$retrofitVersion")
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.4.0")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofitVersion")
}