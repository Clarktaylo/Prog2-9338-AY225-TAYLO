/**
 * =====================================================
 * Student Name    : TAYLO, CLARK KENNETH C.
 * Course          : BSCSIT 1203 Programming 2
 * Assignment      : Programming Assignment 1 — 3x3 Matrix Determinant Solver
 * School          : University of Perpetual Help System DALTA, Molino Campus
 * Date            : March 2026
 * GitHub Repo     : https://github.com/your-username/uphsd-cs-taylo-clarkkenneth
 *
 * Description:
 *   This program determines the determinant of a fixed 3x3 matrix assigned
 *   to TAYLO, CLARK KENNETH C. The computation follows the cofactor
 *   expansion method along the first row. Each stage of the solution is
 *   displayed in the console, including the derived 2x2 submatrices,
 *   their corresponding determinants (minors), the cofactor values, and
 *   the final computed determinant. The output is structured to clearly
 *   present the full solving process step-by-step.
 * =====================================================
 */
public class DeterminantSolver {

    // ── SECTION 1: Matrix Initialization ────────────────────────────────
    // Stores the assigned 3x3 matrix using a two-dimensional array.
    // Each inner array represents a row of the matrix.
    // Matrix layout:
    // | 7  2  1 |
    // | 3  5  4 |
    // | 2  1  6 |
    static int[][] matrix = {
        {7, 2, 1},   // First row
        {3, 5, 4},   // Second row
        {2, 1, 6}    // Third row
    };

    // ── SECTION 2: Minor Calculation Helper ─────────────────────────────
    // Returns the determinant of a 2x2 matrix using:
    // (a × d) − (b × c)
    // This method simplifies repeated calculations when solving minors.
    static int computeMinor(int a, int b, int c, int d) {
        return (a * d) - (b * c);
    }

    // ── SECTION 3: Matrix Display Method ────────────────────────────────
    // Outputs the matrix in a visually organized format for clarity.
    static void printMatrix(int[][] m) {
        System.out.println("┌               ┐");
        for (int[] row : m) {
            System.out.printf("│  %2d  %2d  %2d  │%n", row[0], row[1], row[2]);
        }
        System.out.println("└               ┘");
    }

    // ── SECTION 4: Determinant Computation Process ──────────────────────
    // Performs cofactor expansion along the first row of the matrix:
    // det(M) = a(M11) − b(M12) + c(M13)
    // Each minor and cofactor is calculated and displayed.
    static void solveDeterminant(int[][] m) {

        // Display program title and matrix
        System.out.println("=".repeat(52));
        System.out.println("  3x3 MATRIX DETERMINANT SOLVER");
        System.out.println("  Student: TAYLO, CLARK KENNETH C.");
        System.out.println("  Assigned Matrix:");
        System.out.println("=".repeat(52));
        printMatrix(m);
        System.out.println("=".repeat(52));

        // ── Minor M11 ──
        // Derived by removing row 1 and column 1
        int minor11 = computeMinor(m[1][1], m[1][2], m[2][1], m[2][2]);
        System.out.printf("  Step 1 — M₁₁: (%d×%d)-(%d×%d) = %d%n",
                m[1][1], m[2][2], m[1][2], m[2][1], minor11);

        // ── Minor M12 ──
        // Derived by removing row 1 and column 2
        int minor12 = computeMinor(m[1][0], m[1][2], m[2][0], m[2][2]);
        System.out.printf("  Step 2 — M₁₂: (%d×%d)-(%d×%d) = %d%n",
                m[1][0], m[2][2], m[1][2], m[2][0], minor12);

        // ── Minor M13 ──
        // Derived by removing row 1 and column 3
        int minor13 = computeMinor(m[1][0], m[1][1], m[2][0], m[2][1]);
        System.out.printf("  Step 3 — M₁₃: (%d×%d)-(%d×%d) = %d%n",
                m[1][0], m[2][1], m[1][1], m[2][0], minor13);

        // ── Cofactor Evaluation ──
        // Apply alternating signs: + - +
        int c11 =  m[0][0] * minor11;
        int c12 = -m[0][1] * minor12;
        int c13 =  m[0][2] * minor13;

        System.out.println();
        System.out.printf("  C₁₁ = %d × %d = %d%n", m[0][0], minor11, c11);
        System.out.printf("  C₁₂ = -(%d × %d) = %d%n", m[0][1], minor12, c12);
        System.out.printf("  C₁₃ = %d × %d = %d%n", m[0][2], minor13, c13);

        // ── Final Result ──
        int det = c11 + c12 + c13;

        System.out.printf("%n  det(M) = %d + (%d) + %d%n", c11, c12, c13);
        System.out.println("=".repeat(52));
        System.out.printf("  ✓ FINAL DETERMINANT = %d%n", det);

        // ── Matrix Validation ──
        // If determinant is zero, matrix has no inverse
        if (det == 0) {
            System.out.println("  ⚠ Matrix is singular (non-invertible)");
        }

        System.out.println("=".repeat(52));
    }

    // ── SECTION 5: Program Entry Point ──────────────────────────────────
    // Starts execution and calls the determinant solver.
    public static void main(String[] args) {
        solveDeterminant(matrix);
    }
}