plugins {
    alias(libs.plugins.comlib.android.library)
    alias(libs.plugins.comlib.android.library.compose)
    alias(libs.plugins.comlib.android.library.firebase)
    alias(libs.plugins.comlib.android.feature)
}

android {
    namespace = "com.githukudenis.comlib.feature.auth"


    defaultConfig {

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

}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:common"))
}