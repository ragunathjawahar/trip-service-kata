package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import org.craftedsw.tripservicekata.user.UserSession

open class TripService {
    fun getTripsByUser(user: User): List<Trip> {
        var tripList: List<Trip> = ArrayList()
        val loggedUser: User? = getLoggedUser()
        if (loggedUser != null) {
            var isFriend = false
            for (friend in user.friends) {
                if (friend == loggedUser) {
                    isFriend = true
                    break
                }
            }
            if (isFriend) {
                tripList = tripsBy(user)
            }
            return tripList
        } else {
            throw UserNotLoggedInException()
        }
    }

  internal open fun getLoggedUser(): User? {
      return UserSession.instance.loggedUser
  }

  internal open fun tripsBy(user: User): List<Trip> {
      return TripDAO.findTripsByUser(user)
  }
}
