# 💬 Communication Application

An Android chat and communication application built with **Java** and powered by **Firebase**. The app enables real-time messaging and user communication through Firebase's backend services.

---

## 📱 Screenshots

> _Add screenshots of your app here_

---

## 🚀 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java |
| Platform | Android |
| Backend / Database | Firebase (Realtime Database / Firestore) |
| Authentication | Firebase Authentication |
| Build System | Gradle (Kotlin DSL) |
| IDE | Android Studio |

---

## ✨ Features

- User registration and login via Firebase Authentication
- Real-time messaging between users
- Message delivery powered by Firebase backend
- Clean and responsive Android UI

---

## 📁 Project Structure

```
CommunicationApplication/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/          # Java source files (Activities, Adapters, Models)
│   │   │   ├── res/           # Layouts, drawables, strings
│   │   │   └── AndroidManifest.xml
│   │   └── test/              # Unit tests
│   └── build.gradle.kts       # App-level dependencies
├── build.gradle.kts           # Project-level build config
├── settings.gradle.kts
└── gradle.properties
```

---

## ⚙️ Prerequisites

- [Android Studio](https://developer.android.com/studio) (latest stable)
- Android SDK (API 21+)
- A [Firebase](https://console.firebase.google.com/) project with:
  - Authentication enabled (Email/Password or Google Sign-In)
  - Realtime Database or Firestore configured

---

## 🛠️ Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/abRaq45/CommunicationApplication.git
```

### 2. Open in Android Studio

Open the project using **File → Open** and select the cloned folder.

### 3. Connect Firebase

1. Go to the [Firebase Console](https://console.firebase.google.com/) and create a new project.
2. Add an Android app with your package name.
3. Download the `google-services.json` file.
4. Place it in the `app/` directory:

```
app/
└── google-services.json   ← place it here
```

### 4. Enable Firebase Services

In the Firebase Console:
- **Authentication** → Enable your preferred sign-in method (Email/Password, Google, etc.)
- **Realtime Database** or **Firestore** → Create a database and set up security rules

### 5. Run the app

Connect a physical device or start an emulator, then click **Run ▶** in Android Studio, or use:

```bash
./gradlew installDebug
```

---

## 🔒 Security

- `google-services.json` is excluded from version control via `.gitignore` — never commit this file publicly.
- Set proper Firebase Security Rules to restrict database read/write access to authenticated users only.

**Example Realtime Database rule:**
```json
{
  "rules": {
    ".read": "auth != null",
    ".write": "auth != null"
  }
}
```

---

## 🧪 Running Tests

```bash
./gradlew test
```

---

## 🏗️ Build

```bash
# Debug APK
./gradlew assembleDebug

# Release APK
./gradlew assembleRelease
```

The output APK will be located at `app/build/outputs/apk/`.

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -m 'Add your feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a Pull Request

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
