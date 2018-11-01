package com.bradypod.framework.design.creator.mode.combination;

import java.util.Vector;

/**
 * Github
 * User: previous_yu
 * Date: 2018/10/30-17:52
 * Desc: 组合模式又称部分整体模式
 */
public class Tree {

    TreeNode root;

    public Tree(String rootName) {
        this.root = new TreeNode(rootName);
    }

    public static void main(String[] args) {
        Tree tree = new Tree("组合模式示例-1");
        TreeNode node1 = new TreeNode("1-1");
        node1.add(new TreeNode("1-1-1"));
        tree.root.add(node1);
    }
}

class TreeNode {

    private String name;

    private TreeNode parent;

    private Vector<TreeNode> children = new Vector<>();

    public TreeNode(String name) {
        this.name = name;
    }

    //  增加
    public void add(TreeNode treeNode) {
        this.children.add(treeNode);
    }

    // 删除
    public void remove(TreeNode treeNode) {
        this.children.remove(treeNode);
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public Vector<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(Vector<TreeNode> children) {
        this.children = children;
    }
}
