package org.craftedsw.tripservicekata.user

import org.craftedsw.tripservicekata.trip.Trip

class UserBuilder {
  companion object {
    fun aUser(): UserBuilder {
      return UserBuilder()
    }
  }

  private var friends: List<User> = emptyList()
  private var trips: List<Trip> = emptyList()

  fun withFriends(vararg friends: User): UserBuilder {
    this.friends = friends.toList()
    return this
  }

  fun withTrips(vararg trips: Trip): UserBuilder {
    this.trips = trips.toList()
    return this
  }

  fun build(): User {
    val user = User()
    friends.forEach(user::addFriend)
    trips.forEach(user::addTrip)
    return user
  }
}
