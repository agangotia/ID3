/**
 * 
 */
package com.anupam.ID3;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * @author Anupam Gangotia
 * Profile::http://en.gravatar.com/gangotia
 * github::https://github.com/agangotia
 */
/**
 *This Class is the data structure for Tree. Each node
 *         of a tree has following attribute int atrvalue ->This is the
 *         attribute value String attributeName ->This is the attribute Name
 *         ArrayList<TreeNode> branches -> This is the arraylist of other
 *         branches int fClass ->This is the final class(0/1).
 */
public class TreeNode {

	int atrvalue;// attribute value
					// can be 0,1,2...n based on how many discrete values does
					// the attribute takes
	String attributeName;// This is the attribute Name
							// like attr1, attr2, and so on..
	ArrayList<TreeNode> branches;// All other Child branches
	int fClass; // 0 -> this leaf belongs to Class 0
				// 1 -> this leaf belongs to Class 1
				// -1 -> this is not a leaf node, it does not classifies any
				// class.

	double gain;// Information Gain for the attribute

	// parameterized Constructor
	public TreeNode(int atrvalue, String attributeName, int fClass, double gain) {
		super();
		this.atrvalue = atrvalue;
		this.attributeName = attributeName;
		this.fClass = fClass;

		this.gain = gain;
	}

	// Default Constructor
	public TreeNode() {
		// TODO Auto-generated constructor stub
		branches = new ArrayList<TreeNode>();
	}

	// Getters & Setters
	public int getAtrvalue() {
		return atrvalue;
	}

	public void setAtrvalue(int atrvalue) {
		this.atrvalue = atrvalue;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public ArrayList<TreeNode> getBranches() {
		return branches;
	}

	public void setBranches(ArrayList<TreeNode> branches) {
		this.branches = branches;
	}

	public int getfClass() {
		return fClass;
	}

	public void setfClass(int fClass) {
		this.fClass = fClass;
	}

	public double getGain() {
		return gain;
	}

	public void setGain(double gain) {
		this.gain = gain;
	}
	/*Function ::  printTree, Recursive Function
	 * Parameters :: TreeNode Refrence = The root of the tree we want to print
	 * Parameters :: int level = The level, as this function is always called from root, so it should be 0.
	 * Output:: Prints the build Decision Tree in following form
	 * attr1 = 0 : 
	 * | attr2 = 0 : 
	 * | | attr3 = 0 : 1 
	 * | | attr3 = 1 : 0 
	 * | attr3 = 1 : 
	 * | | attr4 = 0 : 0 
	 * | | attr4 = 1 : 1 
	 * attr1 = 1 : 
	 * | attr2 = 1 : 1 
	 * | 
	 * */
	public void printTree(TreeNode T, int level) {
		if (T.fClass == -1) {// not a leaf node
			for (TreeNode temp : T.branches) {// For all branches of this node
				int i = 0;
				while (i < level) {// According to level value we stdout |
					i++;
					System.out.print("| ");
				}
				System.out.print(T.attributeName);
				System.out.print("=");
				if (temp.fClass == -1)
					System.out.print(temp.atrvalue + " :\n");
				else
					System.out.print(temp.atrvalue + " : ");
				printTree(temp, level + 1);// recursively call same function for
											// child node, with level+1
			}

		} else {// it is a leaf node , hence terminate
			System.out.println(T.fClass);
		}

	}

	/*
	 * Function :: ClassifyTest, Iterative Function Parameters ::
	 * HashMap<Key,Value> testValues Key-> attribute Name, Value -> The value of
	 * that attribute e.g some values in this maps are
	 * <attr1,0><attr2,1><attr3,1> Thus for the given test case, values of
	 * attributes are attr1=0, attr2=1, attr3=1 Parameters :: TreeNode
	 * treeBegin, which is the root node of the tree Output:: Final class for
	 * this given Test: 0, 1 or -1 : if the tree was not able to predict the
	 * output
	 */
	public int ClassifyTest(HashMap<String, Integer> testValues,
			TreeNode treeBegin) {
		int returnValue = -1;
		TreeNode treeTraverse = treeBegin;
		if (treeTraverse.attributeName == null
				|| treeTraverse.attributeName.length() == 0) {
			return returnValue;// if the root node is null then return -1;
		}
		while (treeTraverse != null) {
			if (treeTraverse.fClass != -1) {// if current node is leaf node
				returnValue = treeTraverse.fClass;// then return its class
				treeTraverse = null;
				break;
			} else {// if current node is not leaf node, then based upon the
					// values of test case provided, choose the node to go down.
				String atributeKey = treeTraverse.attributeName;

				if (testValues.containsKey(atributeKey)) {
					int atributeValue = testValues.get(atributeKey);// will
																	// traverse
																	// to this
																	// node now
					if (treeTraverse.branches.size() == 0) {
						break;
					}
					boolean valFound = false;
					for (TreeNode tempChild : treeTraverse.branches) {
						if (tempChild.atrvalue == atributeValue) {
							// this is the correct direction of movement
							valFound = true;
							if (tempChild.fClass != -1) {// if child node is
															// leaf node
								returnValue = tempChild.fClass;// then return
																// its class
								treeTraverse = null;
								break;

							} else {
								treeTraverse = tempChild;// if current node is
															// not leaf
															// node,then
															// traverse this
															// childNode
								continue;
							}
						}
					}
					if (!valFound)// if the child nodes of the traversing node
									// didnt matched any attribute inside map
						break;

				} else {
					System.out.println("Attribute name not found in test case");
					break;
				}

			}
		}

		return returnValue;
	}
}
