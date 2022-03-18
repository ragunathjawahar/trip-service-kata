package org.craftedsw.tripservicekata.trip

import com.google.common.truth.Truth.assertThat
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import org.junit.Before
import org.junit.Test

class TripServiceShould {
  private var loggedInUser: User? = null

  private val guest = null
  private val jane = User()
  private val joe = User()

  private lateinit var tripService: TripService

  @Before
  fun setup() {
    tripService = TestableTripService()
  }

  @Test(expected = UserNotLoggedInException::class)
  fun `validate the logged in user`() {
    // given
    loggedInUser = guest

    // when
    tripService.getTripsByUser(jane)
  }

  @Test
  fun `not list user's trips for a stranger`() {
    // given
    loggedInUser = joe

    // when
    val trips = tripService.getTripsByUser(jane)

    // then
    assertThat(trips)
      .isEmpty()
  }

  @Test
  fun `list user's trip for a friend`() {
    // given
    val newYork = Trip()
    val sydney = Trip()

    val peter = User()
    peter.addTrip(newYork)
    peter.addTrip(sydney)

    peter.addFriend(jane)

    loggedInUser = jane

    // when
    val trips = tripService.getTripsByUser(peter)

    // then
    assertThat(trips)
      .containsExactly(newYork, sydney)
  }

  inner class TestableTripService : TripService() {
    override fun tripsBy(user: User): List<Trip> {
      return user.trips
    }

    override fun getLoggedUser(): User? {
      return loggedInUser
    }
  }
}
