# Mobile Test Automation Framework

[![Build](https://github.com/JunLong-stack/mobile-test-automation-framework-demo/actions/workflows/build.yml/badge.svg)](https://github.com/JunLong-stack/mobile-test-automation-framework-demo/actions/workflows/build.yml)
[![E2E](https://github.com/JunLong-stack/mobile-test-automation-framework-demo/actions/workflows/e2e.yml/badge.svg)](https://github.com/JunLong-stack/mobile-test-automation-framework-demo/actions/workflows/e2e.yml)
[![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)](https://openjdk.org/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-blue?logo=apachemaven)](https://maven.apache.org/)
[![Appium](https://img.shields.io/badge/Appium-2.x-purple?logo=appium)](https://appium.io/)
[![Cucumber](https://img.shields.io/badge/Cucumber-7.15-23D96C?logo=cucumber)](https://cucumber.io/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A lightweight, scalable Android UI test automation framework built with **Java 21**, **Appium**, **Cucumber**, and the **JUnit 5 Platform**. Designed to demonstrate maintainable mobile-automation engineering — clear separation of concerns, reusable utilities, config-driven setup, and parallel-ready execution — rather than test depth on a single app.

The framework drives the native Android **Settings** app on an emulator as a realistic, dependency-free target.

---

## Table of Contents

- [Highlights](#highlights)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Test Coverage](#test-coverage)
- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Running Tests](#running-tests)
- [Reports & Screenshots](#reports--screenshots)
- [Design Decisions](#design-decisions)
- [Roadmap](#roadmap)
- [License](#license)

---

## Highlights

- **Page Object Model** with clean separation between pages, steps, and infrastructure
- **Config-driven** setup — no hardcoded device names, URLs, or timeouts
- **Dynamic locators** built from BDD step parameters (search term, scroll target)
- **Explicit-wait utilities** — no `Thread.sleep` anywhere
- **Screenshot on failure** auto-captured and **embedded into the Cucumber HTML report**
- **BDD** scenarios in Gherkin, executable through JUnit + Maven Surefire

---

## Tech Stack

| Layer            | Tool                                |
| ---------------- | ----------------------------------- |
| Language         | Java 21                             |
| Build            | Maven                               |
| Mobile driver    | Appium 2.x (UiAutomator2) — `java-client` 9.4 |
| BDD              | Cucumber 7.22                       |
| Test runner      | JUnit 5 Platform Suite + Maven Surefire |
| Reporting        | Allure 2.29 + Cucumber HTML         |
| Logging          | SLF4J + Logback                     |
| Static analysis  | Checkstyle                          |
| CI               | GitHub Actions (build, validate, emulator E2E) |
| Target platform  | Android Emulator (API 30+)          |
| Element insight  | Appium Inspector                    |

---

## Architecture

```
src/test/java
├── base              # Thread-safe AndroidDriver lifecycle (ThreadLocal)
│   └── DriverFactory.java
├── pages             # Page objects (one class per screen)
│   └── SettingsPage.java
├── stepdefinitions   # Cucumber glue — hooks + step definitions
│   ├── Hooks.java              # @Before init, @After teardown + screenshot
│   └── SettingSteps.java
├── runner            # JUnit 5 @Suite entry point for the Cucumber engine
│   └── TestRunner.java
└── utils             # Cross-cutting helpers
    ├── ConfigReader.java       # loads config.properties
    ├── WaitUtils.java          # explicit-wait wrappers
    └── ScreenshotUtils.java    # capture + embed on failure

src/test/resources
├── features
│   └── settings-navigation.feature
└── config.properties
```

**Design intent:**

- **`DriverFactory`** owns the Appium driver lifecycle via a `ThreadLocal<AndroidDriver>` — one driver per thread, ready for parallel scenario execution.
- **`Hooks`** wraps each scenario in `@Before` (init driver) / `@After` (teardown + failure screenshot), keeping step definitions free of boilerplate.
- **Page objects** know *how* to interact with the UI; they expose intent-revealing methods (`openNetworkAndInternet`, `searchFor`, `scrollTo`) and pull the current driver from `DriverFactory` — no inheritance from test bases.
- **Step definitions** stay thin — they translate Gherkin into page-object calls and assertions, nothing more.
- **Utilities** are stateless and reusable across pages.

---

## Test Coverage

The current feature file demonstrates three distinct interaction patterns:

| # | Scenario                          | Pattern Demonstrated                  |
| - | --------------------------------- | ------------------------------------- |
| 1 | Navigate to a settings category   | Element click + screen-state assert   |
| 2 | Search for a settings option      | Parameterized step + dynamic locator  |
| 3 | Scroll to and open hidden setting | `UiScrollable.scrollIntoView` pattern |

All three are tagged `@e2e @android` and run via the same Cucumber runner.

---

## Prerequisites

| Requirement       | Version / Notes                              |
| ----------------- | -------------------------------------------- |
| JDK               | 21 (release target set in `pom.xml`)         |
| Maven             | 3.9+                                         |
| Node.js           | 18+ (required for Appium)                    |
| Appium Server     | 2.x — `npm install -g appium`                |
| UiAutomator2      | `appium driver install uiautomator2`         |
| Android SDK       | platform-tools + an emulator (API 30+ tested)|
| Emulator running  | Listening on `emulator-5554` (default)       |

---

## Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/JunLong-stack/mobile-test-automation-framework-demo.git
   cd mobile-test-automation-framework-demo
   ```

2. **Install dependencies**
   ```bash
   mvn dependency:resolve
   ```

3. **Adjust configuration** in `src/test/resources/config.properties` if your emulator name or Appium URL differs:
   ```properties
   platformName=Android
   automationName=UiAutomator2
   deviceName=emulator-5554
   appiumServerUrl=http://127.0.0.1:4723
   appPackage=com.android.settings
   appActivity=.Settings
   implicitWait=12
   explicitWait=12
   ```

4. **Start the Android emulator** (any AVD with API 30+).

5. **Start the Appium server** in a separate terminal:
   ```bash
   appium
   ```

---

## Running Tests

Run the full suite (compiles + executes the Cucumber runner):

```bash
mvn clean test
```

Run a subset by Cucumber tag:

```bash
mvn clean test -Dcucumber.filter.tags="@e2e and @android"
```

### Parallel Execution

Runtime options live in [`src/test/resources/junit-platform.properties`](src/test/resources/junit-platform.properties) — glue, tag filter, report plugins, and parallel settings.

The framework is **parallel-ready**: `DriverFactory` keeps one `AndroidDriver` per thread (`ThreadLocal`), so scenarios are thread-confined. Parallel execution is **off by default** so the suite runs out-of-the-box against a single emulator — multiple UiAutomator2 sessions on one device cannot initialise their instrumentation simultaneously.

On a multi-device setup, opt in at runtime and match the parallelism to the number of connected devices:

```bash
mvn clean test \
  -Dcucumber.execution.parallel.enabled=true \
  -Dcucumber.execution.parallel.config.fixed.parallelism=3
```

---

## Reports & Screenshots

After a test run, find:

| Artifact            | Location                                                |
| ------------------- | ------------------------------------------------------- |
| Allure raw results  | `target/allure-results/`                                |
| Allure HTML report  | `target/allure-report/index.html` (after `mvn allure:report`) |
| Cucumber HTML       | `target/cucumber-reports/cucumber.html`                 |
| Cucumber JSON       | `target/cucumber-reports/cucumber.json`                 |
| Surefire XML        | `target/surefire-reports/`                              |
| Failure screenshots | `target/screenshots/`                                   |

Screenshots are captured automatically in the Cucumber `@After` hook **only when a scenario fails**, then attached to **both** the Cucumber HTML report (`scenario.attach(...)`) and the Allure report (`Allure.addAttachment(...)`) so they render inline in either viewer.

### Viewing the Allure Report

```bash
mvn clean test            # produces target/allure-results/
mvn allure:report         # generates target/allure-report/index.html
# Or, to open a live server in your browser:
mvn allure:serve
```

> _Sample report screenshot can be added at `docs/sample-report.png` and referenced here._

### Continuous Integration

Two workflows run on every push:

- **Build** — `mvn verify` (no emulator), a Cucumber dry-run that proves every step is glued, and Checkstyle.
- **E2E** — boots an **API 33 Android emulator**, starts Appium, and runs the full Cucumber suite against the real Settings app. The Allure results, Cucumber HTML report, and Appium log are uploaded as downloadable artifacts on each run — open the latest [E2E run](https://github.com/JunLong-stack/mobile-test-automation-framework-demo/actions/workflows/e2e.yml) and grab them from the **Artifacts** section.

---

## Design Decisions

### Locator Strategy

Locator priority used across the page objects:

1. **`resource-id`** — when stable and meaningful (e.g. `com.android.settings:id/search_action_bar_title`)
2. **Accessibility id**
3. **`UiSelector().text` / `textContains`** — used liberally because many Android Settings IDs are generic (`:id/title`) and not reliable
4. **XPath** — avoided; used only as a last resort

### Why no `Thread.sleep`

All synchronization goes through `WaitUtils.waitForVisible` / `waitForClickable`, which wrap `WebDriverWait` + `ExpectedConditions`. Timeouts are configured in `config.properties`, not buried in code.

### Why the `Settings` app

It ships with every Android emulator, has no licensing or APK distribution concerns, and exposes enough surface area (navigation, search, scroll, dynamic content) to exercise the framework realistically.

---

## Roadmap

- [ ] Add a sample iOS configuration to demonstrate cross-platform structure

---

## License

[MIT](LICENSE) © 2026 Hoang Anh
