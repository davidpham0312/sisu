# Programming 3 project template

Group pr000g. The project is developed using VSCode.

## Folder structure

```
.
├── Documentation                                # project documentation
├── json                                         # example JSON files provided by the course. The app does not need this to work.
├── Sisu                                         # The folder containing the application
│   ├── saved_information                        # Stores locally saved student info & progress
│   └── src                                      # Source folder
│       ├── main                                 
│       │   ├── java/fi/tuni/prog3               # Interfaces & classes used in the app
│       │   └── resources/fi/tuni/prog3          # FXML files (user interface)
│       └── test
│           ├── java/fi/tuni/prog3               # Test suits
│           └── resources                        # Sample JSON files for testing
├── .gitignore                                   # Git ignore file
├── .gitlab-ci.yml                               # GitLab pipeline runner provided by the course. Does not work.
└── README.md                                    # you're reading it
```
## Running instructions
Make sure you have Java, JavaFX, Maven, etc. installed. (We assumed this project will only be run by the course TAs, therefore we will not go into details of installing everything).

```
cd Sisu
mvn clean javafx:run
```

## Running tests
```
cd Sisu
mvn clean test
```
