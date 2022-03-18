package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User

class TripService(
  private val tripDao: TripDAO,
) {
  fun getTripsByUser(user: User, loggedUser: User?): List<Trip> {
    validateUser(loggedUser)

    return if (user.isFriendsWith(loggedUser)) {
      tripDao.tripsBy(user)
    } else {
      noTrips()
    }
  }

  private fun validateUser(loggedUser: User?) {
    if (loggedUser == null) {
      throw UserNotLoggedInException()
    }
  }

  private fun noTrips(): List<Trip> = emptyList()
}
