package com.kingmon.project.psam.acquisition.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CheckUtil {

	/**
	 * 检测是否水平
	 * @param list
	 * @return
	 */
	public static boolean isHorizontal(List<PositionObj> list){
		int size = list.size()-1;
		for (int i = 0; i < size; i++) {
			PositionObj obj1 = list.get(i);
			PositionObj obj2 = list.get(i+1);
			if (obj1.getTop()!=obj2.getTop()) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 检测是否垂直
	 * @param list
	 * @return
	 */
	public static boolean isVertical(List<PositionObj> list){
		int size = list.size()-1;
		for (int i = 0; i < size; i++) {
			PositionObj obj1 = list.get(i);
			PositionObj obj2 = list.get(i+1);
			if (obj1.getLeft()!=obj2.getLeft()) {
				return false;
			}
		}
		return true;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean checkHeBing(List<PositionObj> list){
		if (list==null||list.size()<2) {//检测是否选择了两个以上房间
			return false;
		}
		
		int size = list.size();
		//竖直方向
		if (isVertical(list)) {
			//先进行排序
			Comparator comp = new Comparator() { 
				@Override
				public int compare(Object o1, Object o2) {
					PositionObj obj1 = (PositionObj) o1;
					PositionObj obj2 = (PositionObj) o2;
					return obj1.getTop() - obj2.getTop();
				}
			}; 
			Collections.sort(list, comp);
			/*list.sort(new Comparator<PositionObj>() {
				@Override
				public int compare(PositionObj obj1,PositionObj obj2) {
					return obj1.getTop() - obj2.getTop();
				}
			});*/
			
			for(int i=0;i<size-1;i++){
				PositionObj obj1 = list.get(i);
				PositionObj obj2 = list.get(i+1);
				if (obj1.getWidth()!=obj2.getWidth()) {
					//房间宽度不一致,不能合并
					return false;
				}
				if ((obj1.getTop()+obj1.getHeight()) != obj2.getTop()) {
					//检测到房间位置不连续，不可合并
					return false;
				}
			}
			return true;
		}
		//水平方向
		if (isHorizontal(list)) {
			//先进行排序
			Comparator comp = new Comparator() { 
				@Override
				public int compare(Object o1, Object o2) {
					PositionObj obj1 = (PositionObj) o1;
					PositionObj obj2 = (PositionObj) o2;
					return obj1.getLeft() - obj2.getLeft();
				}
			}; 
			Collections.sort(list, comp);
		/*	list.sort(new Comparator<PositionObj>() {
				@Override
				public int compare(PositionObj obj1,PositionObj obj2) {
					return obj1.getLeft() - obj2.getLeft();
				}
			});*/
			for(int i=0;i<size-1;i++){
				PositionObj obj1 = list.get(i);
				PositionObj obj2 = list.get(i+1);
				if (obj1.getHeight()!=obj2.getHeight()) {
					//房间高度不一致,不能合并
					return false;
				}
				if ((obj1.getLeft()+obj1.getWidth()) != obj2.getLeft()) {
					//检测到房间位置不连续，不可合并
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static void main(String[] args) {

	}
}

class PositionObj {

	private int left;
	private int top;
	private int width;
	private int height;

	public PositionObj(int left, int top, int width, int height) {
		this.left = left; //
		this.top = top;//
		this.width = width;//
		this.height = height;//
	}

	/**
	 * @return the left
	 */
	public int getLeft() {
		return left;
	}

	/**
	 * @param left
	 *            the left to set
	 */
	public void setLeft(int left) {
		this.left = left;
	}

	/**
	 * @return the top
	 */
	public int getTop() {
		return top;
	}

	/**
	 * @param top
	 *            the top to set
	 */
	public void setTop(int top) {
		this.top = top;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

}
