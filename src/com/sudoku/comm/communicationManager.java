package com.sudoku.comm;

import com.sudoku.data.model;

public final class CommunicationManager {
	
	private static volatile CommunicationManager instance = null;

	private CommunicationManager() {
		super();
	}

	public final static CommunicationManager getInstance() {
		if (CommunicationManager.instance == null) {
			synchronized(CommunicationManager.class) {
				if (CommunicationManager.instance == null){
					CommunicationManager.instance = new CommunicationManager();
				}
			}
		}
		return CommunicationManager.instance;
	}

	public List<Grid> getAllGrid() {
		return null;
	}

	public List<Grid> getAllGrid(User user) {
		return null;
	}

	public  List<User> getAllProfile() {
		return null;
	}

	public pushCommentAndSync(Comment newComment, Grid gridToSync) {
		return null;
	}

/* Useless now, grade = comment
	public pushGradeAndSync(Comment newComment, Grid gridToSync) {
		return null;
	}
	*/
}