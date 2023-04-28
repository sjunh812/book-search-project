plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = AppConfig.NAMESPACE
    compileSdk = AppConfig.COMPILE_SDK

    defaultConfig {
        applicationId = AppConfig.APPLICATION_ID
        minSdk = AppConfig.MIN_SDK
        targetSdk = AppConfig.TARGET_SDK
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME

        testInstrumentationRunner = AppConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        getByName("release") {
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

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(Library.AndroidX.CORE)
    implementation(Library.AndroidX.APPCOMPAT)
    implementation(Library.AndroidX.MATERIAL)
    implementation(Library.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Library.AndroidX.VIEWMODEL)
    implementation(Library.AndroidX.ACTIVITY_KTX)

    implementation(Library.Hilt.ANDROID)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    kapt(Library.Hilt.ANDROID_COMPILER)

    implementation(Library.ROOM.ROOM)
    implementation(Library.ROOM.ROOM_KTX)
    kapt(Library.ROOM.ROOM_COMPILER)

    implementation(Library.Network.OKHTTP)
    implementation(Library.Network.RETROFIT)
    implementation(Library.Network.CONVERTER_MOSHI)
    implementation(Library.Network.MOSHI)

    implementation(Library.Image.GLIDE)
    kapt(Library.Image.GLIDE_COMPILER)

    implementation(Library.JUNIT.JUNIT)

    androidTestImplementation(Library.AndroidX.ANDROID_JUNIT)
    androidTestImplementation(Library.AndroidX.ESPRESSO)
}