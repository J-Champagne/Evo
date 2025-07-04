package ca.uqam.latece.evo.server.core.enumeration;

/**
 * Represents the type of skill.
 * The SkillType enum defines four types of skill:
 * BCT - for BCT skill.
 * MENTAL - for mental skill.
 * PHYSICAL - for physical skill.
 * ETC - for other types of skill.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
public enum SkillType {
    BCT,
    MENTAL,
    PHYSICAL,
    ETC;

    /**
     * Convert the SkillType to String.
     * @param skillType The selected SkillType.
     * @return Return the SkillType in string format.
     */
    public static String toString(SkillType skillType) {
        return switch (skillType) {
            case BCT -> "BCT";
            case MENTAL -> "Mental";
            case PHYSICAL -> "Physical";
            case ETC -> "Etc.";
        };
    }

}
