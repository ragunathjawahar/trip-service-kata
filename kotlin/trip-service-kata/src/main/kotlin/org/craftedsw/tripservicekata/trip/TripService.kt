package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import org.craftedsw.tripservicekata.user.UserSession

open class TripService {
  fun getTripsByUser(user: User): List<Trip> {
    val loggedUser: User? = getLoggedUser()
    if (loggedUser == null) {
      throw UserNotLoggedInException()
    }

    if (user.isFriendsWith(loggedUser)) {
      return tripsBy(user)
    }
    return emptyList()
  }

  internal open fun getLoggedUser(): User? {
    return UserSession.instance.loggedUser
  }

  internal open fun tripsBy(user: User): List<Trip> {
    return TripDAO.findTripsByUser(user)
  }
}
