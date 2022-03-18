package org.craftedsw.tripservicekata.trip

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
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
  private lateinit var tripDao: TripDAO

  @Before
  fun setup() {
    tripDao = mock()
    tripService = TestableTripService(tripDao)
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

    whenever(tripDao.tripsBy(peter)).thenReturn(peter.trips)

    // when
    val trips = tripService.getTripsByUser(peter)

    // then
    assertThat(trips)
      .containsExactly(newYork, sydney)
  }

  inner class TestableTripService(tripDao: TripDAO) : TripService(tripDao) {
    override fun getLoggedUser(): User? {
      return loggedInUser
    }
  }
}
