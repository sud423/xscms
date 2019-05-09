package com.susd.infrastructure;

public class DatatableParam {

	/*
	 * 绘制计数器。这个是用来确保Ajax从服务器返回的是对应的（Ajax是异步的，因此返回的顺序是不确定的）。 要求在服务器接收到此参数后再返回
	 */
	private int draw; // 第几次请求

	/*
	 * 第一条数据的起始位置，比如0代表第一条数据
	 */
	private int start = 0;// 起止位置

	/*
	 * 告诉服务器每页显示的条数，这个数字会等于返回的 data集合的记录数，可能会大于因为服务器可能没有那么多数据。
	 * 这个也可能是-1，代表需要返回全部数据(尽管这个和服务器处理的理念有点违背)
	 */
	private int length = 10; // 数据长度

	public DatatableParam() {

	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getPageIndex() {
		return start / length + 1;
	}

}
