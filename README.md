# sportsEventTracker

## Overview

The Sports Event Tracker is an Android application built using modern technologies to fetch, display, and manage sports events. Users can view events grouped by sport, filter events by favorites, and see a countdown timer for each event, all within a user-friendly interface. The app is designed to support SDK 21 and above.

---

## Features

- **Event Fetching**: Retrieves a list of events from a remote API.
- **Event Grouping**: Events are grouped by sport type.
- **Countdown Timer**: Displays a real-time countdown for event start times.
- **Favorites Management**: Allows users to mark events as favorites and filter by favorites.
- **Expandable/Collapsible Sections**: Users can expand or collapse event lists by sport.
- **Error Handling**: Displays error messages for API failures or when no events are available.
- **Filtering**: Toggle favorite events per sport via a switch.
- **Persistent Favorites**: Favorites are saved locally using Room Database.

---

## Tech Stack

- **Kotlin**: Language for Android development.
- **Jetpack Libraries**:
    - **ViewModel**: For managing UI-related data.
    - **Room**: For local persistence of favorite events.
    - **ViewBinding**: Simplifies view access.
- **Retrofit**: For API calls.
- **Hilt**: Dependency injection.
- **Coroutines & Flows**: For asynchronous programming and state management.
- **RecyclerView**: For displaying grouped events.
- **Material Design**: For UI components.

---

## Architecture

The app uses **MVVM (Model-View-ViewModel)** and incorporates clean architecture principles. The application is divided into the following layers:

1. **Data Layer**: Handles API calls and database interactions.
    - **Repository**: Combines data sources (remote API and local database).
2. **Domain Layer**: Contains business logic and use cases.
3. **UI Layer**:
    - Includes ViewModels, Adapters, and the user interface.
    - **Adapters**: `SportsAdapter` and `MatchesAdapter` manage RecyclerView logic.
    - **State Management**: `UiState` models loading, success, and error states.
