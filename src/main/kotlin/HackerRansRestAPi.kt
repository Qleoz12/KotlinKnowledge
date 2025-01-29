import java.io.*
import kotlin.collections.*
import kotlin.io.*
import kotlin.jvm.*
import kotlin.text.*
import java.net.*
import com.google.gson.*
import java.text.SimpleDateFormat
import java.util.*

/**
<div class="ps-content-wrapper-v0">
<p>
Given a marathon name and a gender, find the fastest runner of that gender in the marathon.
Use HTTP GET requests to access a marathon runner database at the URL
<em>https://jsonmock.hackerrank.com/api/marathon</em>. The query result is paginated and can
be further accessed by appending to the query string <code>&amp;page={num}</code>, where
<code>{num}</code> is the page number.
</p>

<p>
To filter the query based on specific fields, append <code>&lt;fieldname&gt;=value</code> to
the URL. For example: <code>https://jsonmock.hackerrank.com/api/marathon?sex={sex}</code>
filters based on gender.
</p>

<h3>Query Response Structure</h3>
<p>The query response from the API includes the following fields:</p>
<ul>
<li><strong>page:</strong> The current page number.</li>
<li><strong>per_page:</strong> The maximum results per page.</li>
<li><strong>total:</strong> The total number of records.</li>
<li><strong>total_pages:</strong> The total number of pages for the query.</li>
<li><strong>data:</strong> An array of JSON objects containing runner information.</li>
</ul>

<h3>Runner Information</h3>
<p>Each object in the <code>data</code> field includes the following:</p>
<ul>
<li><strong>name:</strong> The name of the runner.</li>
<li><strong>marathon_name:</strong> The name of the marathon the runner participated in.</li>
<li><strong>sex:</strong> The sex of the marathon runner.</li>
<li><strong>average_speed:</strong> The average speed of the runner in kilometers per hour.</li>
<li><strong>distance_run:</strong> The distance run by the runner in kilometers.</li>
<li>Other details that are not relevant to this question.</li>
</ul>

<h3>Example Record</h3>
<p>For example, a record from the API might look like this:</p>
<pre><code>{
"name": "Gene Zboncak-Hansen",
"sex": "female",
"id": "867b0d8c-2b23-4e7e-aabc-5747f67969f1",
"age": 99,
"top_speed": 13.42,
"bottom_speed": 4.75,
"average_speed": 7.01,
"avgheartbeat": 114,
"distance_run": 18.066,
"marathon_name": "Sunset Serenity Marathon",
"country": "Falkland Islands (Malvinas)",
"number_of_participants": 40,
"personal_best_time": 2914,
"stops_taken": 5,
"position": 40
}</code></pre>

<h3>Fastest Runner Calculation</h3>
<p>
The fastest runner is determined based on who takes the least time to run the marathon. This
time is calculated as:
<code>time = distance_run / average_speed</code>. The result should be rounded to 2 decimal
places before making comparisons. If two runners have the same time, return the one whose
name comes earlier in lexicographical order.
</p>

<h3>Function Description</h3>
<p>
Complete the function <code>fastestRunner</code> in the editor below. It has the following
parameters:
</p>
<ul>
<li><code>string marathon:</code> The name of the marathon.</li>
<li><code>string sex:</code> The gender to filter the runners by.</li>
</ul>

<h3>Returns</h3>
<p>
<code>string:</code> The name of the fastest runner of the given gender in the specified
marathon.
</p>

<h3>Additional Notes</h3>
<p>
Please review the header in the code stub to see available libraries for API requests in the
selected language. Required libraries can be imported to solve the question. Check the full
list of supported libraries at
<a href="https://www.hackerrank.com/environment">https://www.hackerrank.com/environment</a>.
</p>

<details>
<summary>Input Format For Custom Testing</summary>
<p>The first line contains a string, <code>marathon</code>.</p>
<p>The second line contains a string, <code>sex</code>.</p>
</details>

<details open="opened">
<summary>Sample Case 0</summary>
<h4>Sample Input For Custom Testing</h4>
<pre>Cityscape Marathon
female</pre>
<h4>Sample Output</h4>
<pre>Gretchen Cummings</pre>
<h4>Explanation</h4>
<p>
Gretchen Cummings is the fastest female runner in the Cityscape Marathon, with a time of
1.81 hours (rounded to 2 decimal places).
</p>
</details>

<details>
<summary>Sample Case 1</summary>
<h4>Sample Input For Custom Testing</h4>
<pre>Cityscape Marathon
male</pre>
<h4>Sample Output</h4>
<pre>Lucas Rau</pre>
<h4>Explanation</h4>
<p>
Lucas Rau took 1.91 hours to finish the race, making him the fastest male runner in the
Cityscape Marathon.
</p>
</details>
</div>


*/

/*
 * Complete the 'fastestRunner' function below.
 *
 * The function is expected to return a STRING.
 * The function accepts following parameters:
 *  1. STRING marathon
 *  2. STRING sex
 * API URL: https://jsonmock.hackerrank.com/api/marathon?sex=<sex>
 */

fun fastestRunner(marathon: String, sex: String): String {
    //check not null paramaters
    if (marathon.isNullOrBlank() || sex.isNullOrBlank()) {
        return "Invalid input"
    }

    val baseurl = "https://jsonmock.hackerrank.com/api/marathon"
    //default
    var fastestTime = 999.99
    var fastestRunnerName: String? = null

    var page = 1
    var totalPages = 1 // Initialize to 1 to start the loop
    val savedMarathonists = mutableListOf<Marathonist>()

    // Iterate over all pages

    while (page <= totalPages) {

        println("$page")
        val url = "$baseurl?marathon=${URLEncoder.encode(marathon, "UTF-8")}&sex=${URLEncoder.encode(sex, "UTF-8")}&page=$page"
        val connection = URL(url).openConnection() as HttpURLConnection
        println(url)
        try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().readText()
                val gson = Gson()
                val jsonMapped = gson.fromJson(response, MarathonApiResponse::class.java)

                totalPages = jsonMapped.total_pages //update real total pages
                //println("$jsonMapped")
                //println("$totalPages")



                for (runner in jsonMapped.data) {
                    //avoid division errors
                    if (runner.average_speed > 0) {
                        val raceTime = runner.distance_run / runner.average_speed
                        runner.fastCalculated=raceTime
                        savedMarathonists.add(runner)  //save in a list
                        if (raceTime < fastestTime || (raceTime == fastestTime && runner.name < (fastestRunnerName ?: ""))) {
                            fastestTime = raceTime
                            fastestRunnerName = runner.name

                        }
                    }
                }
            } else {
                return "Error: Failed to fetch data (HTTP ${connection.responseCode})"
            }
        } catch (e: Exception) {
            return "Exception: ${e.message}" // Add return statement here
        } finally {
            connection.disconnect()
        }

        page++ // Increment page for the next iteration

    }
    //println("$jsonMapped")
    // Sort saved marathonists by race time (fastCalculated)
    savedMarathonists.sortBy { it.fastCalculated }

    // Export data to CSV
    exportToCSV(savedMarathonists,marathon,sex)

    //println(savedMarathonists)
    return fastestRunnerName ?: "No runners found"
}

fun main() {
    val marathon = "Cityscape Marathon"
    val sex ="female"

    var result = fastestRunner(marathon, sex)

    println(result)

    val marathon2 = "Cityscape Marathon"
    val sex2 ="male"

    result = fastestRunner(marathon2, sex2)

    println(result)
}

// Function to export data to CSV
fun exportToCSV(marathonists: List<Marathonist>, marathon: String,sex: String) {
    val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date()) // Get current date
    val fileName = "marathon_results_$currentDate$marathon$sex.csv" // Add the date to the file name
    val writer = BufferedWriter(FileWriter(fileName))

    // Write the header row
    writer.write("Name, Sex, Age, Distance Run, Average Speed, Race Time\n")

    // Write the marathonists data
    for (runner in marathonists) {
        writer.write("${runner.name}, ${runner.sex}, ${runner.age}, ${runner.distance_run}, ${runner.average_speed}, ${runner.fastCalculated}\n")
    }

    writer.close()
    println("Data exported to $fileName")
}

// Wrapper class for API response
data class MarathonApiResponse(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<Marathonist>
)

// Marathonist class
data class Marathonist(
    val name: String,
    val sex: String,
    val id: String,
    val age: Int,
    val distance_run: Double,
    val average_speed: Double,
    var fastCalculated:  Double //time = distance_run / average_speed
)