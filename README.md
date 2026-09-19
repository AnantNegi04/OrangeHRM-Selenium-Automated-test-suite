# OrangeHRM Selenium Automated Test Suite

A Selenium/Java test automation framework built against the [OrangeHRM demo application](https://opensource-demo.orangehrmlive.com/), using the Page Object Model, TestNG, and a CI pipeline that runs the suite on every push.

This is a self-directed learning project, not a company codebase — built without a formal requirements document or test matrix, deliberately treating an existing open-source HR application as if it were a real system under test. The goal was to practice real framework and test-design judgment, not just tool syntax.

## Tech Stack

| Layer | Tool |
|---|---|
| Language | Java 25 |
| Browser automation | Selenium WebDriver 4.45 |
| Test framework | TestNG 7.7 |
| Build | Maven |
| Reporting | ExtentReports 5.1.2 |
| CI | GitHub Actions |

## Architecture

The framework follows the Page Object Model, with a few deliberate structural choices worth calling out:

- **`BasePage`** centralizes the `WebDriver` and `WebDriverWait` every page object needs, so synchronization strategy lives in one place rather than being reimplemented per page.
- **`SideBar`** acts as a navigation hub with one generic `navigate(item)` method rather than a hardcoded method per menu item — every module is reachable through the same code path.
- **Dynamic, stateful components** (a multi-row editable timesheet, an autocomplete-driven leave-assignment form) are modeled with dedicated classes rather than flat scripts — e.g. `EditTimesheets` owns its rows by index and re-acquires them from the live DOM on every access, rather than holding a stale `WebElement` reference across a DOM re-render.
- **Toast/notification-based outcome checks** are used where the application itself provides an unambiguous success/failure signal (e.g. `oxd-toast--success` vs. `--warn` vs. `--error`), rather than inferring an outcome from the absence of a specific known failure — inference degrades silently the first time a failure mode you didn't anticipate shows up; checking for positive proof of success does not.

## Test Coverage

**Fully covered, in the active CI suite (`testng.xml`):**
- **Login** — happy path, invalid credentials, per-field required-field validation, boundary/injection-style input, CSRF token tampering
- **Sidebar navigation** — every application module, data-driven by route; search (with and without results, and from a collapsed sidebar state); the Maintenance module's distinct password-confirmation screen; brand logo navigation
- **Quick Launch** — navigation to all six dashboard shortcuts, plus a sequential same-session navigation test
- **Cross-page session continuity** — a single session navigating through multiple modules in sequence

**In progress, built but not yet merged into the primary suite:**
- **Leave — Assign Leave** — mandatory-field validation, date-range boundary and rejection cases, duplicate/overlap detection, and a cross-module integration test that assigns a leave record via `AssignLeave` and verifies/cleans it up via `LeaveList`
- **Leave List / My Leave** search and filtering

**Deliberately out of scope:**
Most remaining modules (Admin, PIM, Recruitment, Performance, Directory, Claim, Buzz, MyInfo) are reachable and covered at the navigation level via the Sidebar/Quick Launch suites, but do not yet have dedicated functional test classes. This is a scoping decision, not an oversight: the modules built out in depth (Login, Sidebar, Leave, Timesheet) were chosen to demonstrate a representative range of test-design challenges — static forms, dynamic multi-row state, conditional dialogs, cross-module data dependencies — rather than repeating the same page-object pattern across every remaining module for its own sake.

A few specific limitations are worth stating plainly rather than leaving implicit:
- The shared public demo instance gives every employee a permanent zero leave balance, so a *positive*, fully successful leave assignment always passes through an "insufficient balance" confirmation dialog — this is treated as the expected default path in this environment, not an edge case.
- Full positive-path verification of a leave record actually appearing in Leave List required building an assign-then-search-then-cancel flow, since the shared dataset offered no reliable way to create known ground truth otherwise.
- The application enforces a rolling one-year limit on how far in advance leave can be assigned; date generation in the test suite accounts for this but is not fully self-updating and may need periodic adjustment.

## CI/CD

Every push and pull request to `main` triggers a GitHub Actions workflow that checks out the repository, provisions JDK 25, runs the full suite via Maven (`mvn test`), and publishes both the ExtentReports HTML report and any failure screenshots as downloadable build artifacts — regardless of whether the run passed or failed.

## Reporting

Test execution is reported via a custom `TestNG` listener (`ExtentReports.TestListener`) that generates a self-contained HTML report per run, with the full exception stack trace attached to any failure and an automatic screenshot captured and linked at the moment of failure.

## Running Locally

```bash
mvn test
```

Requires JDK 25 and Maven. The suite runs headless Chrome by default (`--headless=new`); comment this out in `BaseTests.java` if you want to watch it run.

## Project Structure

```
src/main/java/pages/       — Page objects, organized by application module
src/test/java/             — Test classes, plus the ExtentReports listener
testng.xml                 — Active test suite definition
.github/workflows/ci.yml   — CI pipeline
```

## About

Built by [Anant Negi](https://github.com/AnantNegi04) — [LinkedIn](https://www.linkedin.com/in/anant-negi-5a0811401).
