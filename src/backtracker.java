/**
 * @author: Anthony Palumbo
 * date: 11-14-16
 * description: backtracker class for Queens-backtracking project
 */

import java.util.Optional;

/**
 * Class for the recursive backtracking algorithm.
 * Searches for a solution and returns one if it exists.
 */
public class backtracker {

    /**
     * Constructor for backtracker.
     */
    public backtracker() {
        // Empty Constructor.
    }

    /**
     * Tries to find a solution for a given configuration.
     * @param bc: a valid configuration
     * @return: a solution configuration or null if not available
     */
    public Optional<boardConfig> solve(boardConfig bc) {
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
