package Project;

import java.util.Arrays;

public class ChargingSystem {
	private static final int HOURS = 24;
	private static final int CHARGING_UNITS = 3;
	static String[][] Prioritized_Queue = new String[HOURS][CHARGING_UNITS];

	public static void main(String[] args) {
		Reservation_System(UserClass.class, AdminstratorClass.class, ChargingUnitClass.class);
		processDay(AdminstratorClass.class, ChargingUnitClass.class);
	}

	private static void Reservation_System(Class<?> userClass, Class<?> adminstratorClass, Class<?> chargingUnitClass) {
		UserDetails userDetails = userClass.getAnnotation(UserDetails.class);
		AdminstratorDetails adminstratorDetails = adminstratorClass.getAnnotation(AdminstratorDetails.class);

		for (String[] row : Prioritized_Queue) {
			Arrays.fill(row, "");
		}

		if (userDetails != null) {
			for (User user : userDetails.users()) {
				reserveSlot(user.CarPlateNumber(), user.TimeSlot(), user.CarType(),ChargingUnitClass.class);
			}

		}
		
		// Print the initial reservations
		System.out.println("Initial Reservations:");
		printReservations(adminstratorDetails);
	}

	private static void processDay(Class<?> adminstratorClass, Class<?> chargingUnitClass) {
		AdminstratorDetails adminstratorDetails = adminstratorClass.getAnnotation(AdminstratorDetails.class);
		ChargingStationDetails chargingStationDetails = chargingUnitClass.getAnnotation(ChargingStationDetails.class);

		
		System.out.println("\nProcessing Reservations Day:");
		for (int hour = 0; hour < HOURS; hour++) {
			// Print the chief's name at the start of each shift
			if (hour == 0) {
				System.out.println("Shift Start: Chief - " + adminstratorDetails.adminstrators()[0].Name());
			} else if (hour == 13) {
				System.out.println("Shift Start: Chief - " + adminstratorDetails.adminstrators()[1].Name());

			}

			System.out.println("\nHour " + hour + ":");
			for (int unit = 0; unit < CHARGING_UNITS; unit++) {
				if (!Prioritized_Queue[hour][unit].equals("")) {
					System.out.println("Processing: " + Prioritized_Queue[hour][unit] + " at unit " + chargingStationDetails.Stations()[unit].Name() + "(" + chargingStationDetails.Stations()[unit].Type() + ")");
					// Clear the processed reservation
					Prioritized_Queue[hour][unit] = "";
				}
			}
			
			printReservations(adminstratorDetails);
		}
	}

	private static void printReservations(AdminstratorDetails adminstratorDetails) {
		for (int hour = 0; hour < HOURS; hour++) {
			for (int unit = 0; unit < CHARGING_UNITS; unit++) {
				System.out.print(Prioritized_Queue[hour][unit] + "\t");
			}
			System.out.println();
		}
	}

	public static void reserveSlot(String carPlateNumber, int hour, String chargingType, Class<?> chargingUnitClass) {
		int startUnit = 0;
		int endUnit = CHARGING_UNITS;
		ChargingStationDetails chargingStationDetails = chargingUnitClass.getAnnotation(ChargingStationDetails.class);

		

		if (chargingType.equalsIgnoreCase("Electric")) {
			// If electric, only consider the first two units
			endUnit = Math.min(endUnit, 2);
		} else if (chargingType.equalsIgnoreCase("Solar")) {
			// If solar, only consider the third unit
			startUnit = Math.max(startUnit, 2);
		}

		// Check if the specified slot is available
		for (int unit = startUnit; unit < endUnit; unit++) {
			if (Prioritized_Queue[hour][unit].equals("")) {
				Prioritized_Queue[hour][unit] = chargingType.substring(0, 1).toUpperCase();
				System.out.println(
						carPlateNumber + " reserved at hour " + hour + " on unit " + unit + " for " + chargingType);
				return; 
			}
		}

		// If all slots are taken, find the next available slot in the next hour
		for (int nextHour = (hour + 1) % HOURS; nextHour != hour; nextHour = (nextHour + 1) % HOURS) {
			for (int unit = startUnit; unit < endUnit; unit++) {
				if (Prioritized_Queue[nextHour][unit].equals("")) {
					Prioritized_Queue[nextHour][unit] = chargingType.substring(0, 1).toUpperCase();
					System.out.println(carPlateNumber + " reserved at hour " + nextHour + " on unit " + chargingStationDetails.Stations()[unit].Name()
 + " for "
							+ chargingType);
					return; 
				}
			}
		}
		System.out.println("No available slots for " + carPlateNumber + " at hour " + hour + " for " + chargingType);
	}
}
