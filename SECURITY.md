# Security Policy for YGO-CardDex Jetpack Compose App

This application is developed using **Android Studio Ladybug**, the latest version at the time of development. Please ensure the following for compatibility and optimal performance:

## Android Studio Version
- **Version**: Android Studio **Ladybug**.
- Ensure your Android Studio is updated to the **Ladybug** version or newer to avoid compatibility issues with the project setup and dependencies.

## TOML-Based Catalog Version
- The app uses the **TOML (Tom's Obvious, Minimal Language) format** for dependency version management.  
- Verify that your `versionCatalogs` are configured correctly in the `libs.versions.toml` file, as this project relies on modern dependency management introduced in **Gradle 7.0+**.
- Keep your dependencies and catalog version in sync with the official Android Gradle Plugin (AGP) recommendations.

Maintaining up-to-date tools ensures security, stability, and access to the latest features for development.
