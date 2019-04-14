package com.kreative.dotbamcrak.tileset;

public enum TraditionalRank {
	ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE;
	
	public int getValue() {
		switch (this) {
		case ONE: return 1;
		case TWO: return 2;
		case THREE: return 3;
		case FOUR: return 4;
		case FIVE: return 5;
		case SIX: return 6;
		case SEVEN: return 7;
		case EIGHT: return 8;
		case NINE: return 9;
		default: return 0;
		}
	}
	
	public boolean isTerminal() {
		return this == ONE || this == NINE;
	}
}
