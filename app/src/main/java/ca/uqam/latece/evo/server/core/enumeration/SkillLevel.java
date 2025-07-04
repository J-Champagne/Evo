package ca.uqam.latece.evo.server.core.enumeration;

/**
 * Represents the levels of skill.
 * <p>
 * The SkillLevel enum defines three levels of skill:
 * BEGINNER - for individuals new to a skill.
 * INTERMEDIATE - for those with moderate experience and knowledge.
 * ADVANCED - for highly skilled and experienced.
 * <p>
 * @since 22.01.2025.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
public enum SkillLevel {
	BEGINNER,
	INTERMEDIATE,
	ADVANCED;

	/**
	 * Convert the SkillLevel to String.
	 * @param skillLevel The selected skillLevel.
	 * @return Return the SkillLevel in string format.
	 */
	public static String toString(SkillLevel skillLevel) {
		return switch (skillLevel) {
			case BEGINNER -> "Beginner";
			case INTERMEDIATE -> "Intermediate";
			case ADVANCED -> "Advanced";
		};
	}

}
