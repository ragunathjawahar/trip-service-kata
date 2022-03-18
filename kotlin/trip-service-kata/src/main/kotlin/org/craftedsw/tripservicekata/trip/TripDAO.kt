package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.CollaboratorCallException
import org.craftedsw.tripservicekata.user.User

class TripDAO {
  companion object {
    @JvmStatic
    fun findTripsByUser(user: User): List<Trip> {
      throw CollaboratorCallException("TripDAO should not be invoked on an unit test.")
    }
  }

  fun tripsBy(user: User): List<Trip> {
    return findTripsByUser(user)
  }
}
