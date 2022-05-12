package aima.core.search.csp.examples;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Domain;
import aima.core.search.csp.Variable;

import java.util.ArrayList;
import java.util.List;

public class SudokuCSP extends CSP<Variable, Integer> {
	public SudokuCSP(int[][] initial_values) {
		int size = 9;

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				addVariable(new Variable("" + (i+1) + (j+1)));
			}
		}
		
		List<Integer> values = new ArrayList<>();
		for (int val = 1; val <= size; val++)
			values.add(val);
		Domain<Integer> positions = new Domain<>(values);

		for (Variable var : getVariables())
			setDomain(var, positions);

		addRowConstraints();
		addColumnConstraints();
		addBlockConstraints();

		addInitialValues(initial_values);
	}

	public void addRowConstraints() {
		// add row constraints
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// calculate correct id of first row variable
				int row = i*9+j;
				Variable var1 = getVariables().get(row);
				for (int column = row+1; column < (i*9)+9; column++) {
					Variable var2 = getVariables().get(column);
					addConstraint(new NotEqualConstraint<>(var1, var2));
					// System.out.println("Row Constraint: i "+ i +" j "+j+" Var1 "+row+", Var2 "+column);
				}
			}
		}
	}

	public void addColumnConstraints() {
		// add column constraints
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int column = i+(j*9);
				Variable var1 = getVariables().get(column);
				for (int k = 1; k < size-j; k++) {
					int row = column + (k*size);
					Variable var2 = getVariables().get(row);
					addConstraint(new NotEqualConstraint<>(var1, var2));
					// System.out.println("Column Constraint: i "+ i +" j "+j+" Var1 "+column+", Var2 "+row);
				}
			}
		}
	}

	public void addBlockConstraints() {
		// add block constraints
		int[][] blocks = {
				{0, 1, 2, 9, 10, 11, 18, 19, 20},
				{3, 4, 5, 12, 13, 14, 21, 22, 23},
				{6, 7, 8, 15, 16, 17, 24, 25, 26},
				{27, 28, 29, 36, 37, 38, 45, 46, 47},
				{30, 31, 32, 39, 40, 41, 48, 49, 50},
				{33, 34, 35, 42, 43, 44, 51, 52, 53},
				{54, 55, 56, 63, 64, 65, 72, 73, 74},
				{57, 58, 59, 66, 67, 68, 75, 76, 77},
				{60, 61, 62, 69, 70, 71, 78, 79, 80}
		};
		// iterate through blocks
		for (int[] block : blocks) {
			// iterate through var1
			for (int i = 0; i < blocks.length; i++) {
				Variable var1 = getVariables().get(block[i]);
				//iterate through var2
				for (int j = i; j < blocks.length; j++) {
					Variable var2 = getVariables().get(block[j]);
					addConstraint(new NotEqualConstraint<>(var1, var2));
					//System.out.println("i: "+i+", j: "+j+", Var1: "+block[i]+", Var2: "+block[j]);
				}
			}
		}
	}

	public void addInitialValues(int[][] initial_values) {
		for (int[] elem : initial_values) {
			Variable var = getVariables().get(elem[0]);
			int value = elem[1];

			for (int val = 1; val <= 9; val++) {
				if (val == value)
					continue;
				this.removeValueFromDomain(var, val);
			}
		}
	}
}