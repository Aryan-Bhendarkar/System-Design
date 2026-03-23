# Parking Lot Design - Beginner Friendly Java

This version is simple and interview-friendly, but still follows core design rules.

## Principles Followed

- SRP: each class has a single job.
- OCP: you can add a new spot-selection rule or fee rule by creating a new strategy class.
- DIP: `ParkingLot` depends on interfaces, not fixed implementations.

## Patterns Used

- Strategy Pattern for spot selection:
	- `SpotSelectionStrategy`
	- `FirstFitSpotSelectionStrategy`
- Strategy Pattern for fee:
	- `FeeStrategy`
	- `SimpleFeeStrategy`

## Classes

- `Main`
- `ParkingLot`
- `ParkingSpot`
- `ParkingTicket`
- `Vehicle`
- `VehicleType`
- `SpotType`
- `SpotSelectionStrategy`
- `FirstFitSpotSelectionStrategy`
- `FeeStrategy`
- `SimpleFeeStrategy`

## Run (PowerShell)

```powershell
cd "a:\System Design\Parking Lot Design"
mkdir out -Force
javac -d out (Get-ChildItem -Filter *.java | ForEach-Object { $_.FullName })
java -cp out Main
```
