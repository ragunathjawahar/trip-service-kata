package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User

class TripService(
  private val tripDao: TripDAO,
) {
  fun getTripsByUser(user: User, loggedUser: User?): List<Trip> {
    if (loggedUser == null) {
      throw UserNotLoggedInException()
    }

    return if (user.isFriendsWith(loggedUser)) {
      tripDao.tripsBy(user)
    } else {
      emptyList()
    }
  }
}
