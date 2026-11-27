# Rajvi · Recovery Desk

An offline case manager for one recovery agent. Borrower records and case history stay in Android app-private storage. The app makes no network requests, requests no contacts/SMS/call-log access, and does not send messages or place calls. The launcher name is **Rajvi**.

## Build and install

Requires JDK 17+ and Android SDK platform/build tools 36.

```sh
./gradlew assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## Back up and import

Choose **Backup / Import** in the app. Export offers JSON through Android's share/save sheet; import opens a JSON file picker. Backups contain case fields and local media paths. Photo and document files are stored in the app's private folder and are not packaged into JSON, so attachments must be saved separately before uninstalling or clearing storage. Android automatic app backup is disabled.

## Privacy and use

Data is local in app-private storage; this is Android sandbox protection, not database encryption against rooted or compromised devices. Photos and PDFs selected for OCR are processed on-device; imported source files are copied to Rajvi's private app folder. Calls are never recorded. Speech dictation launches Android's recognizer and shows the transcript for review; recognizer processing may depend on the installed service and its settings.

The case category selector offers Unmapped, SMA 0, SMA 1, SMA 2, and NPA. SMA labels can be changed with Buckets and tapped on the dashboard to open matching unpaid cases. The borrower form no longer asks for lender-reported status; older NPA records are migrated into the NPA category. Unpaid cases appear in the dashboard, bucket tabs, PTP and visit lists, and the Unpaid cases page; Paid cases are kept separate behind the Paid cases button; All cases in the navigation drawer shows every payment status. Filters on Unpaid cases include category, visit status, overdue status, and PTP date (set, today, overdue, or next 7 days). The dashboard lists unpaid cases whose PTP date matches today. Bottom tabs open the dashboard and each of the three internal buckets; swipe in from the left or tap the top-left menu for unpaid and paid cases, PTP, visits, templates, bucket settings, backup, a confirmed clear-all-records action, a save-and-add-next batch workflow, record import from Excel, and PDF/Excel export with save and share choices. In the Add multiple records menu, Excel workbooks import case rows and PDF/photo scans use bundled, on-device OCR. OCR groups text by visual row and can create multiple records from a page or photo; it processes every page in a PDF and accepts repeated borrower names. A readable borrower name is still needed to create an OCR record. OCR skips a case when the same loan number, reference, phone number, or matching borrower identity already exists. Manual entry warns about a similar borrower name and lets the agent cancel or save it as another case. OCR may miss or misread data in blurred, angled, low-light, or tightly cropped images; review imported cases and correct details as needed. PDF/image contents are processed locally and are not uploaded. OCR source files are kept with records created from an import. Voice-note recording controls are not included; voice dictation for case fields remains available. Tapping a case shows Paid/Unpaid status and opens Call, New/Update PTP, Update visit status, View case history, and Edit full case details actions. The floating plus button opens the borrower form directly; choose a field and use Dictate selected field if needed. Case entry includes Paid/Unpaid status; selecting Paid reveals an optional paid-amount field. It also includes visit-status dropdowns, an internal SMA bucket selector, and calendar pickers for due, visit, and PTP dates. Bottom navigation labels use larger type and spacing. The app starts with no sample borrower records.

## Secure multi-device sync

This personal-use MVP has no sync or account system. Secure sync would require a reviewed backend, authenticated TLS, per-user authorization, encryption at rest and in transit, managed key recovery, audit logs, conflict handling, retention/deletion controls, and an explicit privacy/legal basis. A production app should also use a reviewed encrypted local database and a protected backup/export process.

The delivered debug APK is copied to `../outputs/rajvi.apk`.
