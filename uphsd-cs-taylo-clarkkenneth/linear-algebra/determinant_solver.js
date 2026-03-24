/**
 * =====================================================
 * Student Name    : TAYLO, CLARK KENNETH C.
 * Course          : BSCSIT 1203 Programming 2
 * Assignment      : Programming Assignment 1 — 3x3 Matrix Determinant Solver
 * School          : University of Perpetual Help System DALTA, Molino Campus
 * Date            : March 2026
 * GitHub Repo     : https://github.com/your-username/uphsd-cs-taylo-clarkkenneth
 * Runtime         : Node.js (execute using: node determinant_solver.js)
 *
 * Description:
 *   This script is the JavaScript counterpart of the Java determinant solver.
 *   It evaluates the determinant of a predefined 3x3 matrix using cofactor
 *   expansion across the first row. The program outputs each stage of the
 *   computation, including minor extraction, arithmetic evaluation, cofactor
 *   application, and final determinant calculation, ensuring clarity of the
 *   entire solving procedure.
 * =====================================================
 */

// ── SECTION 1: Matrix Setup ──────────────────────────────────────────
// The assigned matrix is stored as a nested array structure.
// Each inner array represents one row of the matrix.
const matrix = [
    [7, 2, 1],   // Row 1
    [3, 5, 4],   // Row 2
    [2, 1, 6]    // Row 3
];

// ── SECTION 2: Display Function ──────────────────────────────────────
// Prints the matrix in a boxed format for better readability in console.
function printMatrix(m) {
    console.log("┌               ┐");
    m.forEach(row => {
        const formatted = row.map(num => num.toString().padStart(3)).join("  ");
        console.log(`│ ${formatted}  │`);
    });
    console.log("└               ┘");
}

// ── SECTION 3: Minor Computation Utility ─────────────────────────────
// Calculates the determinant of a 2x2 matrix using:
// (a × d) − (b × c)
function computeMinor(a, b, c, d) {
    return (a * d) - (b * c);
}

// ── SECTION 4: Determinant Solver ────────────────────────────────────
// Executes cofactor expansion along the first row and logs each step.
function solveDeterminant(m) {

    const divider = "=".repeat(52);

    // Display heading and matrix
    console.log(divider);
    console.log("  3x3 MATRIX DETERMINANT SOLVER");
    console.log("  Student: TAYLO, CLARK KENNETH C.");
    console.log("  Assigned Matrix:");
    console.log(divider);
    printMatrix(m);
    console.log(divider);

    console.log("\nCofactor Expansion along Row 1:\n");

    // ── Minor M11 ──
    const minor11 = computeMinor(m[1][1], m[1][2], m[2][1], m[2][2]);
    console.log(
        `  Step 1 — M₁₁: (${m[1][1]}×${m[2][2]}) - (${m[1][2]}×${m[2][1]}) = ${minor11}`
    );

    // ── Minor M12 ──
    const minor12 = computeMinor(m[1][0], m[1][2], m[2][0], m[2][2]);
    console.log(
        `  Step 2 — M₁₂: (${m[1][0]}×${m[2][2]}) - (${m[1][2]}×${m[2][0]}) = ${minor12}`
    );

    // ── Minor M13 ──
    const minor13 = computeMinor(m[1][0], m[1][1], m[2][0], m[2][1]);
    console.log(
        `  Step 3 — M₁₃: (${m[1][0]}×${m[2][1]}) - (${m[1][1]}×${m[2][0]}) = ${minor13}`
    );

    // ── Cofactor Values ──
    // Apply alternating signs: + - +
    const c11 =  m[0][0] * minor11;
    const c12 = -m[0][1] * minor12;
    const c13 =  m[0][2] * minor13;

    console.log();
    console.log(`  C₁₁ = ${m[0][0]} × ${minor11} = ${c11}`);
    console.log(`  C₁₂ = -(${m[0][1]} × ${minor12}) = ${c12}`);
    console.log(`  C₁₃ = ${m[0][2]} × ${minor13} = ${c13}`);

    // ── Determinant Calculation ──
    const det = c11 + c12 + c13;

    console.log();
    console.log(`  det(M) = ${c11} + (${c12}) + ${c13}`);
    console.log(divider);
    console.log(`  ✓ FINAL DETERMINANT = ${det}`);

    // ── Validation Check ──
    if (det === 0) {
        console.log("  ⚠ Matrix is singular (no inverse exists)");
    }

    console.log(divider);
}

// ── SECTION 5: Execution Entry ───────────────────────────────────────
// Starts the program by calling the determinant solver.
solveDeterminant(matrix);