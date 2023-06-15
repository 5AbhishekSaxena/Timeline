<img src="https://github.com/5AbhishekSaxena/Timeline/assets/19958130/0c8ba8c7-2133-463c-865f-cc4b6f0c4eb3" width="100%"/>

# Timeline
<img src="https://androidweekly.net/issues/issue-574/badge">

The Timeline project is an Android application designed to help users track and manage their events, add tags to categorize them, and filter events using queries and tags. It aims to provide a convenient way for users to quickly record and recall important moments in their lives.

In our fast-paced lives, it's easy to forget the small but significant events that happen around us. This application addresses that challenge by offering an intuitive and user-friendly interface to capture and organize day-to-day moments effectively.

### Key Features
- **Event Tracking:** Users can easily record and track their events, ensuring they don't miss out on capturing important moments.
- **Tagging System:** The application allows users to add tags to their events, enabling them to categorize and organize events based on specific themes or subjects.
- **Filtering and Querying:** Users can leverage the app's powerful filtering and querying capabilities to search for specific events based on tags or custom queries.
- **Quick Recall:** With all events conveniently stored in the app, users can easily recall and revisit their past moments with just a few taps.

## List of Key Libraries
The project utilizes the following key libraries:

| Type/Used For        | Name                                 |
|----------------------|--------------------------------------|
| UI                   | [Jetpack Compose](https://developer.android.com/jetpack/compose?gclid=CjwKCAjwyeujBhA5EiwA5WD7_eZSufXmYD62eB95VcYtKOrGuM1HjAqtcAMnXA8UcmJvnW_8qvPHWxoCSzAQAvD_BwE&gclsrc=aw.ds)                      |
| Dependency Injection | [Hilt](https://developer.android.com/jetpack/androidx/releases/hilt)                                 |
| Navigation           | [Compose Destinations](https://github.com/raamcosta/compose-destinations)                 |
| Data Persistence     | [Room](https://developer.android.com/jetpack/androidx/releases/room)                                 |
| Language             | [Kotlin](https://kotlinlang.org/)                               |


## Architecure
<img src="https://github.com/5AbhishekSaxena/Timeline/assets/19958130/399c5640-7349-405d-959a-3cedaed5c21e" height="100%"/>

The project follows the MVVM (Model-View-ViewModel) architectural pattern, leveraging the benefits of modern Android development tools:

- **View:** The View layer is built using Jetpack Compose, an innovative UI toolkit that provides a declarative approach to building user interfaces. Jetpack Compose simplifies the UI implementation and enhances the overall user experience.

- **ViewModel:** The ViewModel acts as a mediator between the View and the underlying data. It manages the UI-related data and state, ensuring separation of concerns. The ViewModel provides the necessary data to the View and handles user interactions, while abstracting away the complexity of data retrieval and manipulation.

- **UseCase:** The UseCase encapsulates the application's business logic and defines the operations and use cases that the application supports. It orchestrates the flow of data between the ViewModel and the Repository, ensuring the correct handling of events and tags.

- **Repository:** The Repository serves as a single source of truth for the application's data. It abstracts the data sources, such as a local database or remote API, and provides a clean API for data retrieval, storage, and manipulation. The Repository communicates with the DataSource to handle the low-level details of data management.

- **DataSource:** The DataSource represents the various data sources used by the Repository. It can include a local database for persistent storage of events and tags, as well as remote APIs for synchronization or backup purposes. The DataSource handles the specific data operations required by the application.

## Installation
To run the project locally, follow these steps:

1. Clone the repository: git clone https://github.com/5AbhishekSaxena/Timeline.git
2. Open the project in Android Studio.
3. Build and run the project on an Android emulator or physical device.

## Contribution Guidelines
Thank you for considering contributing to the Timeline project! To ensure a smooth collaboration, please follow these guidelines:

1. Fork the repository and clone it to your local machine.
2. Create a new branch for your feature or bug fix: `git checkout -b T-<issue#>/branch_name`. Example: `T-1/setup_version_catalog`
3. Make your changes and test them thoroughly.
4. Commit your changes: `git commit -m "Your detailed description of changes"`.
5. Push to the branch: `git push origin T-<issue#>/branch_name`.
6. Open a pull request in this repository, explaining your changes and their purpose.

Please adhere to the architecture, code style and naming convention of the existing componnets while contributing.

## License
The Timeline project is licensed under the Apache 2.0 License. See the [LICENSE](LICENSE) file for more details.

## Conclusion
The Timeline project is an application designed to help users track and manage their events effortlessly. By leveraging the power of tags, filtering, and querying, users can easily categorize and recall their day-to-day moments with ease. The project follows the MVVM architectural pattern and utilizes various key libraries to enhance the development experience and deliver a robust application.
