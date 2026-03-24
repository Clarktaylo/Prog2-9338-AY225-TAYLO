# 3×3 Matrix Determinant Solver

## Student Information

**Name:** TAYLO, CLARK KENNETH C.
**Section:** 9339-AY225
**Course:** BSCSIT 1203 Programming 2
**School:** University of Perpetual Help System DALTA – Molino Campus

---

## Assignment Details

**Title:** Programming Assignment 1 — 3×3 Matrix Determinant Solver

This project computes the determinant of a fixed 3×3 matrix using **cofactor expansion along the first row**.
Both **Java** and **JavaScript (Node.js)** implementations are included, and each program prints a detailed step-by-step solution.

---

## Assigned Matrix

The matrix used in this assignment is:

```
|  7   2   1 |
|  3   5   4 |
|  2   1   6 |
```

---

## How to Run the Java Program

### 1. Compile the file

```
javac DeterminantSolver.java
```

### 2. Run the program

```
java DeterminantSolver
```

---

## How to Run the JavaScript Program

Make sure Node.js is installed, then run:

```
node determinant_solver.js
```

---

## Sample Output (Java & JavaScript)

```
====================================================
  3x3 MATRIX DETERMINANT SOLVER
  Student: TAYLO, CLARK KENNETH C.
  Assigned Matrix:
====================================================
┌               ┐
│   7   2   1   │
│   3   5   4   │
│   2   1   6   │
└               ┘
====================================================

Cofactor Expansion along Row 1:

  Step 1 — M₁₁: (5×6) - (4×1) = 26
  Step 2 — M₁₂: (3×6) - (4×2) = 10
  Step 3 — M₁₃: (3×1) - (5×2) = -7

  C₁₁ = 7 × 26 = 182
  C₁₂ = -(2 × 10) = -20
  C₁₃ = 1 × -7 = -7

  det(M) = 182 + (-20) + -7
====================================================
  ✓ FINAL DETERMINANT = 155
====================================================
```

---

## Final Result

**Determinant = 155**

---

## Notes

* The determinant is computed using the formula:
  **det(M) = a(M₁₁) − b(M₁₂) + c(M₁₃)**
* Each minor is derived from a 2×2 submatrix.
* If the determinant equals **0**, the matrix is considered **singular** (non-invertible).

---
