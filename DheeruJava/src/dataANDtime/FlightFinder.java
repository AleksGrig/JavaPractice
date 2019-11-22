package dataANDtime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public class FlightFinder {

	private Map<String, List<Flight>> allFlights = new HashMap<>();

	public FlightFinder(Map<String, List<Flight>> allFlights) {
		this.allFlights = allFlights;
	}

	public List<NavigableSet<Flight>> findFlights(int dayOfMonth, int month, int year, int preferredDepartureStartHour,
			int preferredDepartureEndHour, String departureCity, String arrivalCity, String finalArrivalCity,
			String departureCityTimeZone, String arrivalCityTimeZone) {

		List<NavigableSet<Flight>> result = new ArrayList<>();

		// Step 1: Construct ZonedDateTime objects to represent User-specified time
		// interval when flights depart

		// Your code
		LocalDateTime startTime = LocalDateTime.of(year, month, dayOfMonth, preferredDepartureStartHour - 1, 59);
		LocalDateTime endTime = LocalDateTime.of(year, month, dayOfMonth, preferredDepartureEndHour, 1);
		ZonedDateTime fromTime = ZonedDateTime.of(startTime, ZoneId.of(departureCityTimeZone));
		ZonedDateTime toTime = ZonedDateTime.of(endTime, ZoneId.of(departureCityTimeZone));

		// Step 2: Find departing flights at departureCity
		List<Flight> allDepartingFlights = allFlights.get(departureCity);
		for (Flight flight : allDepartingFlights) {
			System.out.println(
					flight.getDepartureCity() + " --> " + flight.getArrivalCity() + " At " + flight.getDepartureTime());
		}
		System.out.println();

		NavigableSet<Flight> departingflights = new TreeSet<>();

		// Your code
		// Tip: Methods like isAfter can be used to find flights in the specified user
		// time interval
		for (Flight flight : allDepartingFlights) {
			ZonedDateTime departTime = ZonedDateTime.of(flight.getDepartureTime(), ZoneId.of(departureCityTimeZone));
			if (flight.getArrivalCity().equals(arrivalCity) && departTime.isAfter(fromTime)
					&& departTime.isBefore(toTime)) {
				departingflights.add(flight);
			}
		}

		// Step 3: Find connecting flights
		// Constraint 1: Departing at arrivalCity (e.g., Dubai) and arrive at
		// finalArrivalCity (e.g., Mumbai)
		// Constraint 2: Should start at least two hours after the arrival time of the
		// first flight in the above navigable set

		List<Flight> allConnectingFlights = allFlights.get(arrivalCity);
		for (Flight flight : allConnectingFlights) {
			System.out.println(
					flight.getDepartureCity() + " --> " + flight.getArrivalCity() + " At " + flight.getDepartureTime());
		}
		System.out.println();

		NavigableSet<Flight> connectingflights = new TreeSet<>();

		// Your code
		Iterator<Flight> iterator = allConnectingFlights.iterator();
		while (iterator.hasNext()) {
			Flight flight = iterator.next();
			if (flight.getArrivalCity().equals(finalArrivalCity) && flight.getDepartureTime()
					.isAfter(departingflights.first().getDepartureTime().plusMinutes(119))) {
				connectingflights.add(flight);
			}
		}

		result.add(departingflights);
		result.add(connectingflights);

		return result;
	}

	public static void main(String[] args) {
		Flight f1 = new Flight(1, "1", "Emirates", "New York", "Dubai", LocalDateTime.of(2017, 12, 20, 9, 0),
				LocalDateTime.of(2017, 12, 20, 9, 0));
		Flight f2 = new Flight(2, "2", "Delta", "San Francisco", "Paris", LocalDateTime.of(2017, 12, 20, 9, 0),
				LocalDateTime.of(2017, 12, 20, 9, 0));
		Flight f3 = new Flight(3, "3", "Delta", "San Francisco", "Rome", LocalDateTime.of(2017, 12, 20, 9, 0),
				LocalDateTime.of(2017, 12, 20, 9, 0));
		Flight f4 = new Flight(4, "4", "Emirates", "San Francisco", "Dubai", LocalDateTime.of(2017, 12, 20, 8, 0),
				LocalDateTime.of(2017, 12, 20, 8, 0));
		Flight f5 = new Flight(5, "5", "Quatar", "San Francisco", "Dubai", LocalDateTime.of(2017, 12, 20, 9, 30),
				LocalDateTime.of(2017, 12, 20, 9, 30));
		Flight f6 = new Flight(6, "6", "Air France", "San Francisco", "Dubai", LocalDateTime.of(2017, 12, 20, 11, 30),
				LocalDateTime.of(2017, 12, 20, 11, 30));
		Flight f7 = new Flight(7, "7", "KLM", "San Francisco", "Dubai", LocalDateTime.of(2017, 12, 20, 12, 30),
				LocalDateTime.of(2017, 12, 20, 12, 30));
		Flight f8 = new Flight(8, "8", "Lufthanza", "San Francisco", "Dubai", LocalDateTime.of(2017, 12, 20, 16, 30),
				LocalDateTime.of(2017, 12, 20, 16, 30));
		Flight f9 = new Flight(9, "9", "Air France", "Paris", "New Delhi", LocalDateTime.of(2017, 12, 20, 9, 0),
				LocalDateTime.of(2017, 12, 20, 9, 0));
		Flight f10 = new Flight(10, "10", "Singapore Airlines", "Dubai", "Singapore",
				LocalDateTime.of(2017, 12, 20, 9, 0), LocalDateTime.of(2017, 12, 20, 9, 0));
		Flight f11 = new Flight(11, "11", "Emirates", "Dubai", "Mumbai", LocalDateTime.of(2017, 12, 20, 9, 30),
				LocalDateTime.of(2017, 12, 20, 9, 30));
		Flight f12 = new Flight(12, "12", "Finnair", "Dubai", "Mumbai", LocalDateTime.of(2017, 12, 20, 10, 30),
				LocalDateTime.of(2017, 12, 20, 10, 30));
		Flight f13 = new Flight(13, "13", "Swissair", "Dubai", "Mumbai", LocalDateTime.of(2017, 12, 20, 12, 0),
				LocalDateTime.of(2017, 12, 20, 12, 0));
		Flight f14 = new Flight(14, "14", "Quatar", "Dubai", "Mumbai", LocalDateTime.of(2017, 12, 20, 14, 0),
				LocalDateTime.of(2017, 12, 20, 14, 0));
		Flight f15 = new Flight(15, "15", "Alitalia", "Rome", "Mumbai", LocalDateTime.of(2017, 12, 20, 14, 0),
				LocalDateTime.of(2017, 12, 20, 14, 0));

		Map<String, List<Flight>> allFlights = new HashMap<>();

		allFlights.put("New York", Arrays.asList(f1));
		allFlights.put("San Francisco", Arrays.asList(f2, f3, f4, f5, f6, f7, f8));
		allFlights.put("Paris", Arrays.asList(f9));
		allFlights.put("Dubai", Arrays.asList(f10, f11, f12, f13, f14));
		allFlights.put("Rome", Arrays.asList(f15));

		List<NavigableSet<Flight>> result = new FlightFinder(allFlights).findFlights(20, 12, 2017, 9, 13,
				"San Francisco", "Dubai", "Mumbai", "America/Los_Angeles", "Asia/Dubai");

		Set<Flight> setDepartures = new TreeSet<>();
		setDepartures = result.get(0);
		for (Flight flight : setDepartures) {
			System.out.println(
					flight.getDepartureCity() + " --> " + flight.getArrivalCity() + " At "
					+ flight.getDepartureTime());
		}

		System.out.println();

		Set<Flight> setArrivals = new TreeSet<>();
		setArrivals = result.get(1);
		for (Flight flight : setArrivals) {
			System.out.println(
					flight.getDepartureCity() + " --> " + flight.getArrivalCity() + " At " + flight.getDepartureTime());
		}
	}

}
