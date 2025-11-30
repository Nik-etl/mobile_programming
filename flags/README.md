# Flags Gallery App

Android app displaying a scrollable gallery of country flags with names.

## Features

- Scrollable list of flag cards (LazyColumn)
- Flag image with country name
- Material 3 design with Cards
- Data source pattern for flag list

## Components

**FlagCard** - Card composable with flag image and country name

**FlagList** - Lazy scrollable list of flags

**Datasource** - Loads flag data (image + string resources)

**Flag** - Data model (imageResourceId, stringResourceId)

## Tech Stack

- Kotlin, Jetpack Compose
- LazyColumn for performance
- Material 3 Cards
- Resource management (drawables, strings)

## How to Run

1. Open in Android Studio
2. Add flag images to `res/drawable`
3. Add country names to `res/values/strings.xml`
4. Sync Gradle
5. Run on emulator or device

## License

Apache 2.0
