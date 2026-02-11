#!/bin/bash

# Stock Analysis App - Build Script
# Usage: ./build.sh [debug|release]

set -e

echo "📱 Building Stock Analysis App..."

# Check if Android SDK is available
if [ -z "$ANDROID_HOME" ]; then
    echo "⚠️  ANDROID_HOME not set. Please set ANDROID_HOME to your Android SDK path."
    echo "   Example: export ANDROID_HOME=/opt/android-sdk"
    exit 1
fi

# Build type
BUILD_TYPE=${1:-debug}

echo "🔧 Build type: $BUILD_TYPE"

# Clean build
echo "🧹 Cleaning previous builds..."
./gradlew clean

# Build
echo "⚙️  Building APK..."
if [ "$BUILD_TYPE" = "release" ]; then
    echo "📦 Building release APK..."
    ./gradlew assembleRelease
    APK_PATH="app/build/outputs/apk/release/app-release.apk"
else
    echo "🐛 Building debug APK..."
    ./gradlew assembleDebug
    APK_PATH="app/build/outputs/apk/debug/app-debug.apk"
fi

# Check if APK was created
if [ -f "$APK_PATH" ]; then
    echo "✅ Build successful!"
    echo "📄 APK location: $APK_PATH"
    APK_SIZE=$(du -h "$APK_PATH" | cut -f1)
    echo "📦 APK size: $APK_SIZE"
    echo ""
    echo "🚀 To install on device:"
    echo "   adb install -r $APK_PATH"
else
    echo "❌ Build failed! APK not found at $APK_PATH"
    exit 1
fi
