package ru.service.gallery.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum ButtonsOfPaginator {

	LEFT, MIDDLE, RIGHT, FIRST, PREVIOUS, LAST, NEXT;
	
	public static List<ButtonsOfPaginator> buttonsList() {
		return Collections.unmodifiableList(Arrays.asList(ButtonsOfPaginator.values()));
	}
}
