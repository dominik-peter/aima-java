package aima.gui.fx.applications.search;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.Variable;
import aima.core.search.csp.examples.NQueensCSP;
import aima.core.search.csp.examples.SudokuCSP;
import aima.core.search.csp.solver.CspListener;
import aima.core.search.csp.solver.CspSolver;
import aima.core.search.csp.solver.FlexibleBacktrackingSolver;
import aima.core.search.csp.solver.MinConflictsSolver;

import java.util.Optional;

public class SudokuCspDemo {
	public static void main(String[] args) {
		int[][] initial_values = {
				{0, 5},
				{1, 3},
				{4, 7},
				{9, 6},
				{12, 1},
				{13, 9},
				{14, 5},
				{19, 9},
				{20, 8},
				{25, 6},
				{27, 8},
				{31, 6},
				{35, 3},
				{36, 4},
				{39, 8},
				{41, 3},
				{44, 1},
				{45, 7},
				{49, 2},
				{53, 6},
				{55, 6},
				{60, 2},
				{61, 8},
				{66, 4},
				{67, 1},
				{68, 9},
				{71, 5}
		};
		CSP<Variable, Integer> csp = new SudokuCSP(initial_values);
		CspListener.StepCounter<Variable, Integer> stepCounter = new CspListener.StepCounter<>();
		CspSolver<Variable, Integer> solver;
		Optional<Assignment<Variable, Integer>> solution;
		
		System.out.println("Sudoku (Min-Conflicts)");
		solver = new MinConflictsSolver<>(1000);
		solver.addCspListener(stepCounter);
		stepCounter.reset();
		solution = solver.solve(csp);
		if (solution.isPresent())
			System.out.println((solution.get().isSolution(csp) ? ":-)\n" : ":-\n") + solution.get());
		System.out.println(stepCounter.getResults() + "\n");

		csp = new SudokuCSP(initial_values);
		System.out.println("Sudoku (Backtracking + MRV & DEG + LCV + AC3)");
		solver = new FlexibleBacktrackingSolver<Variable, Integer>().setAll();
		solver.addCspListener(stepCounter);
		stepCounter.reset();
		solution = solver.solve(csp);
		if (solution.isPresent())
			System.out.println((solution.get().isSolution(csp) ? ":-)\n" : ":-\n") + solution.get());
		System.out.println(stepCounter.getResults() + "\n");

		csp = new SudokuCSP(initial_values);
		System.out.println("Sudoku (Backtracking)");
		solver = new FlexibleBacktrackingSolver<>();
		solver.addCspListener(stepCounter);
		stepCounter.reset();
		solution = solver.solve(csp);
		if (solution.isPresent())
			System.out.println((solution.get().isSolution(csp) ? ":-)\n" : ":-\n") + solution.get());
		System.out.println(stepCounter.getResults() + "\n");
	}
}
