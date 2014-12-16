package com.sudoku.data.model;

import java.util.List;

public class ExportUser {
	private User user;
	private List<Grid> availableGrids;
	private List<PlayedGrid> playedGrids;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Grid> getAvailableGrids() {
		return availableGrids;
	}
	public void setAvailableGrids(List<Grid> availableGrids) {
		this.availableGrids = availableGrids;
	}
	public List<PlayedGrid> getPlayedGrids() {
		return playedGrids;
	}
	public void setPlayedGrids(List<PlayedGrid> playedGrids) {
		this.playedGrids = playedGrids;
	}
	
}
