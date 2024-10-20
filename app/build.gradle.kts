plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.example.logmeet"
    compileSdk = 35

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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    buildFeatures {
        viewBinding = true
        //noinspection DataBindingWithoutKapt
        dataBinding = true
        compose = true
    }
    packaging {
        resources {
            excludes += listOf(
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.md",
                "META-INF/NOTICE",
                "META-INF/NOTICE.md",
                "META-INF/ASL2.0"
            )
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.glide)
    implementation(libs.adapter.rxjava2)
    implementation(libs.kotlinx.serialization.json.io)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.preferences.rxjava2)
    implementation(libs.androidx.datastore.preferences.rxjava3)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.android.mail)
    implementation(libs.android.activation)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.core.splashscreen)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(platform("androidx.compose:compose-bom:2023.06.01"))
    // other dependencies
    // Compose
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.accompanist:accompanist-themeadapter-material3:0.28.0")
    debugImplementation("androidx.compose.ui:ui-tooling")

    //kakao
//    implementation(libs.kakao.sdk.all) // 전체 모듈 설치, 2.11.0 버전부터 지원
//    implementation(libs.kakao.sdk.user) // 카카오 로그인 API 모듈
//    implementation(libs.kakao.sdk.share) // 카카오톡 공유 API 모듈
//    implementation(libs.kakao.sdk.talk) // 카카오톡 채널, 카카오톡 소셜, 카카오톡 메시지 API 모듈
//    implementation(libs.kakao.sdk.friend) // 피커 API 모듈
//    implementation(libs.kakao.sdk.navi) // 카카오내비 API 모듈
//    implementation(libs.kakao.sdk.cert) // 카카오톡 인증 서비스 API 모듈
    implementation(kotlin("script-runtime"))

}