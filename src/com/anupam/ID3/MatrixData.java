/**
 * 
 */
package com.anupam.ID3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
/**
 * @author Anupam Gangotia
 * Profile::http://en.gravatar.com/gangotia
 * github::https://github.com/agangotia
 */
/**
 * This is the Matrix datatype.,
 */
public class MatrixData {
	int coloumns;//number of coloumns in matrix
	ArrayList<String> Headers;//Values in headers of matrix
	ArrayList<int[]> rows;//rows in matrix
	int Numrows;//number of rows

	//Default constructor
	public MatrixData() {
		rows = new ArrayList<int[]>();
		Headers = new ArrayList<String>();
	}

	
	public ArrayList<String> getHeaders() {
		return Headers;
	}

	public void setHeaders(ArrayList<String> headers) {
		Headers = headers;
	}

	public ArrayList<int[]> getRows() {
		return rows;
	}

	public void setRows(ArrayList<int[]> rows) {
		this.rows = rows;
	}

	
	//This function prints the matrix read from the file
	//usefull in debugging
	public void printMatrix() {
		// print header
		for (String temp : Headers) {
			System.out.print("\t" + temp);
		}
		for (int[] temp : rows) {
			System.out.println("");
			for (int i = 0; i < coloumns; i++) {
				System.out.print("\t" + temp[i]);
			}
		}
		System.out.println("");
	}

	//FIlls the given array with values for which index is matched to the given index
	public void fillArray(int[] arrayToFill, int indexToFetch) {
		int arrIndex = 0;
		for (int[] temp : rows) {
			// System.out.print("\n"+temp[indexToFetch]);
			arrayToFill[arrIndex++] = temp[indexToFetch];
		}
	}

	//Reads the file and prepares the matrix
	public void prepareMatrix(String FileNameToRead,
			int PercentageOfDataToLEarnFrom) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(FileNameToRead));
			int NumberOfColoumns = 0;
			// Reading Header
			{
				String line = br.readLine();
				StringTokenizer st = new StringTokenizer(line);

				boolean notNum = true;
				while (st.hasMoreElements()) {
					if (notNum) {
						Headers.add((String) st.nextElement());
						notNum = false;
					} else {
						st.nextElement();
						notNum = true;
					}

				}

			}

			Headers.add("Class");
			/*
			 * System.out.println("Lets Check Headers"); for(String temp:head){
			 * System.out.println("\t "+temp); }
			 */
			NumberOfColoumns = Headers.size();
			coloumns = NumberOfColoumns;
			// matrix=new MatrixData(NumberOfColoumns);
			// matrix.setHeader(head);
			{// lets read coloumns
				String line = br.readLine();
				while (line != null) {
					// System.out.print(line);
					StringTokenizer st = new StringTokenizer(line);
					// System.out.println("---- Split by space ------");
					int[] tempCol = new int[NumberOfColoumns];
					int tempIndex = 0;
					while (st.hasMoreElements()) {
						tempCol[tempIndex++] = Integer.parseInt((String) st
								.nextElement());
						// System.out.println(tempCol[tempIndex-1]);
					}
					rows.add(tempCol);
					line = br.readLine();
				}
			}

			// Now the truncating part,
			int rowsAfterTrunc = (int) ((PercentageOfDataToLEarnFrom * (rows
					.size())) / 100);
			if (rowsAfterTrunc == rows.size()) {
				// do nothing
			} else {
				for (int i = rows.size() - 1; i > rowsAfterTrunc; i--) {
					rows.remove(i);
				}
			}

			Numrows = rows.size();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/*
	 * Function :: splitMatrix, 
	 * This function splits the matrix
	 * based on the given attribute name, and its value.,
	 * rest other rows are removed from the row.
	 */
	public MatrixData splitMatrix(String attributeName, int value) {// this
																	// fucntion
																	// needs
																	// some
																	// modifications
		MatrixData matrixReturn = new MatrixData();
		ArrayList<int[]> rowsMatrixReturn = new ArrayList<int[]>();
		ArrayList<String> HeadersMatrixReturn = new ArrayList<String>();
		int attributeIndex = 0;

		// First set into headers all headers except the Selected Attribute
		for (String tempHeadValue : Headers) {
			if (!tempHeadValue.equals(attributeName))
				HeadersMatrixReturn.add(tempHeadValue);
		}
		matrixReturn.coloumns = HeadersMatrixReturn.size();
		matrixReturn.setHeaders(HeadersMatrixReturn);

		for (attributeIndex = 0; attributeIndex < Headers.size(); attributeIndex++) {
			if (attributeName.equals(Headers.get(attributeIndex))) {
				break;
			}
		}
		// System.out.println("Attribute found at:"+attributeIndex);

		for (int[] temp : rows) {
			if (temp[attributeIndex] == value) {
				int[] tempRow = new int[HeadersMatrixReturn.size()];
				int indexTempRow = 0;
				for (int i = 0; i < coloumns; i++) {
					if (i == attributeIndex) {

					} else {
						tempRow[indexTempRow] = temp[i];
						indexTempRow++;
					}
				}

				rowsMatrixReturn.add(tempRow);
			}

		}
		matrixReturn.setRows(rowsMatrixReturn);
		matrixReturn.Numrows = rowsMatrixReturn.size();
		return matrixReturn;
	}

	/*
	 * Function :: getTestAccuracy, 
	 * This fucntion forms the Hashmap of attributes and its values for each Row in matrix,
	 * and then calls the ClassifyTest, and verifies the results returned with the result expected
	 * to calculate the accuracy.
	 */
	public double getTestAccuracy(TreeNode treeBegin, String typeOfData) {
		double accuracy = 0.0;
		int countPositives = 0;
		for (int[] temp : rows) {
			HashMap<String, Integer> testValues = new HashMap<String, Integer>();
			int finalValue = -1;
			for (int i = 0; i < coloumns; i++) {
				if (i == (coloumns - 1)) {
					finalValue = temp[i];
				} else {
					testValues.put((String) Headers.get(i), temp[i]);
				}

			}
			// System.out.println("Test returned"+treeBegin.ClassifyTest(testValues,treeBegin));
			if (finalValue == treeBegin.ClassifyTest(testValues, treeBegin)) {
				countPositives++;
			}
		}
		// System.out.println("Test Positives"+countPositives);
		accuracy = ((double) countPositives / (rows.size())) * 100;

		System.out.print("Accuracy on " + typeOfData + " (" + rows.size()
				+ " instances )");
		System.out.print(" = " + accuracy + " %\n");
		return accuracy;
	}
}
