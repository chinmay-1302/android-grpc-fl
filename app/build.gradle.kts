plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.protobuf")
}

android {
    namespace = "com.example.grpcdemo"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.grpcdemo"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.21.9"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.50.2"
        }
        create("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.3.0:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                // Correct way in Kotlin DSL
                create("grpc")
                create("grpckt")
            }
            task.builtins {
                create("java")
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // gRPC core
//    implementation("io.grpc:grpc-okhttp:1.50.2")
//    implementation("io.grpc:grpc-protobuf-lite:1.50.2")
//    implementation("io.grpc:grpc-stub:1.50.2")
//    implementation("io.grpc:grpc-kotlin-stub:1.3.0")
    // Protobuf lite
//    implementation("com.google.protobuf:protobuf-javalite:3.21.9")
//    implementation("com.google.protobuf:protobuf-kotlin-lite:3.21.9") // Kotlin API on top of lite

    // Protobuf runtime (full Java runtime, required for GeneratedMessageV3)
    implementation("com.google.protobuf:protobuf-java:3.25.3")

    // If you want Kotlin extensions (optional, helps with DSL-style usage)
    implementation("com.google.protobuf:protobuf-kotlin:3.25.3")

    // gRPC
    implementation("io.grpc:grpc-okhttp:1.63.0") // for Android transport
    implementation("io.grpc:grpc-protobuf:1.63.0")
    implementation("io.grpc:grpc-stub:1.63.0")

    // gRPC Kotlin stub
    implementation("io.grpc:grpc-kotlin-stub:1.4.1")

    // Coroutines (needed by grpc-kotlin)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}
