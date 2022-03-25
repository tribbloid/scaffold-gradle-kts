package org.tribbloid.scaffold

import org.tribbloid.scaffold.Solution.Size

import java.util.concurrent.atomic.AtomicInteger
import scala.collection.mutable

//object Hello {}

/**
  * I own a parking garage that provides valet parking service. When a customer pulls up to the entrance
  * they are either rejected because the garage is full, or they are given a ticket they can use to
  * collect their car, and the car is parked for them.
  * Given a set of different parking bays (Small, Medium, Large), write a control program to
  * accept/reject cars (also small, medium or large) as they arrive, and issue/redeem tickets.
  *
  * Sample Input:
  *
  * Garage layout is 1 small bay, 1 medium bay, and 2 large bays:
  * [1,1,2]
  *
  * First sequence Actions:
  * [(arrival, small), (arrival, large), (arrival, medium), (arrival, large), (arrival, medium)]
  *
  * Expected output:
  * [1, 2, 3, 4, reject]
  *
  * Second sequence Actions:
  * [(arrival, small), (arrival, large), (arrival, medium), (arrival, large), (depart, 3), (arrival, medium)]
  *
  * Expected output:
  * [1, 2, 3, 4, 5]
  */
object Solution {}
