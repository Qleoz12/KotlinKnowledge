# Kotlin Knowledge Repository

This repository contains a collection of Kotlin examples and solutions to various coding challenges, including REST API interactions, algorithms, and data structures. It serves as a knowledge base for Kotlin programming and problem-solving.

### HackerRank REST API Example

#### Description
This Kotlin example demonstrates how to interact with a REST API to solve a HackerRank challenge. The goal is to find the fastest runner of a specified gender in a given marathon by fetching and processing data from an external API.

Check the generated CSV file for detailed results.
#### Example Usage
```kotlin
fun main() {
    val result = fastestRunner("Cityscape Marathon", "female")
    println(result) // Outputs the fastest female runner
}
```
### CSV Export
The program exports runner data to a CSV file named using the current date, marathon name, and gender.

**Example Output Filename:**
```
marathon_results_2025-01-28_CityscapeMarathon_female.csv
```

#### Key Features
- Fetches paginated data from a REST API.
- Filters runners by marathon name and gender.
- Calculates the fastest runner based on race time.
- Exports results to a CSV file for further analysis.

#### Source Code
The implementation can be found in the following file:
- [HackerRankRestAPI.kt](https://github.com/Qleoz12/KotlinKnowledge/blob/main/src/main/kotlin/HackerRansRestAPi.kt)

### Example Input and Output

Ernest Littel
Vivian Steuber
#### Input
```kotlin
val marathon = "Cityscape Marathon"
val sex = "female"


Dependencies
java.net.URL and java.net.HttpURLConnection for HTTP requests.

com.google.gson.Gson for JSON parsing.

java.io.BufferedWriter and java.io.FileWriter for CSV export.

```

## Repository Structure
The repository is organized as follows:

```
KotlinKnowledge/
├── src/
│   └── main/
│       └── kotlin/
│           ├── HackerRansRestAPi.kt       # HackerRank REST API example
│           └── ...                        # Other Kotlin examples
├── README.md                              # This file
└── ...                                    # Additional files and directories
```

**Author:** [qleoz12](https://qleoz12.github.io/)   