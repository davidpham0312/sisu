# Programming 3 project template

Group pr000g. The project is developed using VSCode. 

## Introduction

The program can be used to investigate the degree structures of study programmes in Tampere University. In addition the user’s progress over their studies can be viewed. The user can define their own study programme and mark courses completed to it as the studies go forward.

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
Make sure you have [Java](https://www.java.com/en/download/help/download_options.html), [JavaFX](https://openjfx.io/), [Maven](https://maven.apache.org/) installed. 

```
cd Sisu
mvn clean javafx:run
```

## Running tests
```
cd Sisu
mvn clean test
```
