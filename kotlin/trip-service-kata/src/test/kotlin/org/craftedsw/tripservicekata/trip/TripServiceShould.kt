package org.craftedsw.tripservicekata.trip

import com.google.common.truth.Truth.assertThat
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import org.junit.Before
import org.junit.Test

class TripServiceShould {
  private var loggedInUser: User? = null

  private val guest = null
  private val joe = User()

  private lateinit var tripService: TripService

  @Before
  fun setup() {
    tripService = TestableTripService()
  }

  @Test(expected = UserNotLoggedInException::class)
  fun `validate the logged in user`() {
    // given
    val jane = User()

    loggedInUser = guest

    // when
    tripService.getTripsByUser(jane)
  }

  @Test
  fun `not list user's trips for a stranger`() {
    // given
    val jane = User()
    loggedInUser = joe

    // when
    val trips = tripService.getTripsByUser(jane)

    // then
    assertThat(trips)
      .isEmpty()
  }

  inner class TestableTripService : TripService() {
    override fun getLoggedUser(): User? {
      return loggedInUser
    }
  }
}
