package org.craftedsw.tripservicekata.trip

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import org.junit.Before
import org.junit.Test

class TripServiceShould {
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
    tripService.getTripsByUser(jane, guest)
  }

  @Test
  fun `not list user's trips for a stranger`() {
    // when
    val trips = tripService.getTripsByUser(jane, joe)

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

    whenever(tripDao.tripsBy(peter)).thenReturn(peter.trips)

    // when
    val trips = tripService.getTripsByUser(peter, jane)

    // then
    assertThat(trips)
      .containsExactly(newYork, sydney)
  }

  inner class TestableTripService(tripDao: TripDAO) : TripService(tripDao) {
  }
}
