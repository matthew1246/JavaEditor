package com.perky;

public class XYWidthHeight {
	/*
	** You can use get and set methods or do xywidtheheight.x = 1
	*/
	public int x;
	public int y;
	public int width;
	public int height;
	/*
	** Use constructor to create object.
	*/
	public XYWidthHeight(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public void setx(int x) {
		this.x = x;
	}
	public int getx() {
		return x;
	}
	public void sety(int y) {
		this.y = y;
	}
	public int gety() {
		return y;
	}
	public void setwidth(int width) {
		this.width = width;
	}
	public int getwidth() {
		return width;
	}
	public void setheight(int height) {
		this.height = height;
	}
	public int getheight() {
		return height;
	}
}
