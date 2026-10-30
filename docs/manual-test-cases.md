# Manual test cases

The test cases this framework automates, written as a tester would run them by hand. Every case has
an ID; the TestNG method that covers it starts with the same ID (`tc101_testSuccessfulLogin`) and so
does its description (`[TC-101] ...`), so a failure in the Extent report leads straight to the case
below.

The four cases run in **one browser session, in this order** (TestNG `priority`): each one starts
where the previous one ended, as a tester working through the flow would.

Not listed: `ReportDemoTest`, which fails and skips on purpose to show the report.

## Setup

- Application: OrangeHRM demo <https://opensource-demo.orangehrmlive.com> (public, shared).
- Login: `Admin` / `admin123` (`src/main/resources/config.properties`).

## Summary

| ID | Title | Group | Priority | Automated by |
|---|---|---|---|---|
| TC-101 | Admin logs in and lands on the dashboard | SmokeTest | High | `TestClass.tc101_testSuccessfulLogin` |
| TC-102 | Admin opens the Admin module from the side menu | SmokeTest | High | `TestClass.tc102_testAdmin` |
| TC-103 | Admin searches System Users by username and status, then resets | Regression | Medium | `TestClass.tc103_testSearchUsername` |
| TC-104 | User logs out through the user menu | Regression | High | `TestClass.tc104_testSuccessfulLogout` |

---

### TC-101 Admin logs in and lands on the dashboard

| # | Step | Expected result |
|---|---|---|
| 1 | Open the login page `.../web/index.php/auth/login` | Username field, password field and **Login** button shown and enabled |
| 2 | Enter `Admin` / `admin123`, click **Login** | URL `.../web/index.php/dashboard/index` |

### TC-102 Admin opens the Admin module from the side menu

Precondition: TC-101 done (dashboard open).

| # | Step | Expected result |
|---|---|---|
| 1 | Click **Admin** in the side menu (shown and enabled) | URL `.../web/index.php/admin/viewSystemUsers` |

### TC-103 Admin searches System Users by username and status, then resets

Precondition: TC-102 done (System Users open).

| # | Step | Expected result |
|---|---|---|
| 1 | Enter username `Admin` | Field shown and enabled |
| 2 | Open the **Status** list, choose **Enabled** | Option shown and selectable |
| 3 | Click **Search** | Results list `Admin` users with status Enabled |
| 4 | Click **Reset** | Filters cleared; still on `.../admin/viewSystemUsers` |

### TC-104 User logs out through the user menu

Precondition: logged in (TC-101).

| # | Step | Expected result |
|---|---|---|
| 1 | Open the user menu (top right, shown and enabled), click **Logout** | URL `.../web/index.php/auth/login` |
