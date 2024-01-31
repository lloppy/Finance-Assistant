# MoneyCraft

## Version: 1.2 [Google drive](https://drive.google.com/drive/folders/1Jy1kMbPBR5o22QhU-c_1MuGSR9eUFC_h?usp=sharing)
## See: [Appetize link](https://appetize.io/app/qd2gn4n43stdektcuzm2kwbfo4)

### Description

**MoneyCraft** is your reliable and convenient financial assistant, designed to control your finances without wasting your time. 
The application provides convenient features:

- Tracking income and expenses
- Create personal budgets
- Spending analysis with selection of profitable MCC category

- Full functionality at [link](https://mm.tt/app/map/3116855325?t=QiOMPbhAsV)

### Features

- Add expenses using QR code of receipt
- Automatically read SMS notifications from banks about transactions
- Uploading bank statements (in CSV format, e.g. from Tinkoff), with automatic card creation
- Chat-bot providing analysis of spending and receipts

### Installation

To install the app, follow the steps below:

1. Open Google drive at [Google drive](https://drive.google.com/drive/folders/1Jy1kMbPBR5o22QhU-c_1MuGSR9eUFC_h?usp=sharing)
2. Download the "Moneycraft" file.
3. click "Install".



#### Learn more about technology

```
The application was written in Kotlin Compose in Android Studio.

The main technologies used in the app are:
for entering transactions using QR codes, the app uses android's CameraX library to run the camera and Zxing for QR codes: Zxing recognizes the image by converting it into a byte array and by decoding the binary image, it extracts the transaction data.

When a user scans a check or reads data from a CSV statement, the MCC category is automatically added as well. The MCC code (Merchant category code) is a standardized code for the service the payee is providing. This code is used by the bank to determine which category of goods the customer has purchased. This allows us to make a report on the chat screen and display MCC-categories, which are the most favorable for the selection of cashback. MCC categories are read through a regular expression.

To process fingerprint and implement biometric authentication, the app uses third party android library biometric. Using BiometricPrompt, I display a dialog box and process the results in BiometricPrompt.AuthenticationCallback. After successful processing, the main application screen is launched.
For user data security, internal android storage is used to store the receipts and spending data and Shared Preferences for password and the percentage that the user is saving (spending goal).

Since all the information about receipts and spending data is stored in the internal storage, I needed the Coil library to load images in the background thread. It provides a convenient API for loading images from a variety of sources, including the network, local storage, and application resources. Using AsyncImage - a Jetpack Compose widget that allows you to display images loaded using coil - and load an image from a URI in the background stream that is stored in the internal memory of the user's phone and display it on the screen.

Also, when the app is running, it reads all incoming SMS (the user can choose not to allow the app to read, the feature is disabled by default). To extract the data, regular expressions were written to process the incoming data.

To read bank statements, BufferedReader is used, opening and reading the file in a new thread, so the application does not stop or lag. RegEx regular expressions are also used to read data from a CSV file.

Also made custom design for calendars from android widget library, which are used to create cards of spending and receipts, and for displaying statistics - animation from compose animation library.

```

### Support

If you have any questions or problems with the app, feel free to contact [ankudinovapol@gmail.com](mailto:ankudinovapol@gmail.com).

Thank you for choosing the **MoneyCraft** application!
