plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.example.logmeet"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.logmeet"
        minSdk = 29
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
    buildFeatures {
        viewBinding = true
        //noinspection DataBindingWithoutKapt
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //kakao
//    implementation(libs.kakao.sdk.all) // 전체 모듈 설치, 2.11.0 버전부터 지원
//    implementation(libs.kakao.sdk.user) // 카카오 로그인 API 모듈
//    implementation(libs.kakao.sdk.share) // 카카오톡 공유 API 모듈
//    implementation(libs.kakao.sdk.talk) // 카카오톡 채널, 카카오톡 소셜, 카카오톡 메시지 API 모듈
//    implementation(libs.kakao.sdk.friend) // 피커 API 모듈
//    implementation(libs.kakao.sdk.navi) // 카카오내비 API 모듈
//    implementation(libs.kakao.sdk.cert) // 카카오톡 인증 서비스 API 모듈

}