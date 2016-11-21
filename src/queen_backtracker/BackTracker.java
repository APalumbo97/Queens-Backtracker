package queen_backtracker;

import java.util.Optional;

/**
 * @author Anthony Palumbo
 * date: 11-14-16
 * description: backtracker class for Queens-backtracking project,
 * searches for a solution and returns one if it exists.
 */
public class BackTracker {

    /**
     * Constructor for back tracker.
     */
    public BackTracker() {

    }

    /**
     * Tries to find a solution for a given configuration.
     * @param bc: a valid configuration
     * @return a solution configuration or null if not available
     */
    public Optional<BoardConfiguration> solve(BoardConfiguration bc) {
        if(bc.isGoal()) {
            return Optional.of(bc);
        }
        else {
            for(BoardConfiguration child: bc.getSuccessors()) {
                if(child.isValid()) {
                    Optional<BoardConfiguration> s = solve(child);
                    if(s.isPresent()) {
                        return s;
                    }
                }
            }
        }
        return Optional.empty();
    }
}
