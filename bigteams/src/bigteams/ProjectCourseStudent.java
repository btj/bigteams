package bigteams;

import logicalcollections.LogicalSet;

/**
 * Each instance of this class represents a student in a project course,
 * as part of a student-team graph.
 * 
 * @invar If a student is in a team, it is among its members.
 *    | getTeam() == null || getTeam().getMembers().contains(this)
 */
public class ProjectCourseStudent {
	
	/**
	 * @invar | true // Phase 1 representation invariant
	 * @invar | team == null || team.members.contains(this) // Phase 2 representation invariant
	 * 
	 * @peerObject
	 */
	Team team;
	
	/**
	 * Returns this student's team, or {@code null} if they are not in a team.
	 * 
	 * @peerObject
	 */
	public Team getTeam() { return team; }
	
	/**
	 * Initializes this object as representing a student who is not in a team.
	 */
	public ProjectCourseStudent() {}

	/**
	 * Make this student a member of the given team.
	 *
	 * @pre {@code team} is not null.
	 *      (Cannot make this a @throws for now because of https://github.com/fsc4j/fsc4j/issues/6 .)
	 *    | team != null
	 * @throws IllegalStateException if this student is already in a team.
	 *    | getTeam() != null
	 * 
	 * @mutates_properties | this.getTeam(), team.getMembers()
	 * 
	 * @post The given team's members equal its old members plus this student.
	 *    | team.getMembers().equals(LogicalSet.plus(old(team.getMembers()), this))
	 */
	public void join(Team team) {
		if (this.team != null)
			throw new IllegalStateException("this student is already in a team");
		
		this.team = team;
		team.members.add(this);
	}

	/**
	 * Make this student no longer be a member of their team.
	 * 
	 * @pre This student is in a team.
	 *      (Cannot make this a @throws for now because of https://github.com/fsc4j/fsc4j/issues/6 .)
	 *    | getTeam() != null
	 * 
     * @mutates_properties | this.getTeam(), this.getTeam().getMembers()
     * 
     * @post This student is not in a team.
     *    | getTeam() == null
     * @post This student's old team's members are its old members minus this student.
     *    | old(getTeam()).getMembers().equals(LogicalSet.minus(old(getTeam().getMembers()), this))
	 */
	public void leaveTeam() {
		if (this.team == null)
			throw new IllegalStateException("this student is not in a team");
		
		team.members.remove(this);
		team = null;
	}
}
