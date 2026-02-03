// LOGIN CREDENTIALS
const VALID_USERNAME = "ADMIN";
const VALID_PASSWORD = "12345";

// BEEP SOUND
const beep = new Audio("beep.mp3");

// LOGIN FUNCTION
function login() {
    const username = document.getElementById("username").value.trim().toUpperCase();
    const password = document.getElementById("password").value.trim();
    const status = document.getElementById("loginStatus");

    status.innerHTML = "";
    status.className = "status";

    if (username === VALID_USERNAME && password === VALID_PASSWORD) {

        status.innerHTML = "✔ SUCCESSFULLY LOGGED IN";
        status.classList.add("success");

        setTimeout(() => {
            document.getElementById("loginSection").classList.add("hidden");
            document.getElementById("attendanceSection").classList.remove("hidden");
            document.getElementById("welcomeMsg").innerText = "WELCOME, " + username;
        }, 1000);
        
    } else {
        beep.play();
        status.innerHTML = "❌ ERROR! INVALID USERNAME OR PASSWORD!";
        status.classList.add("error");
    }
}

// FORCE CAPSLOCK WHILE TYPING
document.addEventListener("input", function (e) {
    if (e.target.id === "fullName" || e.target.id === "courseYear") {
        e.target.value = e.target.value.toUpperCase();
    }
});

// TIME IN FUNCTION
function timeIn() {
    const name = document.getElementById("fullName").value.trim();
    const course = document.getElementById("courseYear").value.trim();
    const status = document.getElementById("attendanceStatus");

    status.innerHTML = "";
    status.className = "status";

    // NAME VALIDATION
    if (!name.includes(",")) {
        status.innerHTML = "❌ ERROR! INVALID FULL NAME FORMAT!";
        status.classList.add("error");
        return;
    }

    const parts = name.split(",");
    if (parts[0].trim().length < 2 || parts[1].trim().length < 1) {
        status.innerHTML = "❌ ERROR! INVALID FULL NAME FORMAT!";
        status.classList.add("error");
        return;
    }

    // COURSE/YEAR VALIDATION
    const courseRegex = /^[A-Z]{4,6} - (1ST|2ND|3RD|4TH|5TH) YEAR$/;
    if (!courseRegex.test(course)) {
        status.innerHTML = "❌ ERROR! INVALID COURSE FORMAT!";
        status.classList.add("error");
        return;
    }

    // TIME FORMAT
    const now = new Date();
    const timeInFormatted = formatDate(now);

    // E-SIGNATURE
    const eSignature = crypto.randomUUID().toUpperCase();

    // STATUS DISPLAY
    status.innerHTML =
        "✔ SUCCESSFULLY TIMED IN!<br>" +
        "NAME: " + name + "<br>" +
        "COURSE/YEAR: " + course + "<br>" +
        "TIME IN: " + timeInFormatted + "<br>" +
        "E-SIGNATURE: " + eSignature;

    status.classList.add("success");

    // GENERATE FILE
    generateAttendanceFile(name, course, timeInFormatted, eSignature);
}

// DATE FORMAT
function formatDate(date) {
    const months = [
        "JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE",
        "JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"
    ];

    let hours = date.getHours();
    const minutes = date.getMinutes().toString().padStart(2, "0");
    const ampm = hours >= 12 ? "PM" : "AM";
    hours = hours % 12 || 12;

    return `${months[date.getMonth()]} ${date.getDate()}, ${date.getFullYear()} | ${hours}:${minutes} ${ampm}`;
}

// FILE GENERATION
function generateAttendanceFile(name, course, timeIn, eSignature) {
    const content =
        "ATTENDANCE SUMMARY\n" +
        "-------------------------\n" +
        "NAME: " + name + "\n" +
        "COURSE/YEAR: " + course + "\n" +
        "TIME IN: " + timeIn + "\n" +
        "E-SIGNATURE: " + eSignature + "\n" +
        "STATUS: SUCCESSFULLY TIMED IN!\n";

    const blob = new Blob([content], { type: "text/plain" });
    const link = document.createElement("a");

    link.href = URL.createObjectURL(blob);
    link.download = "attendance_summary.txt";
    link.click();
}