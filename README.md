
# Project: Charging System (Task 2)

## Introduction
This project is a Charging System that manages reservations for charging electric and solar-powered vehicles at charging stations. The system includes annotations and classes for users, administrators, charging stations, and logging energy-related information.

## Project Structure
The project consists of the following packages and classes:

### 1. Annotations
- **User:** Defines user information such as name, car type, energy type, car plate number, and time slot.
- **UserDetails:** Annotated on the `UserClass`, contains an array of `User` annotations.

- **Administrator:** Defines administrator information such as name, age, and shift taken.
- **AdministratorDetails:** Annotated on the `AdministratorClass`, contains an array of `Administrator` annotations.

- **Charging_Station_Info:** Defines charging station information such as name and type.
- **ChargingStationDetails:** Annotated on the `ChargingUnitClass`, contains an array of `Charging_Station_Info` annotations.

### 2. Classes
- **ChargingSystem:** Main class containing the logic for the reservation system and processing reservations for each hour.
- **ChargingUnitClass:** Represents charging units, annotated with `ChargingStationDetails`.
- **EnergyLogger:** Logs information related to energy, extends the `file` class.
- **File:** Base class for logging, contains common fields like username, plate, car type, energy type, and time slot.
- **Main:** Contains the `main` method, creates instances of `systemLogger`, `stationLogger`, and `energyLogger`, and performs logging tasks.
- **SystemLogger, StationLogger:** Classes that extend `File` and handle system and station-related logging tasks.
- **UserClass:** Represents users, annotated with `UserDetails`.

## How to Run the Project



