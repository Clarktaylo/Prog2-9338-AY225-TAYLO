function compute() {
    const attendance = Number(document.getElementById("attendance").value);
    const lab1 = Number(document.getElementById("lab1").value);
    const lab2 = Number(document.getElementById("lab2").value);
    const lab3 = Number(document.getElementById("lab3").value);
    const examTotal = Number(document.getElementById("examTotal").value);

    const result = document.getElementById("result");

    if (
        attendance < 0 || attendance > 4 ||
        lab1 < 0 || lab1 > 100 ||
        lab2 < 0 || lab2 > 100 ||
        lab3 < 0 || lab3 > 100 ||
        examTotal <= 0
    ) {
        result.className = "status error";
        result.innerHTML = "⚠️ Please enter valid input values.";
        result.classList.remove("hidden");
        return;
    }

    // CLASS STANDING
    const attendancePart = (attendance / 4) * 40;
    const labAverage = (lab1 + lab2 + lab3) / 3;
    const labPart = (labAverage / 100) * 60;

    const classStandingRaw = attendancePart + labPart;
    const classStandingFinal = classStandingRaw * 0.70;

    // REQUIRED PRELIM EXAM SCORE TO PASS
    const requiredExamScore =
        ((75 - classStandingFinal) / 30) * examTotal;

    let output = `
        <strong>Attendance Contribution:</strong> ${attendancePart.toFixed(2)} / 40<br>
        <strong>Lab Work Contribution:</strong> ${labPart.toFixed(2)} / 60<br>
        <strong>Class Standing (70%):</strong> ${classStandingFinal.toFixed(2)}<br><br>
        <strong>Required Prelim Exam Score to PASS:</strong><br>
        ${requiredExamScore.toFixed(2)} / ${examTotal}
    `;

    if (requiredExamScore <= examTotal && requiredExamScore >= 0) {
        result.className = "status success";
        output += "<br><br><strong>REMARKS:</strong> PASSED (Achievable) ✅";
    } else {
        result.className = "status error";
        output += "<br><br><strong>REMARKS:</strong> FAILED (Not achievable even with perfect score) ❌";
    }

    result.innerHTML = output;
    result.classList.remove("hidden");
}
