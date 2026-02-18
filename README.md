# ExecutionTimer 📊

**Java performance timing utility** that measures method execution times, generates timestamped CSV reports with start/end times, durations (ms & minutes), and automatic file handling.

[![Java](https://img.shields.io/badge/Java-8%2B-orange.svg)](https://www.oracle.com/java/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## ✨ Features

- ⏱️ **Precise timing** - Start/End method tracking with `start()`/`end()`
- 📈 **CSV export** - Auto-generates timestamped reports (`ExecTiming_20260218_001200.csv`)
- 🕐 **Dual format** - Milliseconds + Minutes (2 decimal places)
- 📁 **Auto-naming** - `C:\DEV\ExecTiming_YYYYMMDD_hhmmss.csv`
- 🔄 **Thread-safe** - HashMap tracks multiple methods
- 📋 **CSV headers**: Method/DAO, Start Time, End Time, Duration (ms), Duration (mins)

## 📊 Sample Output CSV

Method/DAO,Start Time (mins),End Time (mins),Total Time (ms),Total Time (mins)
userDAO.findById,2026-02-18 00:12:34.123,2026-02-18 00:12:34.456,333,0.56
orderService.process,2026-02-18 00:12:34.789,2026-02-18 00:12:35.234,445,0.74


## 🚀 Quick Start

### 1. Usage

```java
ExecutionTimer timer = new ExecutionTimer();

// Start timing
timer.start("userDAO.findById");
timer.start("orderService.process");

// Your code...
User user = userService.findById(123);
List<Order> orders = orderService.process(user);

// End timing
timer.end("userDAO.findById");
timer.end("orderService.process");

// Export CSV
timer.writeToCSV();  // Creates: C:\DEV\ExecTiming_20260218_001200.csv
```
2. Output Location
```
C:\DEV\ExecTiming_YYYYMMDD_hhmmss.csv
```
🛠️ API
| Method                   | Description                     |
| ------------------------ | ------------------------------- |
| start(String methodName) | Start timer for method          |
| end(String methodName)   | End timer, calculate duration   |
| writeToCSV()             | Generate timestamped CSV report |

📈 Sample Integration

```
public class PerformanceTest {
    public static void main(String[] args) {
        ExecutionTimer timer = new ExecutionTimer();
        
        // Database operations
        timer.start("userDAO.findAll");
        List<User> users = userDAO.findAll();
        timer.end("userDAO.findAll");
        
        // Business logic
        timer.start("reportService.generate");
        Report report = reportService.generate(users);
        timer.end("reportService.generate");
        
        // Export results
        timer.writeToCSV();
    }
}
```
⚙️ Configuration
| Property             | Default    | Description          |
| -------------------- | ---------- | -------------------- |
| fileOutputPathPrefix | C:\\DEV\\  | CSV output directory |
| LOGICAL_FILENAME     | ExecTiming | CSV filename prefix  |

📁 CSV Format
| Column          | Format    | Example                 |
| --------------- | --------- | ----------------------- |
| Method/DAO      | String    | userDAO.findById        |
| Start Time      | Timestamp | 2026-02-18 00:12:34.123 |
| End Time        | Timestamp | 2026-02-18 00:12:34.456 |
| Duration (ms)   | Long      | 333                     |
| Duration (mins) | Double    | 0.56                    |

🔍 Troubleshooting
```
"FileNotFoundException"


fileOutputPathPrefix = "C:\\\\DEV\\\\";  // Ensure directory exists

Timestamps not showing minutes

Headers use java.sql.Timestamp - shows full datetime

Missing timings

Ensure start() called BEFORE end() for same methodName
```

🧪 Testing
```
java
@Test
public void testTiming() {
    ExecutionTimer timer = new ExecutionTimer();
    timer.start("testMethod");
    Thread.sleep(100);  // Simulate work
    timer.end("testMethod");
    timer.writeToCSV();  // Creates test CSV
}
```
📈 Performance Notes
```
    O(1) lookups via HashMap

    Zero GC pressure - minimal object creation

    Thread-safe for concurrent timing
```
💾 License

MIT License - see LICENSE © 2026
