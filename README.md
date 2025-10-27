1 . 🧪 Coroutine Testing in Android with Jetpack Compose

Overview

This repository showcases a comprehensive approach to unit testing asynchronous logic built with Kotlin Coroutines within an Android application using Jetpack Compose.

The core focus is on implementing robust and reliable tests for MVVM ViewModels that handle coroutines, using standard practices like TestDispatcher and libraries like Turbine to assert the emissions of StateFlows and SharedFlows.

The accompanying video, rec.webm, demonstrates the fully functional app whose underlying logic is rigorously tested in this project.

📺 Demo Video: resources/rec.webm

This screen recording showcases the resulting user experience of the fully tested application logic.

<div align="center">
<!-- GitHub supports HTML tags for better control over media embedding -->
<video src="./resources/rec.webm" controls autoplay muted loop width="100%" style="max-width: 768px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);">
Your browser does not support the video tag.
</video>
</div>

Highlights in the Demo

The video primarily shows the functional, real-time aspects of the app, which are guaranteed by the strong test coverage:

Instant UI Recomposition: Observe how data changes, triggered by underlying asynchronous work, immediately update the Compose UI.

State Management: The application state remains consistent and predictable, a direct result of tested ViewModel logic.

Responsive Design: The Compose layout adapts smoothly to configuration changes.

🛠 Technology Stack

The project adheres to modern Android development standards, with a strong emphasis on testability.

Language: Kotlin

UI Toolkit: Jetpack Compose

Architecture: MVVM (Model-View-ViewModel)

2. 🎨 Jetpack Compose Canvas Demo

A modern Android demo showcasing how to **draw, animate, and visualize custom graphics** using the **Jetpack Compose Canvas API** — written entirely in **Kotlin**.  
This project demonstrates how to create dynamic and animated visuals without XML layouts, ideal for learning and experimenting with low-level drawing in Compose.

---

## 🧠 Overview

This demo illustrates:

- Drawing **basic shapes** — circles, rectangles, and lines  
- Using **Path** for custom curves and wave patterns  
- Applying **linear gradients** for stylish backgrounds  
- Adding **text** directly to Canvas  
- Animating objects with `rememberInfiniteTransition`  
- Combining Canvas drawings with modern Compose UI elements  

Perfect for developers exploring **custom UI**, **game effects**, **data visualization**, or **animated art** in Compose.

---

## 🧩 Tech Stack

| Component | Description |
|------------|-------------|
| **Language** | Kotlin |
| **UI Toolkit** | Jetpack Compose |
| **Animation** | `rememberInfiniteTransition`, `tween`, `infiniteRepeatable` |
| **Graphics** | `Canvas`, `Path`, `Brush.linearGradient` |
| **IDE** | Android Studio Iguana or newer |

---

⚡ Getting Started

To explore the testing implementation locally, follow these steps:

Clone the Repository:

git clone https://github.com/Than-Zaw-Hein/ComposeTutorial.git


Open in Android Studio:
Navigate to the project root and open the folder in Android Studio (Jellyfish or newer recommended).

Sync and Build:
Allow Gradle to sync and build the project dependencies.
