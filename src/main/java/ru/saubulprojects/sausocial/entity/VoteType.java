package ru.saubulprojects.sausocial.entity;

public enum VoteType {

	UPVOTE(1), DOWNVOTE(-1), NEUTRAL(0);
	
	private VoteType(int direction) {
		
	}
	
}
