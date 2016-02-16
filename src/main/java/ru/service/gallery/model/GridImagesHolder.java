package ru.service.gallery.model;

import java.util.List;

import ru.service.gallery.entity.Image;

public class GridImagesHolder {

	private boolean emptyGrid;
	private List<Image> images;
	private int leftDigit;
	private int middleDigit;
	private int rightDigit;
	private int firstPage;
	private int prevPage;
	private int lastPage;
	private int nextPage;
	private boolean presentsMiddleDigit;
	private boolean presentsRightDigit;
	private boolean blockedFirst;
	private boolean blockedLast;
	private boolean blockedNext;
	private boolean blockedPrev;
	private boolean activeLleftDigit;
	private boolean activeMiddleDigit;
	private boolean activeRightDigit;
	
	public GridImagesHolder(boolean emptyGrid) {
		super();
		this.emptyGrid = emptyGrid;
	}
	public GridImagesHolder(boolean emptyGrid, List<Image> images, int leftDigit, int middleDigit, int rightDigit,
			int firstPage, int prevPage, int lastPage, int nextPage, boolean presentsMiddleDigit,
			boolean presentsRightDigit, boolean blockedFirst, boolean blockedLast, boolean blockedNext,
			boolean blockedPrev, boolean activeLleftDigit, boolean activeMiddleDigit, boolean activeRightDigit) {
		super();
		this.emptyGrid = emptyGrid;
		this.images = images;
		this.leftDigit = leftDigit;
		this.middleDigit = middleDigit;
		this.rightDigit = rightDigit;
		this.firstPage = firstPage;
		this.prevPage = prevPage;
		this.lastPage = lastPage;
		this.nextPage = nextPage;
		this.presentsMiddleDigit = presentsMiddleDigit;
		this.presentsRightDigit = presentsRightDigit;
		this.blockedFirst = blockedFirst;
		this.blockedLast = blockedLast;
		this.blockedNext = blockedNext;
		this.blockedPrev = blockedPrev;
		this.activeLleftDigit = activeLleftDigit;
		this.activeMiddleDigit = activeMiddleDigit;
		this.activeRightDigit = activeRightDigit;
	}
	
	public boolean isEmptyGrid() {
		return emptyGrid;
	}
	public void setEmptyGrid(boolean emptyGrid) {
		this.emptyGrid = emptyGrid;
	}
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}
	public int getLeftDigit() {
		return leftDigit;
	}
	public void setLeftDigit(int leftDigit) {
		this.leftDigit = leftDigit;
	}
	public int getMiddleDigit() {
		return middleDigit;
	}
	public void setMiddleDigit(int middleDigit) {
		this.middleDigit = middleDigit;
	}
	public int getRightDigit() {
		return rightDigit;
	}
	public void setRightDigit(int rightDigit) {
		this.rightDigit = rightDigit;
	}
	public int getFirstPage() {
		return firstPage;
	}
	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}
	public int getPrevPage() {
		return prevPage;
	}
	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}
	public int getLastPage() {
		return lastPage;
	}
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public boolean isPresentsMiddleDigit() {
		return presentsMiddleDigit;
	}
	public void setPresentsMiddleDigit(boolean presentsMiddleDigit) {
		this.presentsMiddleDigit = presentsMiddleDigit;
	}
	public boolean isPresentsRightDigit() {
		return presentsRightDigit;
	}
	public void setPresentsRightDigit(boolean presentsRightDigit) {
		this.presentsRightDigit = presentsRightDigit;
	}
	public boolean isBlockedFirst() {
		return blockedFirst;
	}
	public void setBlockedFirst(boolean blockedFirst) {
		this.blockedFirst = blockedFirst;
	}
	public boolean isBlockedLast() {
		return blockedLast;
	}
	public void setBlockedLast(boolean blockedLast) {
		this.blockedLast = blockedLast;
	}
	public boolean isBlockedNext() {
		return blockedNext;
	}
	public void setBlockedNext(boolean blockedNext) {
		this.blockedNext = blockedNext;
	}
	public boolean isBlockedPrev() {
		return blockedPrev;
	}
	public void setBlockedPrev(boolean blockedPrev) {
		this.blockedPrev = blockedPrev;
	}
	public boolean isActiveLleftDigit() {
		return activeLleftDigit;
	}
	public void setActiveLleftDigit(boolean activeLleftDigit) {
		this.activeLleftDigit = activeLleftDigit;
	}
	public boolean isActiveMiddleDigit() {
		return activeMiddleDigit;
	}
	public void setActiveMiddleDigit(boolean activeMiddleDigit) {
		this.activeMiddleDigit = activeMiddleDigit;
	}
	public boolean isActiveRightDigit() {
		return activeRightDigit;
	}
	public void setActiveRightDigit(boolean activeRightDigit) {
		this.activeRightDigit = activeRightDigit;
	}
}
