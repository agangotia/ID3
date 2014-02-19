/**
 * 
 */
package com.anupam.ID3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * @author Anupam Gangotia
 * Profile::http://en.gravatar.com/gangotia
 * github::https://github.com/agangotia
 */

/**
 * This is the main class that has the main function()
 *         It accepts two command line arguments: Argument 1 : File name to
 *         Learn from. Argument 2 : File name to Test from. The format of the
 *         source files is as described:: The first line holds the attribute
 *         names, each name followed by an integer representing the number of
 *         possible values for that attribute. Each following line defines a
 *         single example. Each column holds this example’s value for the
 *         attribute given at the top of the file (same order). The last column
 *         holds the class label for the examples
 */
public class ID3AnupamMain {

	/**
	 * @param args
	 *            Arguemnt 1 : File name to Learn from. Argument 2 : File name
	 *            to Test from.
	 */
	public static void main(String[] args) {

		if (args.length != 2) {// if the user has not provided the two
								// arguments, then exit and display message
			System.out.println("Please provide two arguments");
			System.out.println("Correct Syntax is");
			System.out
					.println("java -cp ./src com.anupam.ID3AnupamMain train.dat test.dat");
			return;
		}
		String fileNameLearning = args[0];// reading from param
		String fileNameTesting = args[1];
		int percentLearn = 100;// this is the default percent , which shows how
								// many line to be read from input

		// ID3Learner
		// parameter 1: Learning File Name
		// parameter 2: % of Learn file use to learn the file
		ID3Learner learner = new ID3Learner(fileNameLearning, percentLearn);

		TreeNode rootNode = learner.startLearning();// This function starts
													// learning and creates the
													// tree, which is returned
		if (rootNode != null) {
			rootNode.printTree(rootNode, 0);// print to stdout the tree

			{// Calculating the accuracy on Training Set
				MatrixData matrixTests = new MatrixData();
				matrixTests.prepareMatrix(fileNameLearning, 100);
				// matrixTests.printMatrix();
				matrixTests.getTestAccuracy(rootNode, "Training Set");

			}
			{// Calculating the accuracy on Test Set
				MatrixData matrixTests2 = new MatrixData();
				matrixTests2.prepareMatrix(fileNameTesting, 100);
				// matrixTests.printMatrix();
				matrixTests2.getTestAccuracy(rootNode, "Test Set");
			}

		} else {
			System.out
					.println("Sorry!!.Something Went wrong, The algorithm was not able to create a decision Tree.");

		}
	}
}
