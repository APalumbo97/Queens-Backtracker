import java.util.Optional;

/**
 * @author Anthony Palumbo
 * date: 11-14-16
 * description: backtracker class for Queens-backtracking project,
 * searches for a solution and returns one if it exists.
 */
class backtracker {

    /**
     * Constructor for backtracker.
     */
    backtracker() {

    }

    /**
     * Tries to find a solution for a given configuration.
     * @param bc: a valid configuration
     * @return a solution configuration or null if not available
     */
    Optional<boardConfig> solve(boardConfig bc) {
        if(bc.isGoal()) {
            return Optional.of(bc);
        }
        else {
            for(boardConfig child: bc.getSuccessors()) {
                if(child.isValid()) {
                    Optional<boardConfig> s = solve(child);
                    if(s.isPresent()) {
                        return s;
                    }
                }
            }
        }
        return Optional.empty();
    }
}
