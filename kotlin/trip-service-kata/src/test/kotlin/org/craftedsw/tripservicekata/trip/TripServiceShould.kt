package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import org.junit.Test

class TripServiceShould {
  private var loggedInUser: User? = null
  private val guest = null

  @Test(expected = UserNotLoggedInException::class)
  fun `validate the logged in user`() {
    // given
    val tripService = TestableTripService()
    val jane = User()

    loggedInUser = guest

    // when
    tripService.getTripsByUser(jane)
  }

  inner class TestableTripService : TripService() {
    override fun getLoggedUser(): User? {
      return loggedInUser
    }
  }
}
